package com.shaokp.ivy.web;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.shaokp.ivy.model.Comment;
import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.CommentService;
import com.shaokp.ivy.service.RotateImage;
import com.shaokp.ivy.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class StoryController {

    private StoryService storyService;
    private CommentService commentService;

    @Autowired
    public void setStoryService(StoryService storyService) {
        this.storyService = storyService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/")
    public String showIndex(Model model) throws Exception {
        List<Story> stories = storyService.findAll();
        model.addAttribute("stories", stories);
        return "index";
    }
    @RequestMapping(value = "/", params = {"tag"})
    public String showStoriesByTag(@RequestParam("tag") String tag, Model model) throws Exception {
        List<Story> stories = storyService.findByTag(tag);
        model.addAttribute("stories", stories);
        return "index";
    }

    @RequestMapping(value = "/deleteStory")
    public String deleteStory(@RequestParam("story") Long id) throws Exception {
        Story story = storyService.findById(id);
        storyService.delete(story);

        return "redirect:/";
    }

    @RequestMapping(value = "/deleteComment")
    public String deleteComment(@RequestParam("comment") Long id, Model model) throws Exception {
        Comment comment = commentService.findById(id);
        Long storyId = comment.getStoryId();
        commentService.delete(comment);

        Story story = storyService.findById(storyId);
        model.addAttribute("story", story);
        List<Comment> comments = commentService.listByStory(storyId);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        List<String> tags = storyService.listAllTags();
        model.addAttribute("tags", tags);

        return "article";
    }

    @RequestMapping(value = "/newStory", method = RequestMethod.GET)
    public String showNewStoryForm(Model model) {
        if (!model.containsAttribute("story")) {
            model.addAttribute("story", new Story());
        }

        return "post-new";
    }

    @RequestMapping(value = "/newStory", method = RequestMethod.POST)
    public String addStory(Story story, @RequestParam MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();

        InputStream in = new ByteArrayInputStream(bytes);

        Metadata metadata = ImageMetadataReader.readMetadata(in);
        ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        int orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);

        Integer turn=360;
        //确定旋转度数
        if(orientation==0||orientation==1) {
            turn=360;
        } else if(orientation==3) {
            turn=180;
        } else if(orientation==6) {
            turn=90;
        } else if(orientation==8) {
            turn=270;
        }

        in = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(in);
        BufferedImage image2 = RotateImage.Rotate(image, turn);

        OutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image2, "jpeg", out);
        story.setBytes(((ByteArrayOutputStream) out).toByteArray());

        storyService.save(story);
        return "redirect:/";
    }

    @RequestMapping(value = "/getImage/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "id") Long id) throws Exception {
        Story story = storyService.findById(id);
        return story.getBytes();
    }

    @RequestMapping(value = "/story/{id}")
    public String showStoryDetail(@PathVariable(value = "id") Long id, Model model) throws Exception {
        Story story = storyService.findById(id);
        model.addAttribute("story", story);
        List<Comment> comments = commentService.listByStory(id);
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        List<String> tags = storyService.listAllTags();
        model.addAttribute("tags", tags);

        return "article";
    }

    @RequestMapping(value = "/{storyId}/addComment", method = RequestMethod.POST)
    public String addComment(@PathVariable(value = "storyId") Long storyId, Comment comment) throws Exception {
        comment.setStoryId(storyId);
        commentService.save(comment);
        return "redirect:/story/" + storyId;
    }


}
