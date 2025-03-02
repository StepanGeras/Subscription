package org.example.subapp.controller;

import org.example.subapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.subapp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody User user) {

    if (user == null || user.getName() == null || user.getEmail() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name and email must not be null.");
    }

    try {
      User createdUser = userService.createUser(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user.");
    }

  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id) {
    User user = userService.getUser(id);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }
    return ResponseEntity.ok(user);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
    if (user == null || user.getName() == null || user.getEmail() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User name and email must not be null.");
    }

    try {
      User updatedUser = userService.updateUser(id, user);
      if (updatedUser == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
      }
      return ResponseEntity.ok(updatedUser);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user.");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable Long id) {
    try {
      User user = userService.getUser(id);

      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
      }

      userService.deleteUser(id);

      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User removed.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user.");
    }
  }
}