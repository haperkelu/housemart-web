/**
 * 
 */
package org.housemart.web.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * @author pai.li
 *
 */
public class EncryptionService {

	
	/**AES Algorithm Name**/
	private final static String AES_NAME = "AES/CBC/PKCS5Padding";
	
	/**AES KEY**/
	private final static String AES_KEY = "AES_KEY_2012_0001111";
	
	/**Site Key**/
	private final static String SITE_KEY = "ALL-In-One";
	
	/** Default Encoding**/
	private final static String DEFAULT_ENCODING = "UTF-8";
	
	private SecretKeyFactory securityKeyFactory = null;
	
	public EncryptionService() throws NoSuchAlgorithmException{
		securityKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");  
	}
	
	/**
	 * 
	 * @param rawString
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws InvalidParameterSpecException 
	 * @throws UnsupportedEncodingException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public String encrypt(EncryptionModel model) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, InvalidKeySpecException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		
		if(model == null){
			throw new IllegalArgumentException("encryption model should not be null");
		}
		Cipher cipher = Cipher.getInstance(AES_NAME);
		cipher.init(Cipher.ENCRYPT_MODE, generateSecurityKey(AES_KEY, model)); 
		
		AlgorithmParameters params = cipher.getParameters();  
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();  
		model.setIv(iv);
		
		byte[] ciphertext = cipher.doFinal(model.getRawString().getBytes(DEFAULT_ENCODING)); 		
		String resultString = new String(Base64.encodeBase64(ciphertext));
		resultString = URLEncoder.encode(resultString, DEFAULT_ENCODING);
		
		return resultString;
		
	}
	
	/**
	 * 
	 * @param input
	 * @param model
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	public String decrypt(String input, EncryptionModel model) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		
		if(model == null){
			throw new IllegalArgumentException("encryption model should not be null");
		}
	
		byte[] bResult = Base64.decodeBase64(URLDecoder.decode(input, DEFAULT_ENCODING));		
		KeySpec spec = new PBEKeySpec(SITE_KEY.toCharArray(), model.getSalt(), 1024, 256);  
		SecretKey secretKey = new SecretKeySpec(securityKeyFactory.generateSecret(spec).getEncoded(), "AES"); 
		Cipher dcipher = Cipher.getInstance(AES_NAME); 
		dcipher.init(Cipher.DECRYPT_MODE, secretKey,  
			    new IvParameterSpec(model.getIv()));  
		byte[] result = dcipher.doFinal(bResult);
				
		return new String(result, DEFAULT_ENCODING);
	}
	
	/**
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException 
	 */
	private SecretKey generateSecurityKey(String key, EncryptionModel model) throws NoSuchAlgorithmException, InvalidKeySpecException{
		byte[] salt = new byte[16]; 
		SecureRandom random = new SecureRandom();  
		random.nextBytes(salt);
		model.setSalt(salt);
		KeySpec spec = new PBEKeySpec(SITE_KEY.toCharArray(), salt, 1024, 256); 
		SecretKey secretKey = securityKeyFactory.generateSecret(spec);  
		SecretKey result = new SecretKeySpec(secretKey.getEncoded(), "AES");
		return result;
	}
	
}
