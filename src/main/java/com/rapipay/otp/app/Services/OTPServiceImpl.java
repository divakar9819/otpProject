package com.rapipay.otp.app.Services;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rapipay.otp.dao.OTPRepository;
import com.rapipay.otp.entity.OTP;
import com.rapipay.otp.entity.OTPResponse;
import com.rapipay.otp.exception.EmailNotFoundException;
import com.rapipay.otp.exception.InvalidAttemptsException;
import com.rapipay.otp.exception.InvalidEmailException;
import com.rapipay.otp.exception.InvalidInputException;
import com.rapipay.otp.exception.InvalidOTPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPServiceImpl implements OTPService {

	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	public EmailService emailService;

	public Integer generateOTP() {
		return (int) ((Math.random() * 900000) + 100000);
	}

	public boolean checkEmail(String email) {
		String regex = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w{2,}([-.]\\w+)*$";
		Pattern pattern = Pattern.compile(regex);
		// Creating a Matcher object
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();

	}

	@Override
	public String sendOTP(OTP otp) throws InvalidInputException {
		
		if(otp.getChannelName()==null ||otp.getEmail()==null) {
			throw new InvalidInputException("channel name  or email can't be empty");
		}
		List<OTP> checkEmail = otpRepository.findByEmailAndVerified(otp.getEmail(), false);
		int attempt = 1;
		for (OTP tempOTP : checkEmail) {
			System.out.println("----------");
			if (tempOTP.getCreated_at() > (new Date().getTime() / 1000) - 300) {
				attempt++;
				if (attempt > 2) {
					throw new InvalidAttemptsException("Try After sometime!!");
				}

			}
		}
		if (checkEmail(otp.getEmail())) {
			OTP otpObj = new OTP();
			//otpObj.setOrderId(otp.getOrderId());
			otpObj.setEmail(otp.getEmail());
			otpObj.setOtp(generateOTP());
			otpObj.setVerified(false);
			otpObj.setCreated_at(new Date().getTime() / 1000);
			otpObj.setChannelName(otp.getChannelName());
			sendOTPMail(otpObj);
			otpRepository.saveAndFlush(otpObj);
			return "OTP sent successfully";
		}
		throw new InvalidEmailException("Entered Email is Wrong!!");
	}

	@Override
	public String validateOTP(OTP otp) {
		OTPResponse response = new OTPResponse();
		List<OTP> checkEmail = otpRepository.findByEmailAndVerified(otp.getEmail(), false);

		if (checkEmail == null || checkEmail.size() == 0) {
			response.setMessage("-------------");
			response.setStatus(false);
			System.out.println("-----------------------------------");
			throw new EmailNotFoundException("Email Not Exixts");

		}

		List<OTP> otps;
		otps = (otpRepository.findByEmailAndOtpAndVerified(otp.getEmail(), otp.getOtp(), false));

		if (otps == null || otps.size() == 0) {
			response.setMessage("OTP verification failed");
			response.setStatus(false);
			response.toString();
			throw new InvalidOTPException("Entered OTP is Wrong!");
		}

		
		for (OTP tempOtp : otps) {
			if ((tempOtp.getCreated_at() > (new Date().getTime() / 1000) - 300)) {
				
					tempOtp.setVerified(true);
					otpRepository.save(tempOtp);
					response.setMessage("OTP verified successfully");
					response.setStatus(true);
					return "OTP verified successfully";
				
			}
		}
		
			response.setMessage("This OTP has expired. Please request a new one");
			response.setStatus(false);
			return response.toString();
		
	}

	public void sendOTPMail(OTP otp) {
		int order = (int) (Math.random()*1000);
		emailService.sendSimpleMessage(otp.getEmail(), "OTP for email verification",
				"The orderId  is: "+order+"\n otp is:" + otp.getOtp());

	}
}
