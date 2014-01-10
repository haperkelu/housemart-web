/**
 * 
 */
package org.housemart.web.controller;

import java.util.Calendar;
import java.util.Date;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.housemart.dao.entities.MaxinRawEntity;
import org.housemart.dao.entities.TradingCenterRawEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author user
 *
 */
@Controller
public class ImportDataController {

	private static final Logger logger = LoggerFactory
			.getLogger("CommonLogger");

	@Autowired
	GenericDao maxinRawDao;
	
	@RequestMapping(value = "importDataSubmit.controller")
	public String importData(@RequestParam("file") MultipartFile file, Model model) {
		
		Workbook book  = null;
		logger.info("Begin:" + file.getOriginalFilename() + ":" + Calendar.getInstance().getTime());
		try {
			book  =  Workbook.getWorkbook(file.getInputStream());
		} catch (Exception e) {
			model.addAttribute("message", "excel格式有误");
			logger.error(e.getMessage(), e);
			if(book != null){
				book.close();
			}
			return "report/importData";
		}
		
		Sheet sheet  =  book.getSheet(0);
		if(sheet == null || sheet.getRows() <= 1){
			model.addAttribute("message", "excel数据为空");
			return "report/importData";
		}
		int total = sheet.getRows() - 1;
		int affectedCount = 0;

		model.addAttribute("total", total);
		for(int j = 1; j < sheet.getRows() ; j++)  {
			
			if(j % 10 == 0){
				logger.info(file.getOriginalFilename() + ":" + j +":" + Calendar.getInstance().getTime());
			}
			String id = sheet.getCell(0,j).getContents().toString();
			try {
				MaxinRawEntity entity = new MaxinRawEntity();
				if(StringUtils.isEmpty(id)){
					continue;
				}
				entity.setId(id);
				entity.setClientName(sheet.getCell(1,j).getContents() + "");
				entity.setPhone1(sheet.getCell(2,j).getContents() + "");
				entity.setPhone2(sheet.getCell(3,j).getContents() + "");
				entity.setPhone3(sheet.getCell(4,j).getContents() + "");
				entity.setContactMemo(sheet.getCell(5,j).getContents() + "");
				entity.setRegion(sheet.getCell(6,j).getContents() + "");
				entity.setPlate(sheet.getCell(7,j).getContents() + "");
				
				entity.setResidence(sheet.getCell(8,j).getContents() + "");
				entity.setAddress(sheet.getCell(9,j).getContents() + "");
				entity.setBuildingNo(sheet.getCell(10,j).getContents() + "");
				entity.setCellNo(sheet.getCell(11,j).getContents() + "");
				entity.setHouseArea(sheet.getCell(12,j).getContents() + "");
				entity.setAvg(sheet.getCell(13,j).getContents() + "");
				entity.setTotalValue(sheet.getCell(14,j).getContents() + "");
				
				entity.setRentValue(sheet.getCell(15,j).getContents() + ""); 
				entity.setSaleIntention(sheet.getCell(16,j).getContents() + "");
				entity.setSalePrice(sheet.getCell(17,j).getContents() + "");
				entity.setRentIntention(sheet.getCell(18,j).getContents() + "");
				entity.setRentPrice(sheet.getCell(19,j).getContents() + "");
				entity.setMemo(sheet.getCell(20,j).getContents() + "");
				entity.setDialResult(sheet.getCell(21,j).getContents() + "");
				entity.setTaskPerson(sheet.getCell(22,j).getContents() + "");
				entity.setDialTime(sheet.getCell(23,j).getContents() + "");
				entity.setAssignTime(sheet.getCell(24,j).getContents() + "");				
				
				//updateRawDataByID
				MaxinRawEntity target = (MaxinRawEntity) maxinRawDao.load("findRecordListByMaxinId", Integer.parseInt(id));
				if(target == null){
					maxinRawDao.add("addRawData", entity);
				} else {
					maxinRawDao.update("updateRawDataByID", entity);
				}
								
				affectedCount ++;
			} catch (Exception e) {
				logger.error(id + ":" + e.getMessage(), e);
			}
			if(j % 10 == 0){
				logger.info(file.getOriginalFilename() + ":" + j +":" + Calendar.getInstance().getTime());
			}				
		}						
		logger.info("End:" + file.getOriginalFilename() + ":" + Calendar.getInstance().getTime());		
		model.addAttribute("affectedCount", affectedCount);
		return "report/importData";
	}
	
	@RequestMapping(value = "importData.controller")
	public String importDataInit(Model model) {
		return "report/importData";
	}
	
	
	@RequestMapping(value = "importTradingCenterDataSubmit.controller")
  public String importTradingCenterData(@RequestParam("file") MultipartFile file, Model model) {
    
	  // TODO::::::
    Workbook book  = null;
    try {
      book  =  Workbook.getWorkbook(file.getInputStream());
    } catch (Exception e) {
      model.addAttribute("message", "excel格式有误");
      logger.error(e.getMessage(), e);
      if(book != null){
        book.close();
      }
      return "report/importTradingCenterData";
    }
    
    Sheet sheet  =  book.getSheet(0);
    if(sheet == null || sheet.getRows() <= 1){
      model.addAttribute("message", "excel数据为空");
      return "report/imporTradingCentertData";
    }
    int total = sheet.getRows() - 1;
    int affectedCount = 0;
    model.addAttribute("total", total);
    for(int j = 1; j < sheet.getRows() ; j++)  {
      
      try {
        TradingCenterRawEntity entity = new TradingCenterRawEntity();
        
        entity.setRegionName(sheet.getCell(0,j).getContents().trim().toString());
        entity.setPlateName(sheet.getCell(1,j).getContents().trim().toString());
        entity.setResidenceName(sheet.getCell(2,j).getContents().trim().toString());
        entity.setResidenceAddress(sheet.getCell(3,j).getContents().trim().toString());
        entity.setHouseType(sheet.getCell(4,j).getContents().trim().toString());
        entity.setPropertyArea(sheet.getCell(5,j).getContents().trim().toString());
        entity.setPrice(sheet.getCell(6,j).getContents().trim().toString());
        entity.setDealDate(sheet.getCell(7,j).getContents().trim().toString());
        
        
        affectedCount ++;
      } catch (Exception e) {
        logger.error(":" + e.getMessage(), e);
      }
              
    }           
        
    model.addAttribute("affectedCount", affectedCount);
    return "report/importTradingCenterData";
  }
	
	
	@RequestMapping(value = "importTradingCenterData.controller")
  public String importTradingCenterDataInit(Model model) {
    return "report/importTradingCenterData";
  }
}
