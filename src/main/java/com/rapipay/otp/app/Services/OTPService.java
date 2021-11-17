package com.rapipay.otp.app.Services;

import com.rapipay.otp.entity.OTP;
import com.rapipay.otp.exception.InvalidInputException;

public interface OTPService {
	public String sendOTP(OTP otp) throws InvalidInputException;
	public String validateOTP(OTP otp);

}
