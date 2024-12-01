package edu.msoe.microservicefinal.snowmanPostMicroservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import edu.msoe.microservicefinal.snowmanPostMicroservice.database.SnowmanRepository;
import edu.msoe.microservicefinal.snowmanPostMicroservice.database.UserRepository;
import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.Snowman;
import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class SnowmanController {
    @Autowired
    private SnowmanRepository snowmanRepository;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/city/{city}")
    List<Snowman> getSnowmen(@PathVariable("city") String city){
        return snowmanRepository.findByCity(city);
    }

    @PostMapping("/subscribe/{city}")
    boolean subscribeToCity(@PathVariable("city") String city){
        OAuth2User oAuth2User= (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = oAuth2User.getAttribute("email");
        Optional<User> user = userRepository.findById(email);
        if(user.isEmpty()){
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setNotifyCities(new ArrayList<>());
            newUser.getNotifyCities().add(city);
            userRepository.save(newUser);
            return true;
        }else{
            User existingUser = user.get();
            if(!existingUser.getNotifyCities().contains(city)){
                existingUser.getNotifyCities().add(city);
                userRepository.save(existingUser);
                return true;
            }
            existingUser.getNotifyCities().remove(city);
            userRepository.save(existingUser);
            return false;
        }
    }

    @PostMapping("/missing")
    Snowman missing( @RequestBody Map<String, Object> payload){
        int id = (int) payload.get("id");
        Optional<Snowman> snowmanOpt = snowmanRepository.findById((long) id);
        if(snowmanOpt.isPresent()){
            Snowman snowman = snowmanOpt.get();
            snowman.setMissingCount(snowman.getMissingCount() + 1);
            snowmanRepository.save(snowman);
            return snowman;
        }else{
            throw new NoSuchElementException();
        }
    }
    @PostMapping("/visit")
    Snowman visit( @RequestBody Map<String, Object> payload){
        int id = (int) payload.get("id");
        Optional<Snowman> snowmanOpt = snowmanRepository.findById((long) id);
        if(snowmanOpt.isPresent()){
            Snowman snowman = snowmanOpt.get();
            snowman.setSeenCount(snowman.getSeenCount() + 1);
            snowmanRepository.save(snowman);
            return snowman;
        }else{
            throw new NoSuchElementException();
        }
    }

    @PostMapping("/post")
    void postSnowman(@RequestPart("image") MultipartFile image, @RequestPart("city") String city,
                     @RequestPart("location") String location) throws IOException {
        OAuth2User user = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Snowman snowman = new Snowman();
        snowman.setCity(city);
        snowman.setLocation(location);
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(image.getContentType());
        data.setContentLength(image.getSize());
        String filepath = "images/"+ UUID.randomUUID() + image.getOriginalFilename() + image.getContentType();
        PutObjectResult result = s3Client.putObject("snowman-microservice", filepath,
                image.getInputStream(), data);
        snowman.setImageURI(s3Client.getUrl("snowman-microservice", filepath).toString());
        snowman.setUsername(user.getAttribute("name"));
        snowmanRepository.save(snowman);
    }
}
