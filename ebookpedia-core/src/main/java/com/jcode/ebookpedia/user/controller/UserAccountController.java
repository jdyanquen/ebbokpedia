package com.jcode.ebookpedia.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcode.ebookpedia.user.dto.UserDto;
import com.jcode.ebookpedia.user.model.UserAccount;
import com.jcode.ebookpedia.user.service.UserAccountService;

@RestController
@RequestMapping(value = "/v1/users")
public class UserAccountController {

	// Dependencies
    
    private final UserAccountService userAccountService;
    
    // Constructor

    public UserAccountController(@Qualifier("userDetailsService") UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	// @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public List<UserDto> getAllUserAccounts() {
        return userAccountService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserAccount> findUserAccount(@PathVariable String username) {
        Optional<UserAccount> userAccountOptional = userAccountService.findByUsername(username);
        if (userAccountOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userAccountOptional.get());
    }

    @PostMapping
    public void saveUserAccount(@RequestBody UserDto userData) {
        userAccountService.save(userData);
    }
}
