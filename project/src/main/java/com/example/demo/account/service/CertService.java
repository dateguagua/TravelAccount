package com.example.demo.account.service;

import com.example.demo.account.except.users.CertException;
import com.example.demo.account.model.dto.UsersCert;

public interface CertService {

	UsersCert getCert(String username, String password) throws CertException;
}
