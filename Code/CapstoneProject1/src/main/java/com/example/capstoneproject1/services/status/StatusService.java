package com.example.capstoneproject1.services.status;

import com.example.capstoneproject1.models.SpaceStatus;

import java.util.Optional;

public interface StatusService {
    Optional<SpaceStatus> findBySpaceStatusId(Integer spaceStatusId);
}
