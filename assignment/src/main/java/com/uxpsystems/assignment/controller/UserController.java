package com.uxpsystems.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uxpsystems.assignment.service.UserService;
import com.uxpsystems.assignments.model.User;



@RestController
public class UserController {
	 @Autowired
	   private UserService userService;

	   /*---Add new User---*/
	   @PostMapping("/user")
	   public ResponseEntity<?> save(@RequestBody User user) {
	      Long id = userService.save(user);
	      if(id!=null) {
	      return ResponseEntity.ok().body("New User has been saved with ID:" + id);
	      }else {
	    	  return ResponseEntity.ok().body("This user is alraedy exixt kindly try with unique user:");
	      }
	   }

	   /*---Get a User by id---*/
	   @GetMapping("/user/{id}")
	   public ResponseEntity<?> get(@PathVariable("id") long id) {
	      User user = userService.get(id);
	      if(user!=null) {
	      return ResponseEntity.ok().body(user);
	      }else {
	    	  return ResponseEntity.ok().body("User doesn't exit with ID.."+id); 
	      }
	   }

	   /*---get all books---*/
	   @GetMapping("/users")
	   public ResponseEntity<List<User>> getAllUser() {
	      List<User> users = userService.getAllUser();
	      return ResponseEntity.ok().body(users);
	   }

	   /*---Update a User by id---*/
	   @PutMapping("/update/{id}")
	   public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody User user) {
	      boolean status=userService.update(id, user);
	      if(status) {
	      return ResponseEntity.ok().body("User has been updated successfully.");
	      }else {
	    	  return ResponseEntity.ok().body("User doesn't exixt for update."); 
	      }
	   }

	   /*---Delete a User by id---*/
	   @DeleteMapping("/delete/{id}")
	   public ResponseEntity<?> delete(@PathVariable("id") long id) {
	      boolean status=userService.delete(id);
	      if(status) {
	      return ResponseEntity.ok().body("User has been deleted successfully.");
	      }else {
	    	  return ResponseEntity.ok().body("User doesnot exixt with id>>>."+id);  
	      }
	   }
}
