package dev.nj.tms.web.dto;

import jakarta.validation.constraints.NotBlank;

public record NewTaskDto(
        @NotBlank(message = "title should not be blank")
        String title,
        @NotBlank(message = "description should not be blank")
        String description
) {
}
