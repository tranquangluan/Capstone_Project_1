package com.example.capstoneproject1.services.feedback;

import com.example.capstoneproject1.dto.response.feedback.PageFeedback;
import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void deleteFeedback(Feedback feedback) {
        feedbackRepository.delete(feedback);
    }

    @Override
    public Optional<Feedback> findFeedbackBySenderAnsReceiver(User sender, User receiver) {
        return feedbackRepository.findByUserSendFeedBackAndUserReceiveFeedBack(sender, receiver);
    }

    @Override
    public PageFeedback getFeedback(Integer pageNo, Integer pageSize, String sortBy, String sortDir, Integer rateFrom,  Integer rateTo, Integer receiverId, Integer senderId) {
        try {
            if (sortDir != "None") {
                // Create Sorted instance
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
                Page<Feedback> pageFeedback = feedbackRepository.findFeedbacksByConditions( rateFrom, rateTo, receiverId, senderId, pageable);
                Integer totalPages = pageFeedback.getTotalPages();
                List<Feedback> listFeedbacks = pageFeedback.getContent();
                return new PageFeedback(totalPages, listFeedbacks);
            }else {
                Pageable pageable = PageRequest.of(pageNo, pageSize);
                Page<Feedback> pageFeedback = feedbackRepository.findFeedbacksByConditions( rateFrom, rateTo, receiverId, senderId, pageable);
                Integer totalPages = pageFeedback.getTotalPages();
                List<Feedback> listFeedbacks = pageFeedback.getContent();
                return new PageFeedback(totalPages, listFeedbacks);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new PageFeedback();
        }
    }
}
