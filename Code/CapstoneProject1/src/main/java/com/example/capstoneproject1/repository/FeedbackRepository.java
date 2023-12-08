package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findById(Integer integer);

    Boolean existsByUserSendFeedBackAndUserReceiveFeedBack(User sender, User receiver);

    Optional<Feedback> findByUserSendFeedBackAndUserReceiveFeedBack(User sender, User receiver);
}
