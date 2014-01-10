/**
 * 
 */
package org.housemart.web.security;

import java.util.concurrent.atomic.AtomicLong;

/**
 * To-Do 分布式支持
 * @author pai.li
 *
 */
public class BizIDGenerator {

	private static final int seed = 1; 
	private static AtomicLong value = new AtomicLong(0);
	private static Object lockObj = new Object();
	
	public static long incrementGenerate(Object context){
		
		synchronized(lockObj){
			value.addAndGet(seed);
		}		
		
		return value.get();
		
	}
	
	public static long get(Object context){
		return value.get();
	}
	
	
}
