package com.spring.rest.service.test;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.uxpsystems.assignment.dao.UserDaoImpl;
import com.uxpsystems.assignment.service.UserServiceImpl;
import com.uxpsystems.assignments.model.User;

public class UserServiceTest {
	private UserServiceImpl service;
	private UserDaoImpl repositoryMock;

	@Before
	public void setUp() {
		repositoryMock = mock(UserDaoImpl.class);
		service = new UserServiceImpl(repositoryMock);
	}

	@Test
	public void saveUser() {
		User user = new User();
		user.setId(1l);
		user.setUserName("Rakhi");
		user.setPassword("12345");
		user.setStatus("Active");

		service.save(user);

		ArgumentCaptor<User> userArgument = ArgumentCaptor.forClass(User.class);
		verify(repositoryMock, times(1)).save(userArgument.capture());
		verifyNoMoreInteractions(repositoryMock);

		User user1 = userArgument.getValue();
		assertThat(user1.getUserName(), is(user.getUserName()));
		assertThat(user1.getPassword(), is(user.getPassword()));
	}

	@Test
	public void deleteById() throws Exception {

		when(repositoryMock.delete(1l)).thenReturn(true);

		Boolean actual = service.delete(1l);

		verify(repositoryMock, times(1)).delete(1l);
		verifyNoMoreInteractions(repositoryMock);
		assertThat(actual, is(true));
	}

	@Test
	public void findAll() {
		List<User> users = new ArrayList<>();
		User user1 = new User();
		user1.setId(1l);
		user1.setUserName("Rakhi");
		user1.setPassword("12345");
		user1.setStatus("Active");

		User user2 = new User();
		user2.setId(2l);
		user2.setUserName("dilip");
		user2.setPassword("123456");
		user2.setStatus("Active");

		users.add(user1);
		users.add(user2);
		when(repositoryMock.getAllUser()).thenReturn(users);

		List<User> actual = service.getAllUser();

		verify(repositoryMock, times(1)).getAllUser();
		verifyNoMoreInteractions(repositoryMock);

		assertThat(actual, is(users));
	}

	@Test
	public void update() throws Exception {
		User user1 = new User();
		user1.setId(1l);
		user1.setUserName("Rakhi");
		user1.setPassword("12345");
		user1.setStatus("Active");

		when(repositoryMock.update(user1.getId(), user1)).thenReturn(true);

		Boolean actual = service.update(1l, user1);

		verify(repositoryMock, times(1)).update(user1.getId(), user1);
		verifyNoMoreInteractions(repositoryMock);

		assertThat(actual, is(true));
	}
}
