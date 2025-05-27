package com.example.demo.account.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class HashUtil {
	public static String generateSalt() throws Exception{
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return bytesToHex(salt);
	}
	
	public static String hashPassword(String passwrd, String salt) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] hashBytes = md.digest((passwrd+salt).getBytes());
		return bytesToHex(hashBytes);
	}
	
	public static String bytesToHex(byte[] bytes) {
		//將bytes轉成16進位字串
		StringBuilder sb = new StringBuilder();
		//逐一處理陣列裡的每個 byte
		for(byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		//把累加結果一次回傳
		return sb.toString();
	}
	
}
