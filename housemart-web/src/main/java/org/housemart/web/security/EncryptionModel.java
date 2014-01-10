package org.housemart.web.security;

/**
 * 
 * @author pai.li
 *
 */
public class EncryptionModel {
	
	private long id;
	private String rawString;
	private byte[] salt;
	private byte[] iv;
	private String extra;
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the rawString
	 */
	public String getRawString() {
		return rawString;
	}
	/**
	 * @param rawString the rawString to set
	 */
	public void setRawString(String rawString) {
		this.rawString = rawString;
	}
	/**
	 * @return the salt
	 */
	public byte[] getSalt() {
		return salt;
	}
	/**
	 * @param salt the salt to set
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	/**
	 * @return the iv
	 */
	public byte[] getIv() {
		return iv;
	}
	/**
	 * @param iv the iv to set
	 */
	public void setIv(byte[] iv) {
		this.iv = iv;
	}
	/**
	 * @return the extra
	 */
	public String getExtra() {
		return extra;
	}
	/**
	 * @param extra the extra to set
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	
}
