/**   
* @Title: StartupListner.java 
* @Package org.housemart.web.context 
* @Description: TODO
* @author Pie.Li   
* @date 2013-12-26 下午9:54:35 
* @version V1.0   
*/
package org.housemart.web.context;

import javax.servlet.ServletContextEvent;

import org.brilliance.middleware.core.EmbeddedServer;
import org.brilliance.middleware.event.RPCEventHandler;
import org.housemart.resource.ResourceProvider;
import org.housemart.web.events.CustomRPCEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pie.Li
 *
 */
public class StartupListener implements javax.servlet.ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		RPCEventHandler rpcHandler = new CustomRPCEventHandler();
		EmbeddedServer.registerEvent(rpcHandler);
		final String portStr = ((ResourceProvider)SpringContextHolder.getBean("resourceProvider")).getValue("housemart.rpc.port");
		Thread thread = new Thread(new Runnable(){

			public void run() {
				try {
					EmbeddedServer.startServer(Integer.parseInt(portStr));
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		});
		thread.start();
		
	}

	
	
	
}
