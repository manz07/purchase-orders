package org.myboosttest.purchaseorders.service;

import org.myboosttest.purchaseorders.dto.UserRequest;
import org.myboosttest.purchaseorders.dto.UserResponse;
import org.myboosttest.purchaseorders.entity.Users;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public Users toUser(UserRequest userRequest) {
        if (userRequest == null) {
            return null;
        }
        return Users.builder()
                .id(userRequest.id())
                .firstname(userRequest.firstname())
                .lastname(userRequest.lastname())
                .email(userRequest.email())
                .phone(userRequest.phone())
                .createdBy(userRequest.createdBy())
                .updatedBy(userRequest.updatedBy())
                .build();
    }

    public UserResponse fromUser(Users users) {
        return new UserResponse(
                users.getId(),
                users.getFirstname(),
                users.getLastname(),
                users.getEmail(),
                users.getPhone()
        );
    }
}
