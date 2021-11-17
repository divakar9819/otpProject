package com.rapipay.otp.app.Services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.rapipay.otp.entity.OTP;

class OTPServiceImplTest {

	@Test
	@DisplayName("Validate Email 1")
	void testCheckEmail1() {
		//fail("Not yet implemented");
		OTPServiceImpl otpService=new OTPServiceImpl();
	    boolean expected=true;
	    boolean actual=otpService.checkEmail("abc@gmail.com");
	    assertEquals(expected,actual);
	}
	
	@Test
	@DisplayName("Validate Email 2")
	void testCheckEmail2() {
		//fail("Not yet implemented");
		OTPServiceImpl otpService=new OTPServiceImpl();
	    boolean expected=false;
	    boolean actual=otpService.checkEmail("abc@gmail.");
	    assertEquals(expected,actual);
	}

	@Test
	void testSendOTP() {
		//fail("Not yet implemented");
		OTPServiceImpl otpService=new OTPServiceImpl();
		assertThrows(Exception.class, ()->{
			Object obj = "divakar.1822cs1132@kiet.edu";
			otpService.sendOTP((OTP) obj);
	});
	}
	
	@Test
	void testVerifyOTP() {
		OTPServiceImpl otpService=new OTPServiceImpl();

		OTP otp = new OTP();
		assertThrows(Exception.class, () -> {
			otpService.validateOTP(otp);
		});
	}

	

}
