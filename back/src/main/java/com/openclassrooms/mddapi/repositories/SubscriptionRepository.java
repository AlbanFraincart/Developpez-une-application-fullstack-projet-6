package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndSubscriptions_Id(Long userId, Long topicId);
}
