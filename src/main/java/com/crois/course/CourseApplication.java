package com.crois.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseApplication {

    private static Initializer initiator;

    @Autowired
    public void setInitialLoader(Initializer initiator){
        CourseApplication.initiator = initiator;
    }

    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
        initiator.initial();
    }

}
