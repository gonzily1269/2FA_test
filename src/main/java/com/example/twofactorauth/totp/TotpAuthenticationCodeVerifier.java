package com.example.twofactorauth.totp;

import com.example.account.Account;
import com.example.twofactorauth.TwoFactorAuthenticationCodeVerifier;

public class TotpAuthenticationCodeVerifier implements TwoFactorAuthenticationCodeVerifier {

	@Override
	public boolean verify(Account account, String code) {
		// TODO:一旦素通し
		return true;
	}

}
