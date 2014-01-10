package org.housemart.service;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.housemart.dao.entities.ResidenceEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchService {

	private static final Logger logger = LoggerFactory.getLogger("indexLogger");
	private static final String FILE_INDEX_DIRECTORY = "/data/index/housemart";

	
	@SuppressWarnings("resource")
	public void populateResidenceData(ResidenceEntity entity){
		if(entity != null){
			logger.debug("begin search");
			
			try {
				IndexReader reader = IndexReader.open(FSDirectory.open(new File(  
						FILE_INDEX_DIRECTORY)));  
				Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
				IndexSearcher searcher = new IndexSearcher(reader);  
				QueryParser parser = new QueryParser(Version.LUCENE_36, "id",  
				        analyzer);  
				Query query = parser.parse(String.valueOf(entity.getResidenceId()));  
				TopDocs results = searcher.search(query, null, 100);
				ScoreDoc[] scoreDocs = results.scoreDocs;
				if(scoreDocs == null){
					logger.error("search hit error");
					return;
				}
				ScoreDoc target = null;
				for(ScoreDoc score: scoreDocs){
					if(target == null){
						target = score;
					} else{
						if(target.score < score.score){
							target = score;
						}
					}
				}
				Document doc = searcher.doc(target.doc);  
				int buildingCount = Integer.parseInt(doc.get("buildingCount"));
				int cellCount = Integer.parseInt(doc.get("cellCount"));
				entity.setBuildingCount(buildingCount);
				entity.setCellCount(cellCount);
				reader.close();
			} catch (Exception e) {
				logger.error("search error", e);
			}   
		}
		
	}
}
