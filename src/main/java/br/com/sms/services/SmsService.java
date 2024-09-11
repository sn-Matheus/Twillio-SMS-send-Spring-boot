package br.com.sms.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import br.com.sms.models.SmsCodes;
import br.com.sms.repositories.SmsRepository;
import br.com.sms.utilities.Utilities;

@Service
public class SmsService {

    private final String ACCCOUNT_SID = "Your twillio SID";
    private final String AUTH_TOKEN = "Your twillio TOKEN";
    private final String FROM_PHONE = "Your twillio NUMBER";
    private final SmsRepository smsRepository;

    public SmsService(SmsRepository smsRepository) {
	Twilio.init(ACCCOUNT_SID, AUTH_TOKEN);
	this.smsRepository = smsRepository;
    }

    /*---------------------------------
     * SMS SENDER
     * ------------------------------*/
    private String formatPhone(String phone) {
	if (!phone.startsWith("+")) {
	    return "+" + phone;
	}
	return phone;
    }

    public void sendSms(String phone, String code) {
	String formattedPhone = formatPhone(phone);
	String messageBody = "Your verification code is: " + code;
	Message message = Message.creator(new PhoneNumber(formattedPhone), new PhoneNumber(FROM_PHONE), messageBody).create();
	System.out.println(message.getSid());
    }

    private void saveSmsCode(String phone, String code) {
	SmsCodes smsCode = SmsCodes.builder().code(code).phone(phone).build();
	smsRepository.save(smsCode);
    }

    public ResponseEntity<Object> requestCode(String phone) {
	Optional<SmsCodes> optional = smsRepository.findByPhone(phone);

	if (optional.isPresent()) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Write a message");
	}

	String verificationCode = Utilities.generateCode();
	saveSmsCode(phone, verificationCode);
	sendSms(phone, verificationCode);

	return ResponseEntity.status(HttpStatus.OK).body("Write a message");
    }

    public ResponseEntity<Object> verifyCode(String phone, String code) {
	Optional<SmsCodes> optional = smsRepository.findByPhone(phone);

	if (optional.isEmpty()) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Write a message");
	}

	SmsCodes savedCode = optional.get();

	if (!savedCode.getCode().equals(code)) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Write a message");
	}

	return ResponseEntity.status(HttpStatus.OK).body("Write a message");
    }

}
