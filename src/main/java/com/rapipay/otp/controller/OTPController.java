package com.rapipay.otp.controller;

import com.rapipay.otp.app.Services.OTPServiceImpl;
import com.rapipay.otp.entity.OTP;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.exception.InvalidInputException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OTPController {

	@Autowired
	OTPServiceImpl otpService;

	
	public boolean verifyEmail(String email) {
		boolean isValid=false;
		
 		return isValid;
	}

	
	@GetMapping("/home")
	public String home() {
		return "hello";
	}
	@RequestMapping(method = RequestMethod.POST, value = "/sendOTP")
	public String sendOTP(@RequestBody OTP otp) throws InvalidInputException {
		try {
			otpService.sendOTP(otp);
		}catch(Exception e){
			return e.toString();
		}
		
		return "OTP sent successfully";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/verifyOTP")
	public String verifyOTP(@RequestBody OTP otp){
		try {
			return otpService.validateOTP(otp);
		}catch(Exception e) {
			return e.toString();
		}
		
	}

}
