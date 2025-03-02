package org.example.subapp.controller;

import java.util.Optional;
import org.example.subapp.entity.Subscription;
import org.example.subapp.entity.User;
import org.example.subapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.example.subapp.service.SubscriptionService;

@RestController
@RequestMapping
public class SubscriptionController {

  private final SubscriptionService subscriptionService;
  private final UserService userService;

  @Autowired
  public SubscriptionController(SubscriptionService subscriptionService, UserService userService) {
    this.subscriptionService = subscriptionService;
    this.userService = userService;
  }

  @PostMapping("/users/{userId}/subscriptions")
  public ResponseEntity<?> addSubscription(@PathVariable Long userId, @RequestBody Subscription subscription) {
    User user = userService.getUser(userId);

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    boolean subscriptionExists = user.getSubscriptions().stream()
                                     .anyMatch(existingSubscription -> existingSubscription
                                         .getServiceName().equals(subscription.getServiceName()));

    if (subscriptionExists) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have this subscription.");
    }

    subscription.setUser(user);
    subscriptionService.addSubscription(subscription);
    return ResponseEntity.status(HttpStatus.CREATED).body("Subscription added successfully.");
  }

  @GetMapping("/users/{userId}/subscriptions")
  public ResponseEntity<List<Subscription>> getUserSubscriptions(@PathVariable Long userId) {

    List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
    if (subscriptions.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(subscriptions);
    }

    return ResponseEntity.ok(subscriptionService.getUserSubscriptions(userId));
  }

  @DeleteMapping("/users/{userId}/subscriptions/{subId}")
  public ResponseEntity<String> removeSubscription(@PathVariable Long subId, @PathVariable Long userId) {

    User user = userService.getUser(userId);
    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    Optional<Subscription> subscriptionOptional = subscriptionService.getSubscriptionById(subId);
    if (subscriptionOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription not found.");
    }

    Subscription subscription = subscriptionOptional.get();

    if (!subscription.getUser().equals(user)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Subscription does not belong to this user.");
    }

    subscriptionService.removeSubscription(subId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Subscription removed.");
  }

  @GetMapping("/subscriptions/top")
  public ResponseEntity<List<String>> getTopSubscriptions() {
    List<String> topSubscriptions = subscriptionService.getTopSubscriptions();

    if (topSubscriptions.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(topSubscriptions);
    }

    return ResponseEntity.ok(topSubscriptions);
  }

}