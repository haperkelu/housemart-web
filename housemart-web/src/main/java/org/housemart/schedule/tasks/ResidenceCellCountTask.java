package org.housemart.schedule.tasks;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.housemart.dao.entities.ResidenceBuildingEntity;
import org.housemart.dao.entities.ResidenceEntity;
import org.housemart.framework.dao.generic.GenericDao;
import org.housemart.framework.dao.generic.UniqueIdObject;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ResidenceCellCountTask extends  TimerTask {
		
	private static final Logger logger = LoggerFactory.getLogger(ResidenceCellCountTask.class);
	
	@SuppressWarnings("rawtypes")
	GenericDao residenceDao = SpringContextHolder.getBean("residenceDao");

	@SuppressWarnings("rawtypes")
	GenericDao residenceBuildingDao = SpringContextHolder.getBean("residenceBuildingDao");
		
	
	private static final String FILE_INDEX_DIRECTORY = "/data/index/housemart";

	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		
		try {
			logger.debug("begin index");
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36); 			
			Directory directory = FSDirectory.open(new File(FILE_INDEX_DIRECTORY));
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("status", 1);
			List<ResidenceEntity> list = residenceDao.select("findResidenceList", param);
			if(!CollectionUtils.isEmpty(list)){
				
				IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
				conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
				IndexWriter writer = new IndexWriter(directory, conf); 
				
				for(ResidenceEntity item: list){
					Document doc = new Document(); 
					doc.add(new Field("id", String.valueOf(item.getResidenceId()), Store.YES, Index.ANALYZED));							
					List<ResidenceBuildingEntity> buildingList = residenceBuildingDao.select("findResidenceBuildingListByResidenceId",
									new UniqueIdObject(item.getResidenceId()));
					doc.add(new Field("buildingCount", String.valueOf(buildingList == null ?0 :buildingList.size()), Store.YES, Index.ANALYZED));
					
					int cellCount = 0;
					if(!CollectionUtils.isEmpty(buildingList)){
						for(ResidenceBuildingEntity building: buildingList){
							cellCount += building.getCellCount();
						}
					}
					doc.add(new Field("cellCount", String.valueOf(cellCount), Store.YES, Index.ANALYZED));					
					writer.addDocument(doc); 	
					writer.updateDocument(new Term("id", String.valueOf(item.getResidenceId())), doc);

				}
				writer.close();
			} 			
			
			logger.debug("index end");
		} catch (Exception e) {
			logger.error("Index Error", e);
		}
		
	}

}
