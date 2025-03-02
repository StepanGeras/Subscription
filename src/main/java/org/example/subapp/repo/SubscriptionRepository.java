package org.example.subapp.repo;

import org.example.subapp.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
  List<Subscription> findByUserId(Long userId);

  @Query("SELECT s.serviceName " +
      "FROM Subscription s " +
      "GROUP BY s.serviceName " +
      "ORDER BY COUNT(s) DESC")
  List<String> findTopSubscriptions();
}
