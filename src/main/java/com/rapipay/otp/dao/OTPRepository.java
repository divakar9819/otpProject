package com.rapipay.otp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rapipay.otp.entity.OTP;

public interface OTPRepository extends JpaRepository<OTP, Integer> {
	List<OTP> findByEmailAndOtpAndVerified(String email, Integer otp,boolean verified);
    List<OTP> findByEmailAndVerified(String email,boolean verified);
}
