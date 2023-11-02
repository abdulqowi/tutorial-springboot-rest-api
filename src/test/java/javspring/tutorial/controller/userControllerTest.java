package javspring.tutorial.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javspring.tutorial.userRepository;
import javspring.tutorial.model.RegisterUserRequest;
import javspring.tutorial.model.WebResponse;

@SpringBootTest
@AutoConfigureMockMvc

public class userControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private userRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void testRegister()throws Exception {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("Admin");
        request.setPassword("admin");
        request.setAddress("Bekasi");
        request.setPhone("000");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))  
        ).andExpectAll(MockMvcResultMatchers.status().isOk())
        .andDo(result -> {
          WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
            });
           assertEquals("Ok",response.getData());
        });
    }
}
