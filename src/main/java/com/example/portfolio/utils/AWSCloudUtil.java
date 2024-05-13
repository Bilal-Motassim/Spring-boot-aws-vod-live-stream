package com.example.portfolio.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ivs.AmazonIVS;
import com.amazonaws.services.ivs.AmazonIVSClientBuilder;
import com.amazonaws.services.ivs.model.*;
import com.amazonaws.services.ivschat.Amazonivschat;
import com.amazonaws.services.ivschat.AmazonivschatClientBuilder;
import com.amazonaws.services.ivschat.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.security.DrbgParameters;

@Slf4j
public class AWSCloudUtil {
    private static AWSCredentials awsCredentials(String accessKey, String secretKey){
        return new BasicAWSCredentials(
                accessKey,
                secretKey
        );
    }

    private static AmazonS3 awsS3ClientBuilder(String accessKey, String secretKey){
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accessKey, secretKey)))
                .withRegion(Regions.EU_WEST_2)
                .build();
    }

    private static AmazonIVS awsIVSClientBuilder(String accessKey, String secretKey){
        return AmazonIVSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accessKey, secretKey)))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    private static Amazonivschat awsIVSChatClientBuilder(String accessKey, String secretKey){
        return AmazonivschatClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accessKey, secretKey)))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }

    private static AmazonIVS ivsclient;
    private static AmazonS3 s3client;

    private static Amazonivschat chatclient;


    public static void uploadFileToS3(String filename, byte[] fileBytes, String accessKey, String secretKey, String bucket){
        s3client = awsS3ClientBuilder(accessKey,secretKey);
        File file = new File(filename);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(fileBytes);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        s3client.putObject(bucket, filename, file);
    }

    public static CreateChannelResult createIVSChannel(String accessKey, String secretKey, String streamName){
        ivsclient = awsIVSClientBuilder(accessKey, secretKey);

        CreateChannelRequest request = new CreateChannelRequest()
                .withName(streamName)
                .withType(ChannelType.STANDARD)
                .withLatencyMode(ChannelLatencyMode.NORMAL);

        return ivsclient.createChannel(request);
    }

    public static GetStreamResult getStream(String accessKey, String secretKey, String streamArn){
       try{
           ivsclient = awsIVSClientBuilder(accessKey, secretKey);
           GetStreamRequest request = new GetStreamRequest().withChannelArn(streamArn);
           GetStreamResult result = ivsclient.getStream(request);
           log.error(result.getStream().getState());
           return result;
       }catch (ChannelNotBroadcastingException e){
           log.error(e.toString());
       }
       return null;
    }

    public static CreateRoomResult createRoom(String accessKey, String secretKey, String roomName){
        chatclient = awsIVSChatClientBuilder(accessKey, secretKey);
        CreateRoomRequest request = new CreateRoomRequest().withName(roomName);

        return chatclient.createRoom(request);
    }

    public static CreateChatTokenResult createChatToken(String accessKey, String secretKey, String chatARN){
        chatclient = awsIVSChatClientBuilder(accessKey, secretKey);
        CreateChatTokenRequest request = new CreateChatTokenRequest()
                .withRoomIdentifier(chatARN)
                .withUserId("all")
                .withCapabilities(ChatTokenCapability.SEND_MESSAGE)
                .withSessionDurationInMinutes(60);
        return chatclient.createChatToken(request);
    }



    public static S3ObjectInputStream downloadFileFromS3(String filename, String bucket, String accessKey, String secretKey){
        AmazonS3 s3client = awsS3ClientBuilder(accessKey, secretKey);
        S3Object s3Object = s3client.getObject(bucket, filename);
        return s3Object.getObjectContent();
    }
}
