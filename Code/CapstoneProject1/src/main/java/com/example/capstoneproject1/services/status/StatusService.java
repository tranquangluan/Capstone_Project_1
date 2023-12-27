package com.example.capstoneproject1.services.status;

import com.example.capstoneproject1.models.Status;

import java.util.Optional;

public interface StatusService {
    Optional<Status> findBySpaceStatusId(Integer spaceStatusId);
    Optional<Status> findById(Integer integer);
}
