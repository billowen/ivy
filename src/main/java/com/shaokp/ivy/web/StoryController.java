package com.shaokp.ivy.web;

import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class StoryController {

    private StoryService storyService;

    @Autowired
    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }

    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping(value = "/newStory", method = RequestMethod.GET)
    public String showNewStoryForm(Model model) {
        if (!model.containsAttribute("story")) {
            model.addAttribute("story", new Story());
        }

        return "post-new";
    }

    @RequestMapping(value = "/newStory", method = RequestMethod.POST)
    public String addStory(Story story, @RequestParam MultipartFile file) throws SQLException, IOException {
        storyService.save(story, file);
        return "redirect:/";
    }
}
