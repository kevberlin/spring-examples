package com.example.webmvc.integration;

import com.example.webmvc.controller.NewsUserController;
import com.example.webmvc.model.NewsUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(NewsUserController.class)
public class NewsUserRegistrationTest {

    @MockBean
    private NewsUserRepository newsUserRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc
                .perform(get("/user/register"))
                .andExpect(model().attributeExists("newsUser"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Register")));
    }

    @Test
    public void testRegistration() throws Exception {

        //mock
        NewsUser checkUser = new NewsUser("test", "test",
                LocalDate.of(1970, 1, 1), "user", "pass");

        // act
        mockMvc
                .perform(post("/user/register")
                        .with(csrf())
                        .param("firstname", "test")
                        .param("lastname", "test")
                        .param("birthday", "1970-01-01")
                        .param("username", "user")
                        .param("password", "pass"))
                .andExpect(redirectedUrl("/user/profile/user"))
                .andExpect(flash().attributeExists("newsUser"));

        // capture
        ArgumentCaptor<NewsUser> argument =
                ArgumentCaptor.forClass(NewsUser.class);
        verify(newsUserRepository, times(1))
                .save(argument.capture());
        NewsUser savedUser = argument.getValue();

        // assert
        assertThat(savedUser).isEqualToIgnoringGivenFields(savedUser, "password");
        assertTrue(new BCryptPasswordEncoder()
                .matches("pass", savedUser.getPassword()));
    }

    @Test
    public void testNotFound() throws Exception {
        mockMvc.perform(get("/user/profile/unknown"))
                .andExpect(view().name("usernotfound"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(
                        containsString("We can not find this user.")));
    }

    @Test
    public void testNotValidUserRegistration() throws Exception {
        NewsUser checkUser = new NewsUser("a", "b",
                LocalDate.of(1970, 1, 1), "use", "pass");
        mockMvc
                .perform(post("/user/register")
                        .with(csrf())
                        .param("firstname", "a")
                        .param("lastname", "b")
                        .param("birthday", "1970-01-01")
                        .param("username", "use")
                        .param("password", "pass"))
                .andExpect(view().name("register"))
                .andExpect(content().string(
                        containsString("Muss mind. 4 Zeichen lang sein.")));
        verify(newsUserRepository, times(0)).save(checkUser);
    }
}
