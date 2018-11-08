package com.example.webmvc.integration;

import com.example.webmvc.controller.NewsController;
import com.example.webmvc.model.NewsItem;
import com.example.webmvc.service.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
public class NewsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    @WithMockUser
    public void testNewsController() throws Exception {

        // Prepare
        List<NewsItem> mockItems = IntStream.range(0, 5)
                .mapToObj(i -> new NewsItem("Title " + i, "Desc"))
                .collect(Collectors.toList());
        when(newsService.findNews(5))
                .thenReturn(mockItems);

        // Perform & Assert
        mockMvc.perform(get("/news"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("news",
                        hasItems(mockItems.toArray())))
                .andExpect(content().string(
                        containsString("Title 0")));
    }

    @Test
    @WithMockUser
    public void testNewsControllerWithParameters() throws Exception {

        // Prepare
        List<NewsItem> mockItems = IntStream.range(0, 12)
                .mapToObj(i -> new NewsItem("Title " + i, "Desc"))
                .collect(Collectors.toList());
        when(newsService.findNews(12))
                .thenReturn(mockItems);

        // Perform & Assert
        mockMvc.perform(get("/news?count=12"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("news",
                        hasItems(mockItems.toArray())))
                .andExpect(content().string(
                        containsString("Title 0")));
    }
}
