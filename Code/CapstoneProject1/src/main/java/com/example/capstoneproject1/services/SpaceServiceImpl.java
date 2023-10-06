package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.SpaceRepository;
import com.example.capstoneproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaceServiceImpl implements SpaceService{

    @Autowired
    private SpaceRepository spaceRepository;
    @Override
    public void update(Space space) {
        spaceRepository.save(space);
    }

    @Override
    public Space findById(Integer id) {
        return  spaceRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        spaceRepository.deleteById(id);
    }

    @Override
    public Iterable<Space> findAll() {
        return (List<Space>) spaceRepository.findAll();
    }
}
