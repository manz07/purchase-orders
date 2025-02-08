package org.myboosttest.purchaseorders.dto;

public record UserResponse(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String phone
) {
}
