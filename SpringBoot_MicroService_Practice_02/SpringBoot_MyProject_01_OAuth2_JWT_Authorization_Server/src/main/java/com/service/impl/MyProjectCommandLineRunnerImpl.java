package com.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.model.User;
import com.repository.UserRepository;
import com.service.AccountKeyService;
import com.service.MyProjectCommandLineRunnner;
import com.service.OneTimePasswordService;
import com.util.ProxyServer;

@Component
public class MyProjectCommandLineRunnerImpl implements CommandLineRunner, MyProjectCommandLineRunnner{
	
	private UserRepository userRepository;
	private AccountKeyService accountKeyService;
	private OneTimePasswordService oneTimePasswordService;
	private ProxyServer proxyServer;



	public MyProjectCommandLineRunnerImpl(UserRepository userRepository, AccountKeyService accountKeyService,
			OneTimePasswordService oneTimePasswordService, ProxyServer proxyServer) {
		this.userRepository = userRepository;
		this.accountKeyService = accountKeyService;
		this.oneTimePasswordService = oneTimePasswordService;
		this.proxyServer = proxyServer;
	}

	public void run(String... args) throws Exception {
		dropUsersTable();
		createUsersTable();
		dropAccountKeyTable();
		createAccountKeyTable();
		dropOtpTable();
		createOtpTable();
		createDummyUser();
	}

	public void createUsersTable() {
		userRepository.createUsersTable();
	}

	public void dropUsersTable() {
		userRepository.dropUsersTable();
	}

	public void createDummyUser() {
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		User user = new User();
		UUID uuid = UUID.randomUUID();	
		user.setId(uuid.toString());
		user.setEmail("eu@fa.hu");
	    user.setPassword(new BCryptPasswordEncoder().encode("myPassword"));
	    user.setActive(true);
	    user.setMfa(false);
		userRepository.registerUser(user, "user");
		proxyServer.sendNewUserId(userRepository.findByEmail(user.getEmail()).getId());  
		
		User user2 = new User();
		UUID uuid2 = UUID.randomUUID();	
		user2.setId(uuid2.toString());
		user2.setEmail("admin@fa.hu");
	    user2.setPassword(new BCryptPasswordEncoder().encode("myAdmin"));
	    user2.setActive(true);
	    user2.setMfa(true);
		userRepository.registerUser(user2, "user admin");
		proxyServer.sendNewUserId(userRepository.findByEmail(user2.getEmail()).getId()); 
	}

	@Override
	public void createAccountKeyTable() {
		accountKeyService.createAccountKeyTable();	
	}

	@Override
	public void dropAccountKeyTable() {
		accountKeyService.dropAccountKeyTable();	
	}

	@Override
	public void createOtpTable() {
		oneTimePasswordService.createOneTimePasswordTable();
		
	}

	@Override
	public void dropOtpTable() {
		oneTimePasswordService.dropOneTimePasswordTable();
	}
}
