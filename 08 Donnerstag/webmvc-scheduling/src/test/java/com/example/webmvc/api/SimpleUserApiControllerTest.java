package com.example.webmvc.api;

import com.example.webmvc.controller.api.UserApiController;
import com.example.webmvc.model.NewsUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class SimpleUserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsUserRepository userRepository;
    private NewsUser testUser;

    @Before
    public void setup() {
        testUser = new NewsUser(
                "testfirst", "testlast",
                LocalDate.of(1970, 1, 1),
                "testuser",
                "testpass");
        when(userRepository.findAll())
                .thenReturn(Arrays.asList(testUser));
        when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(testUser);
        when(userRepository.save(any()))
                .thenReturn(testUser);
    }

    @Test
    public void simpleTest() throws Exception {
        mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("$._embedded.newsUserResourceList[0].username")
                        .value(testUser.getUsername()))
                .andExpect(jsonPath("$._embedded.newsUserResourceList[0].firstname")
                        .value(testUser.getFirstname()))
                .andExpect(jsonPath("$._embedded.newsUserResourceList[0].lastname")
                        .value(testUser.getLastname()))
                .andExpect(jsonPath("$._embedded.newsUserResourceList[0].birthday")
                        .value(testUser.getBirthday().toString()))
                .andExpect(jsonPath("$._embedded.newsUserResourceList[0]._links.self.href")
                        .value("http://localhost/api/" + testUser.getUsername()));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserByIdTest() throws Exception {
        mockMvc.perform(get("/api/testuser"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("$.username")
                        .value(testUser.getUsername()))
                .andExpect(jsonPath("$.firstname")
                        .value(testUser.getFirstname()))
                .andExpect(jsonPath("$.lastname")
                        .value(testUser.getLastname()))
                .andExpect(jsonPath("$.password")
                        .doesNotExist())
                .andExpect(jsonPath("$.birthday")
                        .value(testUser.getBirthday().toString()));
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void saveUserTest() throws Exception {
        String json = "{\"username\":\"testuser\"," +
                "\"firstname\":\"testfirst\"," +
                "\"lastname\":\"testlast\"," +
                "\"password\":\"testpass\"," +
                "\"birthday\":\"1970-01-01\"" +
                "}";
        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());

        ArgumentCaptor<NewsUser> argument =
                ArgumentCaptor.forClass(NewsUser.class);
        verify(userRepository, times(1))
                .save(argument.capture());
        NewsUser savedUser = argument.getValue();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getFirstname()).isEqualTo("testfirst");
        assertThat(savedUser.getLastname()).isEqualTo("testlast");
        assertThat(savedUser.getBirthday()).isEqualTo(LocalDate.of(1970, 1, 1));
        assertTrue(new BCryptPasswordEncoder()
                .matches("testpass", savedUser.getPassword()));
    }

    @Test
    public void notFoundTest() throws Exception {
        mockMvc.perform(get("/api/unknown"))
                .andExpect(status().isNotFound());
        verify(userRepository, times(1)).findByUsername("unknown");
    }

    @Test
    public void locationHeaderTest() throws Exception {
        String json = "{\"username\":\"testuser\"," +
                "\"firstname\":\"testfirst\"," +
                "\"lastname\":\"testlast\"," +
                "\"password\":\"testpass\"," +
                "\"birthday\":\"1970-01-01\"" +
                "}";
        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header()
                        .string("Location", "http://localhost/api/testuser"));
    }
}
