package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Topic;
import com.openclassrooms.mddapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u.subscriptions FROM User u WHERE u.id = :userId")
    List<Topic> findSubscriptionsByUserId(@Param("userId") Long userId);

}
