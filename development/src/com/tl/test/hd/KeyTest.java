package com.tl.test.hd;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

public class KeyTest {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");  
keyPairGenerator.initialize(1024);  

KeyPair keyPair = keyPairGenerator.generateKeyPair();  

// 甲方公钥  
DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();  
System.out.println(publicKey.getEncoded());
// 甲方私钥  
DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();  
System.out.println(privateKey.getAlgorithm());
	}
}
