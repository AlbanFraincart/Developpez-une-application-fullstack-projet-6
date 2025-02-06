package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {}
