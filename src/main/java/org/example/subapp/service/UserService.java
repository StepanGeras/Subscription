package org.example.subapp.service;

import java.util.Optional;
import org.example.subapp.entity.User;
import org.example.subapp.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(User user) {
    logger.info("Creating user: {}", user.getName());
    return userRepository.save(user);
  }

  public User getUser(Long id) {
    logger.info("Fetching user with ID: {}", id);
    Optional<User> user = userRepository.findById(id);

    return user.orElse(null);
  }

  public User updateUser(Long id, User userDetails) {
    logger.info("Updating user with ID: {}", id);
    User user = getUser(id);
    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());
    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    logger.info("Deleting user with ID: {}", id);
    userRepository.deleteById(id);
  }

}
