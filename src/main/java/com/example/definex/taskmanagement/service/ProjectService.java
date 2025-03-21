package com.example.definex.taskmanagement.service;

import com.example.definex.taskmanagement.dto.request.CreateProjectRequest;
import com.example.definex.taskmanagement.dto.response.CreatedProjectResponse;
import com.example.definex.taskmanagement.dto.response.ProjectResponse;

public interface ProjectService {
    CreatedProjectResponse save(CreateProjectRequest createProjectRequest,Long departmentId);
    ProjectResponse findById(Long id);
    void deleteById(Long id);
}
