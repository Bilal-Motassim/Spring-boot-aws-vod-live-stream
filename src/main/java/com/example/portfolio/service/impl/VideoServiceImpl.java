package com.example.portfolio.service.impl;

import com.example.portfolio.Model.Video;
import com.example.portfolio.Repository.UserRepository;
import com.example.portfolio.Repository.VideoRepository;
import com.example.portfolio.service.VideoService;
import com.example.portfolio.utils.AWSCloudUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoRepository videoRepository;

    @Value("${aws.access.key}")
    private String AWS_ACCESS_KEY;
    @Value("${aws.secret.key}")
    private String AWS_SECRET_KEY;
    @Value("${aws.s3.bucket}")
    private String AWS_BUCKET;
    @Value("${aws.cloudfront.url}")
    private String AWS_CLOUDFRONT_URL;


    @Override
    public String uploadVideo(MultipartFile data, Long id) {
        try{
            Video video = new Video();

            int lastDotIndex = data.getOriginalFilename().lastIndexOf('.');
            String extention = data.getOriginalFilename().substring(lastDotIndex + 1);


            String videoname = UUID.randomUUID().toString();//+ "." + extention;
            //video.setVideourl(AWS_CLOUDFRONT_URL + videoname);
            video.setVideoname(data.getOriginalFilename());
            video.setVideoid(videoname);

            Video vid = userRepository.findById(id).map(user -> {
                video.setUser(user);
                return videoRepository.save(video);
            }).orElseThrow(() -> new ExecutionException("Not found user with id = " + id));

            if(vid!=null){
                AWSCloudUtil.uploadFileToS3(videoname+ "." + extention, data.getBytes(), AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_BUCKET);
                return String.format("File uploaded successfully to s3", data.getOriginalFilename());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return String.format("File failed to upload to to s3", data.getOriginalFilename());
    }

    @Override
    public List<Video> getVideos(Long id) {
        return videoRepository.findByUserId(id);
    }

//    @Override
//    public byte[] downloadFile(String filename) {
//        try{
//            AWSCloudUtil util = new AWSCloudUtil();
//            return util.downloadFileFromS3(filename, AWS_BUCKET, AWS_ACCESS_KEY, AWS_SECRET_KEY).readAllBytes();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
