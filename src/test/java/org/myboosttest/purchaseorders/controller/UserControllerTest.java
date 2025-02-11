package org.myboosttest.purchaseorders.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.myboosttest.purchaseorders.dto.request.UserRequest;
import org.myboosttest.purchaseorders.dto.response.UserResponse;
import org.myboosttest.purchaseorders.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // Test creating User for valid data
    @Test
    void testCreateUser() throws Exception {
        UserRequest userRequest = new UserRequest(null, "Nurman", "", "Nurman@example.com", "123456789", null, null);

        when(userService.createUser(any(UserRequest.class))).thenReturn(1);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("User created successfully with ID: 1"));

        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    // Test creating User with invalid data
    @Test
    void testCreateUserWithInvalidData() throws Exception {
        // Invalid email format and missing firstname
        UserRequest invalidUserRequest = new UserRequest(null, null, "", "invalid-email", "123456789", null, null);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath("$.message").value("Validation failed"));

        verify(userService, times(0)).createUser(any(UserRequest.class));
    }

    // Test for finding all users
    @Test
    void testFindAllUsers() throws Exception {
        UserResponse userResponse1 = new UserResponse(1, "Nurman ", "Saleh", "Nurman.@example.com", "2893882");
        UserResponse userResponse2 = new UserResponse(2, "Naruto", "Uzu", "Naruto@example.com", "2329912");
        List<UserResponse> users = Arrays.asList(userResponse1, userResponse2);

        when(userService.findAllUsers()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Nurman "))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Naruto"));

        verify(userService, times(1)).findAllUsers();
    }

    // Test for finding a user by ID
    @Test
    void testFindUserById() throws Exception {
        UserResponse userResponse = new UserResponse(1, "Nurman", "Saleh","nurman@example.com", "089228833");
        when(userService.findById(1)).thenReturn(userResponse);

        mockMvc.perform(get("/api/v1/users/{user-id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nurman"))
                .andExpect(jsonPath("$.email").value("nurman@example.com"));

        verify(userService, times(1)).findById(1);
    }

    // Test for updating a user
    @Test
    void testUpdateUser() throws Exception {
        UserRequest userRequest = new UserRequest(1, "Nurman", "Saleh", "nurman@example.com", "987654321", null, null);

        doNothing().when(userService).updateUser(any(UserRequest.class));

        mockMvc.perform(put("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isAccepted());

        verify(userService, times(1)).updateUser(any(UserRequest.class));
    }

    // Test for deleting a user
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1);

        mockMvc.perform(delete("/api/v1/users/{user-id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("User with ID :: 1 has been deleted"));

        verify(userService, times(1)).deleteUser(1);
    }
}
