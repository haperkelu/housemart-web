/**
 * 
 */
package org.housemart.encoding;

import java.io.IOException;
import org.msgpack.MessagePack;

/**
 * @author pai.li
 *
 */
public class FastOjbecEncoder {

	private final static MessagePack msgpack = new MessagePack();
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @throws IOException 
	 */
	public static <T> byte[] encode(T obj) throws IOException{
		return msgpack.write(obj);
	}
	
	/**
	 * 
	 * @param bytes
	 * @param c
	 * @return
	 * @throws IOException 
	 */
	public static <T> T decode(byte[] bytes, Class<T> c) throws IOException{
		return msgpack.read(bytes, c);
	}
	
}
