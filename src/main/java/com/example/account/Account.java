package com.example.account;

import java.io.Serializable;

public record Account(String username, String password) implements Serializable {

	public static Account account(String username, String password) {
		return new Account(username, password);
	}
}
