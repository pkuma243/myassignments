package com.uxpsystems.assignment.service;

import java.util.List;

import com.uxpsystems.assignments.model.User;

public interface UserService {
	Long save(User book);
	   User get(long id);
	   List<User> getAllUser();
	   boolean update(long id, User book);
	   boolean delete(long id);
}
