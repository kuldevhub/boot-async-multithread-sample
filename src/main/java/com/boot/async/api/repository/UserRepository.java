package com.boot.async.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.async.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
