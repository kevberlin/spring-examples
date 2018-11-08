package com.example.webmvc.integration;

import com.example.webmvc.controller.NewsUserController;
import com.example.webmvc.model.NewsUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsUserController.class)
public class NewsUserIntegrationTest {

    @MockBean
    private NewsUserRepository newsUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        when(newsUserRepository.findByUsername("testuser"))
                .thenReturn(new NewsUser("test", "test",
                        LocalDate.now(), "testuser", "pass"));
    }

    @Test
    public void testProfile() throws Exception {
        mockMvc.perform(get("/user/profile/testuser"))
                .andExpect(model().attributeExists("newsUser"))
                .andExpect(content().string(
                        containsString("Hello test")));
        Mockito.verify(newsUserRepository, times(1)).findByUsername("testuser");
    }
}
