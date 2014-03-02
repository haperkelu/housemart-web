/**
 * Created on 2013-12-1
 * 
 * Copyright (c) HouseMart, Inc. 2012. All rights reserved.
 */
package org.housemart.crawl.pic.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.housemart.crawl.common.crawler.PicCrawler;
import org.housemart.crawl.pic._IPicConstants;
import org.housemart.crawl.pic.model.anjuke.CommunityGet;
import org.housemart.crawl.pic.service.anjuke.search._IEntitySearchable;
import org.housemart.dao.entities.HousePicEntity.Status;
import org.housemart.dao.entities.RepositoryHousePicEntity;
import org.housemart.dao.entities.RepositoryHousePicEntity.CrawlStatus;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import org.housemart.resource.ResourceProvider;
import org.housemart.util.UrlBase64Coder;
import org.housemart.web.context.SpringContextHolder;
import org.webharvest.runtime.Scraper;

import com.caucho.hessian.client.HessianProxyFactory;

public class ResidencePicCrawlerService {
  
  public List<Integer> crawlAndSaveResidencePic(int housemartId, int anjukeId) throws Exception {
    
    PicCrawler picCrawler = SpringContextHolder.getBean("picCrawler");
    _IEntitySearchable<CommunityGet> communitySearcher = SpringContextHolder.getBean("communitySearcher");
    List<Integer> pics = new ArrayList<Integer>();
    CommunityGet communityGet = communitySearcher.search(String.valueOf(anjukeId));
    
    // pic of residence20131130
    List<String> photosOfResidence = communityGet.getPhotos();
    if (CollectionUtils.isNotEmpty(photosOfResidence)) {
      for (String photo : photosOfResidence) {
        String url = photo;
        String photoName = StringUtils.substringAfterLast(url, "com/").replaceAll("/", "-");
        String fileSavePath = getPicPathPrefix() + photoName;
        String fullPath = picCrawler.getWorkDir() + "/" + fileSavePath;
        
        // file
        picCrawler.crawl(url, fileSavePath);
        
        // db
        RepositoryHousePicEntity.CrawlStatus crawlStatus = RepositoryHousePicEntity.CrawlStatus.Error;
        if (picCrawler.getScraper().getStatus() == Scraper.STATUS_FINISHED) {
          crawlStatus = RepositoryHousePicEntity.CrawlStatus.Success;
          int picId = addHousePicToRespositoryDb(null, housemartId, fullPath, RepositoryHousePicEntity.Type.Residence.getValue(),
              crawlStatus);
          pics.add(picId);
        }
        
      }
    }
    return pics;
  }
  
  public UploadResultBean uploadPics(List<Integer> picIds) throws IOException {
    
    UploadResultBean result = new UploadResultBean();
    if (CollectionUtils.isNotEmpty(picIds)) {
      result.setTotalCount(picIds.size());
      for (Integer pic : picIds) {
        if (pic != null) {
          boolean uploadSuccess = uploadPic(pic);
          if (uploadSuccess) {
            result.setSuccessCount(result.getSuccessCount() + 1);
          }
        }
      }
    }
    return result;
  }
  
  public boolean uploadPic(Integer picId) throws IOException {
    
    GenericDao<RepositoryHousePicEntity> repositoryHousePicDao = SpringContextHolder.getBean("repositoryHousePicDao");
    ResourceProvider resourceProvider = SpringContextHolder.getBean("resourceProvider");
    
    boolean uploadSuccess = false;
    RepositoryHousePicEntity pEntity = repositoryHousePicDao.load("loadHousePic", picId);
    String fileFullPath = pEntity.getUrl();
    String[] arrNames = pEntity.getUrl().split("\\.");
    final String fileName = UrlBase64Coder.encoded(StringUtils.substringAfterLast(arrNames[0], "/")) + "_"
        + Calendar.getInstance().getTime().getTime() + "." + arrNames[1];
    
    String URL = resourceProvider.getValue("housemart.pic.service.url");
    HessianProxyFactory factory = new HessianProxyFactory();
    HessianPicServiceInterface service = (HessianPicServiceInterface) factory.create(HessianPicServiceInterface.class, URL);
    
    PicSaveResult remoteResult = service.savePicToCloud(picId, fileName, "image/" + arrNames[1], fileFullPath);
    
    if (remoteResult != null && remoteResult.getCode() == 200) {
      Map<String,Object> map = new HashMap<String,Object>();
      map.put("id", picId);
      map.put("cloudURL", remoteResult.getUrl());
      map.put("status", 1);
      repositoryHousePicDao.update("updateRepositoryPicCloudURL", map);
      uploadSuccess = true;
    }
    return uploadSuccess;
    
  }
  
  private int addHousePicToRespositoryDb(Integer houseId, Integer residenceId, String path, int type,
      RepositoryHousePicEntity.CrawlStatus crawlStatus) {
    
    GenericDao<RepositoryHousePicEntity> repositoryHousePicDao = SpringContextHolder.getBean("repositoryHousePicDao");
    
    int picId = -1;
    RepositoryHousePicEntity picEntity = new RepositoryHousePicEntity();
    picEntity.setHouseId(houseId);
    picEntity.setResidenceId(residenceId);
    picEntity.setType(type);
    picEntity.setCrawlStatus(crawlStatus.getValue());
    picEntity.setAddTime(Calendar.getInstance().getTime());
    picEntity.setUpdateTime(Calendar.getInstance().getTime());
    picEntity.setUrl(path);
    picId = repositoryHousePicDao.add("addHousePic", picEntity);
    return picId;
  }
  
  public int addHousePicToRespositoryDb(Integer houseId, Integer residenceId, String cloudPath, int type) {
    GenericDao<RepositoryHousePicEntity> repositoryHousePicDao = SpringContextHolder.getBean("repositoryHousePicDao");
    int picId = -1;
    RepositoryHousePicEntity picEntity = new RepositoryHousePicEntity();
    picEntity.setHouseId(houseId);
    picEntity.setResidenceId(residenceId);
    picEntity.setType(type);
    picEntity.setStatus(Status.Valid.getValue());
    picEntity.setCrawlStatus(CrawlStatus.Success.getValue());
    picEntity.setAddTime(Calendar.getInstance().getTime());
    picEntity.setUpdateTime(Calendar.getInstance().getTime());
    picEntity.setCloudURL(cloudPath);
    picId = repositoryHousePicDao.add("addHousePic", picEntity);
    return picId;
  }
  
  private String getPicPathPrefix() {
    return new SimpleDateFormat("yyyyMMdd").format(new Date()) + _IPicConstants.FILE_SUBPATH_RESIDENCE + "/";
  }
  
  public static class UploadResultBean {
    int totalCount = 0;
    int successCount = 0;
    
    public int getTotalCount() {
      return totalCount;
    }
    
    public void setTotalCount(int totalCount) {
      this.totalCount = totalCount;
    }
    
    public int getSuccessCount() {
      return successCount;
    }
    
    public void setSuccessCount(int successCount) {
      this.successCount = successCount;
    }
  }
}
