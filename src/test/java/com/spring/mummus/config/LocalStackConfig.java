package com.spring.mummus.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class LocalStackConfig {

    private static final String BUCKET_NAME = "mummus";  // 사용할 버킷 이름 지정

    @Bean
    public AmazonS3Client amazonS3Client() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");

        AwsClientBuilder.EndpointConfiguration endpoint =
                new AwsClientBuilder.EndpointConfiguration(
                        "http://localhost:4566",
                        "us-east-1"
                );

        AmazonS3Client amazonS3Client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials("localstack-access-key", "localstack-secret-key")
                ))
                .build();

        // 버킷이 없으면 생성
        if (!amazonS3Client.doesBucketExistV2(BUCKET_NAME)) {
            amazonS3Client.createBucket(BUCKET_NAME);
        }

        return amazonS3Client;
    }
}