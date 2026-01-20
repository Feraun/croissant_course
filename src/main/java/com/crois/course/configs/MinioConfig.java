package com.crois.course.configs;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    public static final String COMMON_BUCKET_NAME = "common";

    private String minioUsername;

    private String minioPassword;

    @Bean
    public MinioClient minioClient() throws Exception {
        String minioUrl = "http://localhost:9000";
        String minioUsername = "admin";
        String minioPassword = "admin123";

        MinioClient client = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
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
