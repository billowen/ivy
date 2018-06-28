package com.shaokp.ivy.web;

import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Controller
public class StoryController {

    private StoryService storyService;

    @Autowired
    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }

    @RequestMapping("/")
    public String showIndex(Model model) throws SQLException {
        List<Story> stories = storyService.findAll();
        for (Story story : stories) {
            System.out.println(story.getDateUploaded());
        }
        model.addAttribute("stories", stories);
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

    @RequestMapping(value = "/getImage/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "id") Long id) throws SQLException {
        Story story = storyService.findById(id);
        return story.getBytes();
    }

    @RequestMapping(value = "/story/{id}")
    public String showStoryDetail(@PathVariable(value = "id") Long id, Model model) throws SQLException {
        Story story = storyService.findById(id);
        model.addAttribute("story", story);

        return "article";
    }
}
