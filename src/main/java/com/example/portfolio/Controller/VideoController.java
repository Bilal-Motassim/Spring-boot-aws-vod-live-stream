package com.example.portfolio.Controller;


import com.example.portfolio.Model.Video;
import com.example.portfolio.dto.VideoResponse;
import com.example.portfolio.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/s3/{id}")
    public ResponseEntity<String> uploadMultipartFileToS3(@RequestParam("file") MultipartFile data,@PathVariable("id") Long id){
        String response = videoService.uploadVideo(data,id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/s3/{id}")
    public ResponseEntity<ArrayList<VideoResponse>> getVideos(@PathVariable("id") Long id){
        List<Video> videos = videoService.getVideos(id);
        ArrayList<VideoResponse> response = new ArrayList<>();
        for (Video v: videos) {
            response.add(new VideoResponse(v.getVideoname(),v.getVideoid()));
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

//    @GetMapping("/s3/{filename}")
//    public ResponseEntity<?> DownloadS3File(@PathVariable String filename){
//        String response = postService.downloadFile(filename);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
