/**   
* @Title: RPCEventHandler.java 
* @Package org.housemart.web.events 
* @Description: TODO
* @author Pie.Li   
* @date 2013-12-26 下午9:44:08 
* @version V1.0   
*/
package org.housemart.web.events;

import java.lang.reflect.Method;
import java.util.List;
import org.brilliance.middleware.event.RPCEventHandler;
import org.brilliance.middleware.serialize.CustomEntry;
import org.housemart.web.context.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Pie.Li
 *
 */
public class CustomRPCEventHandler implements RPCEventHandler {

	private static final Logger logger = LoggerFactory.getLogger(CustomRPCEventHandler.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object onRecieveData(String classFullName, String method,
			List<CustomEntry<Class, Object>> para)   {
		
		logger.info("fullName:" + classFullName + ";method:" + method);
		if(classFullName != null){
			try {
				logger.info("rpc bean name:" + replaceDotWithUnderStore(classFullName));
				Object obj = SpringContextHolder.getBean(replaceDotWithUnderStore(classFullName));
				Class[] transformedClass = null;
				Object[] transformedValues = null;
				Method methodInvoking;

				logger.info("RPC Parameter List Size:" + para.size());
				if(para != null && para.size() > 0){
					transformedClass = new Class[para.size()];
					transformedValues = new Object[para.size()];
					int count = 0;
					for(CustomEntry<Class, Object> item: para){
						logger.info("RPC Parameter Item:" + item.getKey() + ";" + item.getValue());
						transformedClass[count] = item.getKey();
						transformedValues[count] = item.getValue();
						count ++;
					}
				}
				
				methodInvoking = obj.getClass().getMethod(method, transformedClass);
				logger.info("method:" + (methodInvoking == null));

				Object result = methodInvoking.invoke(obj, transformedValues);
				logger.info("RPC invoke:" + result);
				return result;					
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceDotWithUnderStore(String str){
		if(str != null){
			return str.replaceAll("[.]", "_");
		}
		return null;
	}

}
