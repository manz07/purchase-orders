package org.myboosttest.purchaseorders.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Validated
public record UserRequest(
        Integer id,
        @NotNull(message = "Firstname is required")
        String firstname,
        @NotNull(message = "Lastname is required")
        String lastname,
        @NotNull(message = "Email is required")
        @Email(message = "The user email is not correctly formatted")
        String email,
        @NotNull(message = "Phone is required")
        String phone,
        String createdBy,
        String updatedBy,
        LocalDateTime createdDateTime

) {
}