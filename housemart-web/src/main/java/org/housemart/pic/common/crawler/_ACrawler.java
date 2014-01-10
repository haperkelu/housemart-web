/**
 * Created on 2013-4-5
 * 
 */
package org.housemart.pic.common.crawler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.xml.sax.InputSource;

public abstract class _ACrawler {

	protected final Log log = LogFactory.getLog(this.getClass());
	protected ScraperConfiguration config;
	protected String workDir;
	protected Scraper scraper;
	protected ObjectMapper om;

	public _ACrawler(String configPath, String workDir) {
		this.config = new ScraperConfiguration(new InputSource(_ACrawler.class.getClassLoader().getResourceAsStream(
				configPath)));
		this.workDir = workDir;
		this.scraper = new HouseMartScraper(config, workDir);
		this.om = new ObjectMapper();
	}

	public Scraper getScraper() {
		return scraper;
	}

  public String getWorkDir() {
    return workDir;
  }

}
