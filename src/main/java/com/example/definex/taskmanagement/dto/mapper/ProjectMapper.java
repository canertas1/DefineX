package com.example.definex.taskmanagement.dto.mapper;

import com.example.definex.taskmanagement.dto.request.CreateProjectRequest;
import com.example.definex.taskmanagement.dto.response.CreatedProjectResponse;
import com.example.definex.taskmanagement.dto.response.ProjectResponse;
import com.example.definex.taskmanagement.entities.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    CreateProjectRequest projectToCreateProjectRequest(Project project);

    Project createProjectRequestToProject(CreateProjectRequest createProjectRequest);

    @Mapping(source = "department.id", target = "departmentId")
    CreatedProjectResponse projectToCreatedProjectResponse(Project project);
    Project createdProjectResponseToProject(CreatedProjectResponse createdProjectResponse);
    Project projectResponseToProject(ProjectResponse projectResponse);
    ProjectResponse projectToProjectResponse(Project project);
}
