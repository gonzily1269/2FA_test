package com.example.twofactorauth.web;

import java.io.IOException;

import com.example.account.Account;
import com.example.account.AccountService;
import com.example.account.AccountUserDetails;
import com.example.twofactorauth.TwoFactorAuthentication;
import com.example.twofactorauth.TwoFactorAuthenticationCodeVerifier;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwoFactorAuthController {

	private final TwoFactorAuthenticationCodeVerifier codeVerifier;

	private final AuthenticationSuccessHandler successHandler;

	private final AuthenticationFailureHandler failureHandler;

	public TwoFactorAuthController(AccountService accountService, TwoFactorAuthenticationCodeVerifier codeVerifier,
			AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
		this.codeVerifier = codeVerifier;
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
	}

	@GetMapping(path = "/challenge/totp")
	public String requestTotp() {
		return "totp";
	}

	@PostMapping(path = "/challenge/totp")
	public void processTotp(@RequestParam String code, TwoFactorAuthentication authentication,
			HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Authentication primaryAuthentication = authentication.getPrimary();
		AccountUserDetails accountUserDetails = (AccountUserDetails) primaryAuthentication.getPrincipal();
		Account account = accountUserDetails.getAccount();
		if (this.codeVerifier.verify(account, code)) {
			SecurityContextHolder.getContext().setAuthentication(primaryAuthentication);
			this.successHandler.onAuthenticationSuccess(request, response, primaryAuthentication);
		} else {
			this.failureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("Invalid code"));
		}
	}

}
