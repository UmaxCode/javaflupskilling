package com.umaxcode.spring_boot_essentials_with_crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umaxcode.spring_boot_essentials_with_crud.model.dto.UserRequestDTO;
import com.umaxcode.spring_boot_essentials_with_crud.model.entity.User;
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
        User user = new User("maxwell", "password123");
        String id = UUID.randomUUID().toString();
        user.setId(id);

        when(userService.addUser(any(UserRequestDTO.class))).thenReturn(user);

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
        User user = new User("maxwell", "password123");
        user.setId(id);

        when(userService.findUserById(id)).thenReturn(user);

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
        User user1 = new User("maxwell1", "password123");
        user1.setId(id1);

        String id2 = UUID.randomUUID().toString();
        User user2 = new User("maxwell2", "password456");
        user2.setId(id2);

        List<User> users = Arrays.asList(
                user1,
                user2
        );

        when(userService.findAllUsers()).thenReturn(users);

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
        User updatedUser = new User("new-maxwell", "new-password");
        updatedUser.setId(id);

        when(userService.updateUser(id, request)).thenReturn(updatedUser);

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
