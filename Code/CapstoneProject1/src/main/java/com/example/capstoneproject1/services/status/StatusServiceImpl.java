package com.example.capstoneproject1.services.status;

import com.example.capstoneproject1.models.Status;
import com.example.capstoneproject1.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusServiceImpl implements  StatusService {

    @Autowired
    StatusRepository statusRepository;

    @Override
    public Optional<Status> findBySpaceStatusId(Integer spaceStatusId) {
        return statusRepository.findById(spaceStatusId);
    }
}
