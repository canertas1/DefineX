package com.example.definex.taskmanagement.dto.mapper;

import com.example.definex.taskmanagement.dto.request.CreateCommentRequest;
import com.example.definex.taskmanagement.dto.request.UpdateCommentRequest;
import com.example.definex.taskmanagement.dto.response.CommentResponse;
import com.example.definex.taskmanagement.dto.response.CreatedCommentResponse;
import com.example.definex.taskmanagement.dto.response.UpdatedCommentResponse;
import com.example.definex.taskmanagement.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CreateCommentRequest commentToCreateCommentRequest(Comment comment);
    Comment createCommentRequestToComment(CreateCommentRequest createCommentRequest);
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "user.id", target = "userId")
    CreatedCommentResponse commentToCreatedCommentResponse(Comment comment);
    Comment createdCommentResponseToComment(CreatedCommentResponse createdCommentResponse);
    Comment updateCommentRequestToComment(UpdateCommentRequest updateCommentRequest);
    UpdateCommentRequest commentToUpdateCommentRequest(Comment comment);
    Comment updatedCommentResponseToComment(UpdatedCommentResponse updatedCommentResponse);
    UpdatedCommentResponse commentToUpdatedCommentResponse(Comment comment);
    Comment commentResponseToComment(CommentResponse commentResponse);
    @Mapping(source = "task.id", target = "taskId")
    @Mapping(source = "user.id", target = "userId")
    CommentResponse commentToCommentResponse(Comment comment);
}
