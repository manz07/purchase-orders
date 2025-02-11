package org.myboosttest.purchaseorders.dto.response;

public record UserResponse(
        Integer id,
        String firstname,
        String lastname,
        String email,
        String phone
) {
}
