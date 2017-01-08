package com.test.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.user.dao.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public User findByUsername(String username) {
    	try{
    		return jdbcTemplate.queryForObject("SELECT * FROM user WHERE username = ?", new Object[] {username}, BeanPropertyRowMapper.newInstance(User.class));
    	}catch(EmptyResultDataAccessException e){
    		//This exception is ok, just means there is no user with that username
    	}
    	return null;
    }
    
    @Override
    public int createUser(String username, String password) {
    	int rows = 0;
       	try{
    		rows = jdbcTemplate.update("INSERT INTO user (username, password) VALUES (?,?);",username, password);
    	}catch (Exception e){
    		//I'm pretty sure doing this is a bad idea...
    		e.printStackTrace();
    	}
    	
    	return rows;
    }

}
