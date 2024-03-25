package com.jcode.ebookpedia.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcode.ebookpedia.user.dto.UserDto;
import com.jcode.ebookpedia.user.mapper.UserDetailsMapper;
import com.jcode.ebookpedia.user.mapper.UserMapper;
import com.jcode.ebookpedia.user.model.UserAccount;
import com.jcode.ebookpedia.user.repository.UserAccountRepository;

@Service("userDetailsService")
public class UserAccountServiceImpl implements UserAccountService {

    // Dependencies
	
    private final UserAccountRepository userRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    // Constructor

    public UserAccountServiceImpl(UserAccountRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccountOptional = this.findByUsername(username);
        if (userAccountOptional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return UserDetailsMapper.build(userAccountOptional.get());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserAccount> getUserAccount(long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public UserAccount save(UserDto userData) {
    	UserAccount userAccount = UserMapper.INSTANCE.map(userData);
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userRepository.save(userAccount);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll() {
        List<UserAccount> users = userRepository.findAll(); 
        return users.stream().map(UserMapper.INSTANCE::map).collect(Collectors.toList());
    }

    @Override
    public Optional<UserAccount> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
