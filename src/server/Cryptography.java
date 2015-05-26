/**
 * 
 */
package server;

import java.security.*;

/**
 *
 */
public class Cryptography {
	
	/**
	 * Encrypts the password
	 * @param pwd The password
	 * @param salt The salt to encrypt the password with
	 */
	public static String encrypt(String pwd, String salt)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = (pwd+salt).getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// This should not happen, ever.
			e.printStackTrace();
		}
		return null;
	}
}
