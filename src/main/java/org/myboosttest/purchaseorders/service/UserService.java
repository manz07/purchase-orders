package org.myboosttest.purchaseorders.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.myboosttest.purchaseorders.dto.UserRequest;

import org.myboosttest.purchaseorders.dto.UserResponse;
import org.myboosttest.purchaseorders.entity.Users;
import org.myboosttest.purchaseorders.exception.UserNotFoundException;
import org.myboosttest.purchaseorders.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Integer createUser(UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        return userRepository.save(user).getId();
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::fromUser)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer userId) {
        return userRepository.findById(userId)
                .map(userMapper::fromUser)
                .orElseThrow(() -> new UserNotFoundException(format("No user found with the provided ID:: %s", userId)));
    }

    public void updateUser(UserRequest userRequest) {
        var user = userRepository.findById(userRequest.id())
                .orElseThrow(() -> new UserNotFoundException(format("Cannot update user:: No user found with provided ID:: %s", userRequest.id())
                ));
        mergeUser(user, userRequest);
        userRepository.save(user);
    }

    private void mergeUser(Users user, UserRequest userRequest) {
        if(StringUtils.isNotBlank(userRequest.firstname())) {
            user.setFirstname(userRequest.firstname());
        }
        if(StringUtils.isNotBlank(userRequest.lastname())) {
            user.setLastname(userRequest.lastname());
        }
        if(StringUtils.isNotBlank(userRequest.email())) {
            user.setEmail(userRequest.email());
        }
        if(StringUtils.isNotBlank(userRequest.phone())) {
            user.setPhone(userRequest.phone());
        }
    }


    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
