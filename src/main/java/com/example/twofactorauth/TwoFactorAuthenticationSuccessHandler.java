package com.example.twofactorauth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class TwoFactorAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final AuthenticationSuccessHandler authenticationSuccessHandler;

	public TwoFactorAuthenticationSuccessHandler(String authUrl) {
		this.authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler(authUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(new TwoFactorAuthentication(authentication));
		this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
	}

}
