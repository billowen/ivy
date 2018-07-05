package com.shaokp.ivy.web;

import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.CommentService;
import com.shaokp.ivy.service.StoryService;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MainControllerTest {

    @Test
    public void test_showIndex() throws Exception {
        StoryController controller = new StoryController();
        StoryService storyService = mock(StoryService.class);
        controller.setStoryService(storyService);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("stories"));
        verify(storyService).findAll();
    }

    @Test
    public void test_showStoriesWithTage() throws Exception {
        StoryController controller = new StoryController();
        StoryService storyService = mock(StoryService.class);
        controller.setStoryService(storyService);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/").param("tag", "testTag"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("stories"));
        verify(storyService).findByTag("testTag");
    }

    @Test
    public void test_showNewStoryForm() throws Exception {
        StoryController controller = new StoryController();
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/newStory"))
                .andExpect(status().isOk())
                .andExpect(view().name("post-new"))
                .andExpect(model().attributeExists("story"));
    }

    @Test
    public void test_showStoryDetail() throws Exception {
        StoryController controller = new StoryController();
        StoryService storyService = mock(StoryService.class);
        CommentService commentService = mock(CommentService.class);
        controller.setStoryService(storyService);
        controller.setCommentService(commentService);
        MockMvc mockMvc = standaloneSetup(controller).build();

        when(storyService.findById(2L)).thenReturn(new Story());

        mockMvc.perform(get("/story/2"))
                .andExpect(status().isOk())
                .andExpect(view().name("article"))
                .andExpect(model().attributeExists("story"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(model().attributeExists("newComment"))
                .andExpect(model().attributeExists("tags"));

    }

    @Test
    public void test_addComment() throws Exception {
        StoryController controller = new StoryController();
        CommentService commentService = mock(CommentService.class);
        controller.setCommentService(commentService);
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(post("/2/addComment")
                .param("name", "ivy")
                .param("email", "ivy@outlook.com")
                .param("response", "test response"))
                .andExpect(redirectedUrl("/story/2"));

    }

}