package org.example.subapp.service;

import java.util.Optional;
import java.util.stream.Collectors;
import org.example.subapp.entity.Subscription;
import org.example.subapp.repo.SubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;
  private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

  @Autowired
  public SubscriptionService(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public void addSubscription(Subscription subscription) {
    logger.info("Adding subscription: {}", subscription.getServiceName());
    subscriptionRepository.save(subscription);
  }

  public List<Subscription> getUserSubscriptions(Long userId) {
    logger.info("Fetching subscriptions for user ID: {}", userId);
    return subscriptionRepository.findByUserId(userId);
  }

  public void removeSubscription(Long id) {
    logger.info("Removing subscription with ID: {}", id);
    subscriptionRepository.deleteById(id);
  }

  public List<String> getTopSubscriptions() {
    List<String> topSubscriptions = subscriptionRepository.findTopSubscriptions();

    return topSubscriptions.stream().limit(3).collect(Collectors.toList());
  }

  public Optional<Subscription> getSubscriptionById(Long subId) {
    return subscriptionRepository.findById(subId);
  }
}