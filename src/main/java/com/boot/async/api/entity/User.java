package com.boot.async.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "USER")
public class User {
	@Id
	@GeneratedValue
	private int id;
	private String firtName;
	private String lastName;
	private String email;
	
	
}
