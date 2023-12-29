package com.example.capstoneproject1.services.feedback;

import com.example.capstoneproject1.dto.response.feedback.PageFeedback;
import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;

import java.util.Optional;

public interface FeedbackService {

    void saveFeedback(Feedback feedback);
    Optional<Feedback> findById(Integer feedbackId);

    Boolean existsFeedbackBySenderAnsReceiver(User sender, User receiver);

    void deleteFeedback(Feedback feedback);

    Optional<Feedback> findFeedbackBySenderAnsReceiver(User sender, User receiver);

    Boolean isFeedback (User user, User owner);

    PageFeedback getFeedback(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer rateFrom,  Integer rateTo,Integer receiverId, Integer senderId);
}
