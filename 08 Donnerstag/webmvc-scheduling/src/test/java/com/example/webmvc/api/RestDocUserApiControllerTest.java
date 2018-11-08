package com.example.webmvc.api;

import com.example.webmvc.controller.api.UserApiController;
import com.example.webmvc.model.NewsUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class RestDocUserApiControllerTest {

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
    public void test() throws Exception {
        mockMvc.perform(get("/api/testuser"))
                .andExpect(status().isOk())
                .andExpect(
                        content()
                                .contentType("application/hal+json;charset=UTF-8"))
                .andExpect(jsonPath("$._links.self.href")
                        .value("http://localhost:8080/api/" + testUser.getUsername()))
                .andDo(document("findone", responseFields(
                        fieldWithPath("username").description("The users name"),
                        fieldWithPath("firstname").description("The users first name"),
                        fieldWithPath("lastname").description("The users last name"),
                        fieldWithPath("birthday").description("The users birthday"),
                        fieldWithPath("_links.self.href").description("The link to this user")
                )));
    }
}