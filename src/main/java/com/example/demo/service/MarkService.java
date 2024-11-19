package com.example.demo.service;

import com.example.demo.model.Mark;

import java.util.List;

public interface MarkService {
    List<Mark> getAll();
    Mark addMark(Mark mark);
    Mark updateMark(Mark mark);
    void deleteMark(Long markId);
}
