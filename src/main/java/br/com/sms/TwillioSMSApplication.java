package br.com.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class TwillioSMSApplication {
    public static void main(String[] args) {
	SpringApplication.run(TwillioSMSApplication.class, args);
    }
}