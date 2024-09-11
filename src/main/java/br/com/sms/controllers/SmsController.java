package br.com.sms.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sms.services.SmsService;

@RestController()
@RequestMapping("/api/v1/sms")
public class SmsController {

    private final SmsService smsService;

    public SmsController(SmsService smsService) {
	this.smsService = smsService;
    }

    @PostMapping("/requestCode")
    public ResponseEntity<Object> requestCode(@RequestParam String phone) {
	return smsService.requestCode(phone);
    }

    @PostMapping("/verifyCode")
    public ResponseEntity<Object> verifyCode(@RequestParam String phone, @RequestParam String code) {
	return smsService.verifyCode(phone, code);
    }

}
