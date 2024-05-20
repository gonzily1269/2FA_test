package com.example.account;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

	private final JdbcTemplate jdbcTemplate;

	public AccountService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Account findByUsername(String username) {
		return this.jdbcTemplate.queryForObject(
				"SELECT username, password FROM account WHERE username = ?",
				new DataClassRowMapper<>(Account.class), username);
	}

	@Transactional
	public int insert(Account account) {
		return this.jdbcTemplate.update(
				"INSERT INTO account(username, password) VALUES (?, ?)",
				account.username(), account.password());
	}
}
