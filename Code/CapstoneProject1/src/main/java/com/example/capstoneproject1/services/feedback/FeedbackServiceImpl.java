package com.example.capstoneproject1.services.feedback;

import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackServiceImpl implements  FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public void saveFeedback(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(Integer feedbackId) {
        return feedbackRepository.findById(feedbackId);
    }

    @Override
    public Boolean existsFeedbackBySenderAnsReceiver(User sender, User receiver) {
        return feedbackRepository.existsByUserSendFeedBackAndUserReceiveFeedBack(sender, receiver);
    }

    @Override
    public Optional<Feedback> findFeedbackBySenderAnsReceiver(User sender, User receiver) {
        return feedbackRepository.findByUserSendFeedBackAndUserReceiveFeedBack(sender, receiver);
    }
}
