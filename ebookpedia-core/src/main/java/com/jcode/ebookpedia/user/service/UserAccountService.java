package com.jcode.ebookpedia.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jcode.ebookpedia.user.dto.UserDto;
import com.jcode.ebookpedia.user.model.UserAccount;

public interface UserAccountService extends UserDetailsService {

    Optional<UserAccount> getUserAccount(long id);

    UserAccount save(UserDto user);

    List<UserDto> findAll();

    Optional<UserAccount> findByUsername(String username);
}
