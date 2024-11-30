package edu.msoe.microservicefinal.snowmanPostMicroservice.controller;

import edu.msoe.microservicefinal.snowmanPostMicroservice.database.SnowmanRepository;
import edu.msoe.microservicefinal.snowmanPostMicroservice.entity.Snowman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class SnowmanController {
    @Autowired
    private SnowmanRepository snowmanRepository;

    @GetMapping("/city/{city}")
    List<Snowman> getSnowmen(@PathVariable("city") String city){
        return snowmanRepository.findByCity(city);
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
                     @RequestPart("location") String location){
        Snowman snowman = new Snowman();
        snowman.setCity(city);
        snowman.setLocation(location);
        //TODO S3
        snowman.setImageURI("");
        //TODO Oauth
        snowman.setUsername("todo oauth");
        snowmanRepository.save(snowman);
    }
}
