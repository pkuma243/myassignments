package com.uxpsystems.assignment.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uxpsystems.assignments.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long save(User user) {
		List userobj = this.getUserByName(user.getUserName());
		if (userobj.size() == 0) {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
			return user.getId();
		} else {
			return null;
		}
	}

	@Override
	public User get(long id) {
		return sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public List<User> getAllUser() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		Query<User> query = session.createQuery(cq);
		return query.getResultList();
	}

	@Override
	public boolean update(long id, User User) {
		Session session = sessionFactory.getCurrentSession();
		User userobj = session.byId(User.class).load(id);
		if (userobj != null) {
			userobj.setUserName(User.getUserName());
			userobj.setPassword(User.getPassword());
			userobj.setStatus(User.getStatus());
			session.flush();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		User user = session.byId(User.class).load(id);
		if (user != null) {
			session.delete(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List getUserByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT E.userName FROM User E WHERE E.userName = :userName";
		Query query = session.createQuery(hql);
		query.setParameter("userName", name);
		return query.list();
	}

}
