package com.boot.async.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot.async.api.entity.User;
import com.boot.async.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	Logger logger = LoggerFactory.getLogger(UserService.class);
	@Async
	public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws Exception{
		
		long start = System.currentTimeMillis();
		List<User> users = parseCsv(file);
		repository.saveAll(users);
		logger.info("Saving list of users of size {}", users.size(),""+Thread.currentThread().getName());
		long end = System.currentTimeMillis();
		logger.info("Total time {}", (end-start));
		return CompletableFuture.completedFuture(users);
	}
	
	@Async
	public CompletableFuture<List<User>> findAllUsers(){
		logger.info("get list of users by "+Thread.currentThread().getName());
		List<User> users = repository.findAll();
		
		return CompletableFuture.completedFuture(users);
	}
	
	private List<User> parseCsv(final MultipartFile file) throws Exception{
		final List<User> users = new ArrayList<>();
		
		try {
			try(final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
				String line;
				while((line = br.readLine()) != null) {
					final String[] data = line.split(",");
					final User user = new User();
					user.setFirtName(data[0]);
					user.setLastName(data[1]);
					user.setEmail(data[2]);
					
					users.add(user);
					
				}
				return users;
				
			}
		}catch(final IOException e) {
			logger.error("Failed to parse csv file {}", e);
			throw new Exception("Failed to parse csv File {}", e);
		}
	}
	
}
