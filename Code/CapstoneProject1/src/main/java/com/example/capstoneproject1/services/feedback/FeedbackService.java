package com.example.capstoneproject1.services.feedback;

import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface FeedbackService {

    void saveFeedback(Feedback feedback);
    Optional<Feedback> findById(Integer feedbackId);

    Boolean existsFeedbackBySenderAnsReceiver(User sender, User receiver);

    Optional<Feedback> findFeedbackBySenderAnsReceiver(User sender, User receiver);
}
