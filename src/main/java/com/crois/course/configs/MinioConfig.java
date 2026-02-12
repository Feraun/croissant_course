package com.crois.course.configs;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.username}")
    private String username;

    @Value("${minio.password}")
    private String password;

    //todo application
    public static final String COMMON_BUCKET_NAME = "common";

    @Bean
    public MinioClient minioClient() throws Exception {

        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(username, password)
                .build();


        if (!client.bucketExists(BucketExistsArgs.builder().bucket(COMMON_BUCKET_NAME).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(COMMON_BUCKET_NAME)
                            .build()
            );
        }
        return client;
    }

}
