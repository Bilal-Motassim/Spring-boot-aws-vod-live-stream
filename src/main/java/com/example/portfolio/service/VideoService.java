package com.example.portfolio.service;

import com.example.portfolio.Model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideo(MultipartFile data, Long id);

    List<Video> getVideos(Long id);
}
