package com.example.definex.taskmanagement.service;

import com.example.definex.taskmanagement.authorization.impl.ProjectAuthorizationImpl;
import com.example.definex.taskmanagement.dto.mapper.ProjectMapper;
import com.example.definex.taskmanagement.dto.request.CreateProjectRequest;
import com.example.definex.taskmanagement.dto.response.CreatedProjectResponse;
import com.example.definex.taskmanagement.dto.response.ProjectResponse;
import com.example.definex.taskmanagement.entities.Department;
import com.example.definex.taskmanagement.entities.Project;
import com.example.definex.taskmanagement.exception.DepartmentNotFoundException;
import com.example.definex.taskmanagement.exception.ProjectNotFoundException;
import com.example.definex.taskmanagement.exception.ProjectValidationException;
import com.example.definex.taskmanagement.exception.UnauthorizedAccessException;
import com.example.definex.taskmanagement.exception.constants.MessageKey;
import com.example.definex.taskmanagement.repository.DepartmentRepository;
import com.example.definex.taskmanagement.repository.ProjectRepository;
import com.example.definex.taskmanagement.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ProjectServiceImplTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectAuthorizationImpl projectAuthorizationImpl;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private CreateProjectRequest createProjectRequest;
    private Project project;
    private Department department;
    private CreatedProjectResponse createdProjectResponse;
    private ProjectResponse projectResponse;
    private final Long PROJECT_ID = 1L;
    private final Long DEPARTMENT_ID = 1L;

    @BeforeEach
    void setUp() {
        createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setTitle("Test Project");

        department = new Department();
        department.setId(DEPARTMENT_ID);

        project = new Project();
        project.setId(PROJECT_ID);
        project.setTitle("Test Project");
        project.setDepartment(department);

        createdProjectResponse = new CreatedProjectResponse();
        createdProjectResponse.setTitle("Test Project");

        projectResponse = new ProjectResponse();
        projectResponse.setTitle("Test Project");
    }

    @Test
    void save_ShouldCreateProject_WhenValidRequest() {
        when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(projectMapper.createProjectRequestToProject(createProjectRequest)).thenReturn(project);
        when(projectMapper.projectToCreatedProjectResponse(project)).thenReturn(createdProjectResponse);
        doNothing().when(projectAuthorizationImpl).userHasAuthorization(project);
        when(projectRepository.save(project)).thenReturn(project);

        CreatedProjectResponse result = projectService.save(createProjectRequest, DEPARTMENT_ID);

        assertNotNull(result);
        assertEquals("Test Project", result.getTitle());
        verify(projectAuthorizationImpl, times(1)).userHasAuthorization(project);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void save_ShouldThrowProjectValidationException_WhenTitleIsEmpty() {
        createProjectRequest.setTitle("");

        ProjectValidationException exception = assertThrows(
                ProjectValidationException.class,
                () -> projectService.save(createProjectRequest, DEPARTMENT_ID)
        );
        assertEquals(MessageKey.PROJECT_TITLE_CANNOT_BE_EMPTY.toString(), exception.getMessage());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowProjectValidationException_WhenTitleIsNull() {
        createProjectRequest.setTitle(null);

        ProjectValidationException exception = assertThrows(
                ProjectValidationException.class,
                () -> projectService.save(createProjectRequest, DEPARTMENT_ID)
        );
        assertEquals(MessageKey.PROJECT_TITLE_CANNOT_BE_EMPTY.toString(), exception.getMessage());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowDepartmentNotFoundException_WhenDepartmentNotFound() {
        when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.empty());

        DepartmentNotFoundException exception = assertThrows(
                DepartmentNotFoundException.class,
                () -> projectService.save(createProjectRequest, DEPARTMENT_ID)
        );
        assertEquals(MessageKey.DEPARTMENT_NOT_FOUND_WITH_ID.toString(), exception.getMessage());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void save_ShouldThrowUnauthorizedAccessException_WhenUserNotAuthorized() {
        when(departmentRepository.findById(DEPARTMENT_ID)).thenReturn(Optional.of(department));
        when(projectMapper.createProjectRequestToProject(createProjectRequest)).thenReturn(project);
        doThrow(new UnauthorizedAccessException(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID))
                .when(projectAuthorizationImpl).userHasAuthorization(project);

        UnauthorizedAccessException exception = assertThrows(
                UnauthorizedAccessException.class,
                () -> projectService.save(createProjectRequest, DEPARTMENT_ID)
        );
        assertEquals(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID, exception.getMessage());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void findById_ShouldReturnProject_WhenProjectExists() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        doNothing().when(projectAuthorizationImpl).userHasAuthorization(project);
        when(projectMapper.projectToProjectResponse(project)).thenReturn(projectResponse);

        ProjectResponse result = projectService.findById(PROJECT_ID);

        assertNotNull(result);
        assertEquals("Test Project", result.getTitle());
        verify(projectAuthorizationImpl, times(1)).userHasAuthorization(project);
    }

    @Test
    void findById_ShouldThrowProjectNotFoundException_WhenProjectNotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(
                ProjectNotFoundException.class,
                () -> projectService.findById(PROJECT_ID)
        );
        assertEquals(MessageKey.PROJECT_NOT_FOUND_WITH_ID.toString(), exception.getMessage());
        verify(projectAuthorizationImpl, never()).userHasAuthorization(any());
    }

    @Test
    void findById_ShouldThrowUnauthorizedAccessException_WhenUserNotAuthorized() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        doThrow(new UnauthorizedAccessException(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID))
                .when(projectAuthorizationImpl).userHasAuthorization(project);

        UnauthorizedAccessException exception = assertThrows(
                UnauthorizedAccessException.class,
                () -> projectService.findById(PROJECT_ID)
        );
        assertEquals(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID, exception.getMessage());
        verify(projectMapper, never()).projectToProjectResponse(any());
    }

    @Test
    void deleteById_ShouldMarkProjectAsDeleted_WhenProjectExists() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        doNothing().when(projectAuthorizationImpl).userHasAuthorization(project);

        projectService.deleteById(PROJECT_ID);

        assertTrue(project.getIsDeleted());
        verify(projectAuthorizationImpl, times(1)).userHasAuthorization(project);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void deleteById_ShouldThrowProjectNotFoundException_WhenProjectNotFound() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.empty());

        ProjectNotFoundException exception = assertThrows(
                ProjectNotFoundException.class,
                () -> projectService.deleteById(PROJECT_ID)
        );
        assertEquals(MessageKey.PROJECT_NOT_FOUND_WITH_ID.toString(), exception.getMessage());
        verify(projectAuthorizationImpl, never()).userHasAuthorization(any());
        verify(projectRepository, never()).save(any());
    }

    @Test
    void deleteById_ShouldThrowUnauthorizedAccessException_WhenUserNotAuthorized() {
        when(projectRepository.findById(PROJECT_ID)).thenReturn(Optional.of(project));
        doThrow(new UnauthorizedAccessException(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID))
                .when(projectAuthorizationImpl).userHasAuthorization(project);

        UnauthorizedAccessException exception = assertThrows(
                UnauthorizedAccessException.class,
                () -> projectService.deleteById(PROJECT_ID)
        );
        assertEquals(MessageKey.USER_CANNOT_MANAGE_PROJECTS_IN_DEPARTMENT.toString() + DEPARTMENT_ID, exception.getMessage());
        verify(projectRepository, never()).save(any());
    }
}