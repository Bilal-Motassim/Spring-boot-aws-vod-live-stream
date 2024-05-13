package com.example.awsproject.service;

import com.example.awsproject.Model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile data, Long id);

    List<Video> getVideos(Long id);
}
