package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTopicId(Long topicId);

    @Query("SELECT a FROM Article a WHERE a.topic.id IN (SELECT t.id FROM User u JOIN u.subscriptions t WHERE u.id = :userId)")
    List<Article> findArticlesByUserSubscriptions(@Param("userId") Long userId);
}
