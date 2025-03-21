package com.example.definex.taskmanagement.dto.request;

import com.example.definex.taskmanagement.entities.Project;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateDepartmentRequest {

    @NotNull
    private String name;

}
