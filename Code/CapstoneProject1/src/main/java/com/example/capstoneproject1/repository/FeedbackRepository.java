package com.example.capstoneproject1.repository;

import com.example.capstoneproject1.models.Feedback;
import com.example.capstoneproject1.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Optional<Feedback> findById(Integer integer);

    Boolean existsByUserSendFeedBackAndUserReceiveFeedBack(User sender, User receiver);

    Optional<Feedback> findByUserSendFeedBackAndUserReceiveFeedBack(User sender, User receiver);

    @Query("SELECT f FROM Feedback f " +
            "WHERE (:rateFrom IS NULL OR f.rate >= :rateFrom)" +
            "AND (:rateTo IS NULL OR f.rate <= :rateTo) " +
            "AND (:receiverId IS NULL OR f.userReceiveFeedBack.id = :receiverId)" +
            "AND (:userId IS NULL OR f.userSendFeedBack.id = :userId)")
    Page<Feedback> findFeedbacksByConditions(
            @Param("rateFrom") Integer priceFrom,
            @Param("rateTo") Integer priceTo,
            @Param("receiverId") Integer receiverId,
            @Param("userId") Integer spaceId,
            Pageable pageable
    );
}
