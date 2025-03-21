package com.example.definex.taskmanagement.dto.mapper;

import com.example.definex.taskmanagement.dto.request.CreateUserRequest;
import com.example.definex.taskmanagement.dto.response.CreatedUserResponse;
import com.example.definex.taskmanagement.dto.response.UserResponse;
import com.example.definex.taskmanagement.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    CreateUserRequest userToUserCreateUserRequest(User user);
    User createUserRequestToUser(CreateUserRequest createUserRequest);
    CreatedUserResponse userToCreatedUserResponse(User user);
    User createdUserResponseToUser(CreatedUserResponse createdUserResponse);
    UserResponse userToUserResponse(User user);
    User userReponseToUser(UserResponse userResponse);

}
