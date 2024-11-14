package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserResponseDTO;
import com.umaxcode.spring_boot_essentials_with_crud.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser_ShouldReturnCreatedUser_WhenRequestIsValid() throws Exception {
        UserRequestDTO request = new UserRequestDTO("maxwell", "password123");
        String id = UUID.randomUUID().toString();
        UserResponseDTO response = new UserResponseDTO(id, "maxwell", "password123");

        when(userService.addUser(any(UserRequestDTO.class))).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("maxwell"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    void getUser_ShouldReturnUser_WhenUserExists() throws Exception {
        String id = UUID.randomUUID().toString();

        UserResponseDTO response = new UserResponseDTO(id, "maxwell", "password123");

        when(userService.findUserById(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("maxwell"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() throws Exception {

        String id1 = UUID.randomUUID().toString();
        UserResponseDTO user1 = new UserResponseDTO(id1, "maxwell", "password123");

        String id2 = UUID.randomUUID().toString();
        UserResponseDTO user2 = new UserResponseDTO(id2, "maxwell2", "password456");


        List<UserResponseDTO> response = Arrays.asList(
                user1,
                user2
        );

        when(userService.findAllUsers()).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1))
                .andExpect(jsonPath("$[1].id").value(id2));
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser_WhenRequestIsValid() throws Exception {
        String id = UUID.randomUUID().toString();
        UserRequestDTO request = new UserRequestDTO("new-maxwell", "new-password");
        UserResponseDTO updatedUserResponse = new UserResponseDTO(id,"new-maxwell", "new-password");

        when(userService.updateUser(id, request)).thenReturn(updatedUserResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("new-maxwell"))
                .andExpect(jsonPath("$.password").value("new-password"));
    }

    @Test
    void deleteUser_ShouldReturnConfirmationMessage_WhenUserIsDeleted() throws Exception {
        String id = UUID.randomUUID().toString();

        Mockito.doNothing().when(userService).deleteUser(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted user with id = " + id));
    }
}
