package com.example.capstoneproject1.services;

import com.example.capstoneproject1.models.Space;
import com.example.capstoneproject1.models.User;
import com.example.capstoneproject1.repository.SpaceRepository;
import com.example.capstoneproject1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public List<Space> search(BigDecimal priceMin,BigDecimal priceMax, float areaMin,float areaMax, Integer categoryId, String province, String district, String ward, String address) {
        List<Space> spaceList = spaceRepository.search(priceMin, priceMax, areaMin,areaMax, categoryId, province, district, ward, address);
        return spaceList;
    }

    @Override
    public List<Space> sortAsc(BigDecimal priceMin, BigDecimal priceMax, float areaMin, float areaMax, Integer categoryId, String province, String district, String ward, String address) {
        List<Space> spaceList = spaceRepository.sortAsc(priceMin, priceMax, areaMin,areaMax, categoryId, province, district, ward, address);
        return spaceList;
    }

    @Override
    public List<Space> sortDesc(BigDecimal priceMin, BigDecimal priceMax, float areaMin, float areaMax, Integer categoryId, String province, String district, String ward, String address) {
        List<Space> spaceList = spaceRepository.sortDesc(priceMin, priceMax, areaMin,areaMax, categoryId, province, district, ward, address);
        return spaceList;
    }

    @Override
    public Space detailSpace(Integer id) {
        return spaceRepository.detailSpace(id);
    }

    @Override
    public List<Space> getList() {
        return spaceRepository.getList();
    }

    @Override
    public Iterable<Space> findAll() {
        return (List<Space>) spaceRepository.findAll();
    }
}
