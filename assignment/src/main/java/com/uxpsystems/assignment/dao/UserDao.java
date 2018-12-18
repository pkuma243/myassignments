package com.uxpsystems.assignment.dao;

import java.util.List;

import com.uxpsystems.assignments.model.User;



public interface UserDao {
	   Long save(User User);

	   User get(long id);

	   List<User> getAllUser();

	   boolean update(long id, User User);

	   boolean delete(long id);
	   
	   List getUserByName(String name);
}
