package com.example.capstoneproject1.dto.response.space;

import com.example.capstoneproject1.models.Space;

import java.util.List;

public class PageSpace {

    private Integer totalPages;
    private List<Space> listSpaces;

    public PageSpace(Integer totalPages, List<Space> listSpaces) {
        this.totalPages = totalPages;
        this.listSpaces = listSpaces;
    }

    public PageSpace() {
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Space> getListSpaces() {
        return listSpaces;
    }

    public void setListSpaces(List<Space> listSpaces) {
        this.listSpaces = listSpaces;
    }
}
