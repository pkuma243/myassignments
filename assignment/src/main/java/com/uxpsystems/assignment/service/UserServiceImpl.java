package com.uxpsystems.assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uxpsystems.assignment.dao.UserDao;
import com.uxpsystems.assignments.model.User;

@Service(value="UserService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	@Autowired
	   private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	   @Transactional
	   @Override
	   public Long save(User User) {
	      return userDao.save(User);
	   }

	@Override
	   public User get(long id) {
	      return userDao.get(id);
	   }
	   
	   @Transactional
	   @Override
	   public boolean update(long id, User User) {
	      return userDao.update(id, User);
	   }

	   @Transactional
	   @Override
	   public boolean delete(long id) {
	      return userDao.delete(id);
	   }

	
	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

}
