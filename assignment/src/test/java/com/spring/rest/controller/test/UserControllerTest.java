package com.spring.rest.controller.test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.spring.rest.test.config.TestContext;
import com.uxpsystems.assignment.config.AppConfig;
import com.uxpsystems.assignment.config.WebConfig;
import com.uxpsystems.assignment.controller.UserController;
import com.uxpsystems.assignment.service.UserService;
import com.uxpsystems.assignments.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestContext.class, AppConfig.class, WebConfig.class })
@WebAppConfiguration
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private UserService userServiceMock;
	// Add WebApplicationContext field here.
	@Autowired
	private WebApplicationContext webApplicationContext;
	// The setUp() method is omitted.
	private UserController controller;

	// private LocaleContextHolderWrapper localeHolderWrapperMock;

	private MessageSource messageSourceMock;

	@Before
	public void setUp() {
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		controller = new UserController();
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void add() throws Exception {
		User user = new User();
		user.setId(1l);
		user.setUserName("Rakhi");
		user.setPassword("12345");
		user.setStatus("Active");
		Long expected = 1l;
		when(userServiceMock.save(user)).thenReturn(expected);

		mockMvc.perform(
				post("/user").contentType(TestUtils.APPLICATION_JSON_UTF8).content(TestUtils.objectToJson(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteById() throws Exception {

		when(userServiceMock.delete(32L)).thenReturn(true);

		mockMvc.perform(delete("/delete/{id}", 32L)).andExpect(status().isOk());

		verify(userServiceMock, times(1)).delete(32L);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void findById() throws Exception {
		User user = new User();
		user.setId(1l);
		user.setUserName("Rakhi");
		user.setPassword("12345");
		user.setStatus("Active");

		when(userServiceMock.get(1L)).thenReturn(user);

		mockMvc.perform(get("/user/{id}", 1L)).andExpect(status().isOk())
				.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.userName", is("Rakhi"))).andExpect(jsonPath("$.password", is("12345")))
				.andExpect(jsonPath("$.status", is("Active")));

		verify(userServiceMock, times(1)).get(1L);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void update() throws Exception {
		User user = new User();
		user.setId(2l);
		user.setUserName("Rakhi");
		user.setPassword("1234599");
		user.setStatus("Active");

		when(userServiceMock.update(2l, user)).thenReturn(true);

		mockMvc.perform(put("/update/{id}",2l).contentType(TestUtils.APPLICATION_JSON_UTF8)
				.content(TestUtils.objectToJson(user))).andExpect(status().isOk());
	}

	@Test
	public void findAll() throws Exception {
		User first = new User();
		first.setId(28L);
		first.setUserName("Priyendu");
		first.setPassword("12345");
		first.setStatus("active");
		User second = new User();
		second.setId(29L);
		second.setUserName("Pawan");
		second.setPassword("123456");
		second.setStatus("active");

		when(userServiceMock.getAllUser()).thenReturn(Arrays.asList(first, second));

		mockMvc.perform(get("/users")).andExpect(status().isOk())
				.andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(28))).andExpect(jsonPath("$[0].userName", is("Priyendu")))
				.andExpect(jsonPath("$[0].password", is("12345"))).andExpect(jsonPath("$[1].status", is("active")))
				.andExpect(jsonPath("$[1].id", is(29))).andExpect(jsonPath("$[1].userName", is("Pawan")))
				.andExpect(jsonPath("$[1].password", is("123456"))).andExpect(jsonPath("$[1].status", is("active")));

		verify(userServiceMock, times(1)).getAllUser();
		verifyNoMoreInteractions(userServiceMock);
	}
}
