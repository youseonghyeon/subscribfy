package com.example.subscribify.repository;

import com.example.subscribify.entity.CustomerSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionInfoRepository extends JpaRepository<CustomerSubscription, Long> {
}
