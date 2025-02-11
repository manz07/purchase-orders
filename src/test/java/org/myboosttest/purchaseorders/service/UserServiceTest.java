package org.myboosttest.purchaseorders.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.myboosttest.purchaseorders.dto.request.UserRequest;
import org.myboosttest.purchaseorders.dto.response.UserResponse;
import org.myboosttest.purchaseorders.entity.Users;
import org.myboosttest.purchaseorders.exception.UserNotFoundException;
import org.myboosttest.purchaseorders.repository.UserRepository;
import org.myboosttest.purchaseorders.service.mapper.UserMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private Users user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        // Setup mock objects
        user = new Users(1, "Nurman", "Saleh", "nurman@example.com", "1234567890", "admin", "admin", null, null);
        userRequest = new UserRequest(1, "Nurman", "Saleh", "nurman@example.com", "1234567890", "creator", "updater");
        userResponse = new UserResponse(1, "Nurman", "Saleh", "nurman@example.com", "1234567890");

        // Initialize mock behavior for UserMapper
        when(userMapper.toUser(userRequest)).thenReturn(user);
        when(userMapper.fromUser(user)).thenReturn(userResponse);
    }

    @Test
    void testCreateUser() {
        // Arrange: Mock repository behavior
        when(userRepository.save(user)).thenReturn(user);

        // Act: Call service method
        Integer userId = userService.createUser(userRequest);

        // Assert: Verify behavior and results
        verify(userRepository, times(1)).save(user);
        assertNotNull(userId, "User ID should not be null");
        assertEquals(user.getId(), userId, "User ID should match");
    }

    @Test
    void testFindAllUsers() {
        // Arrange: Mock repository behavior
        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act: Call service method
        var users = userService.findAllUsers();

        // Assert: Verify behavior and results
        verify(userRepository, times(1)).findAll();
        assertNotNull(users, "Users list should not be null");
        assertEquals(1, users.size(), "Users list size should be 1");
        assertEquals(userResponse, users.getFirst(), "First user in the list should match the expected user");
    }

    @Test
    void testFindById() {
        // Arrange: Mock repository behavior
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act: Call service method
        UserResponse foundUser = userService.findById(1);

        // Assert: Verify behavior and results
        verify(userRepository, times(1)).findById(1);
        assertNotNull(foundUser, "User should be found");
        assertEquals(userResponse, foundUser, "Returned user should match the expected user");
    }

    @Test
    void testFindByIdThrowsUserNotFoundException() {
        // Arrange: Mock repository behavior for non-existing user
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(UserNotFoundException.class, () -> userService.findById(1), "Should throw UserNotFoundException if user does not exist");
    }

    @Test
    void testUpdateUser() {
        // Arrange: Mock repository behavior
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        // Act: Call service method to update user
        userService.updateUser(userRequest);

        // Assert: Verify behavior
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserThrowsUserNotFoundException() {
        // Arrange: Mock repository behavior for non-existing user
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect exception to be thrown
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userRequest), "Should throw UserNotFoundException if user does not exist");
    }

    @Test
    void testDeleteUser() {
        // Act: Call service method to delete user
        userService.deleteUser(1);

        // Assert: Verify behavior
        verify(userRepository, times(1)).deleteById(1);
    }
}
