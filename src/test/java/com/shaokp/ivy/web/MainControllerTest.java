package com.shaokp.ivy.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MainControllerTest {

    @Test
    public void test_showIndex() throws Exception {
        StoryController controller = new StoryController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
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

}