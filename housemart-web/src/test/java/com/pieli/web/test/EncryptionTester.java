/**
 * 
 */
package com.pieli.web.test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.housemart.web.security.SimpleSecurityService;
import org.junit.Test;


/**
 * @author pai.li
 *
 */
public class EncryptionTester {

	@Test
	public void test() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		/**
		EncryptionService service = new EncryptionService();
		EncryptionModel model = new EncryptionModel();
		model.setId(BizIDGenerator.incrementGenerate(null));
		model.setRawString("eeeddfasdfasd1122133$$");
		String result = service.encrypt(model);
		System.out.println(result);		
		System.out.println(model.getId());
		
		EncryptionModel dModel = new EncryptionModel();
		dModel.setId(BizIDGenerator.get(null));
		dModel.setSalt(model.getSalt());
		dModel.setIv(model.getIv());
		String original = service.decrypt(result, dModel);
		System.out.println(original);		
		**/
		String r = SimpleSecurityService.encrypt("v54fff35ss", new Date());
		System.out.println(r);
		String rr = SimpleSecurityService.decrypt(r);
		System.out.println(r + ";" + rr);
		
		String g = "f";
		System.out.println(Integer.toHexString(g.getBytes()[0]));
		String gg = "v";
		System.out.println(Integer.toHexString(gg.getBytes()[0]));
	}
	
}
