package com.test.user.dao;

import com.test.user.dao.model.User;

public interface UserDao {

    User findByUsername(String username);
    int createUser(String username, String password);
}
