package es.ilunion.test;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

public class test {
	
	public static void main(String[] args)
	{
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		System.out.println(UUID.randomUUID().toString());
		
		SecureRandom random = new SecureRandom();
		System.out.println(new BigInteger(50, random).toString(32));
	}
	

}
