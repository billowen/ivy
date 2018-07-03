package com.shaokp.ivy.web;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.shaokp.ivy.model.Story;
import com.shaokp.ivy.service.RotateImage;
import com.shaokp.ivy.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public String showIndex(Model model) throws Exception {
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

//    @RequestMapping(value = "/getImage/{id}")
//    public ResponseEntity<byte[]> getImage(@PathVariable(value = "id") Long id) throws SQLException, IOException {
//        Story story = storyService.findById(id);
//        byte[] bytes = story.getBytes();
//
//        String userDir = System.getProperty("user.home");
//
//        Path p1 = Paths.get(userDir, "text.jpg");
//
//        Files.write(p1, bytes);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
//    }



    @RequestMapping(value = "/story/{id}")
    public String showStoryDetail(@PathVariable(value = "id") Long id, Model model) throws Exception {
        Story story = storyService.findById(id);
        model.addAttribute("story", story);

        return "article";
    }
}
