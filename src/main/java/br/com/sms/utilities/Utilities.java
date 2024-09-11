package br.com.sms.utilities;

import java.security.SecureRandom;

public class Utilities {
    private static final SecureRandom random = new SecureRandom();
    
    public static String generateCode() {
	int code = 100000 + random.nextInt(900000); // Genarate a number between 100000 and 999999
	return String.valueOf(code);
    }
}
