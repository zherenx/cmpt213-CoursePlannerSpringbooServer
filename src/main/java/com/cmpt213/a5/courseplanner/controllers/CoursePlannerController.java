package com.cmpt213.a5.courseplanner.controllers;

import com.cmpt213.a5.courseplanner.model.Data;
import com.cmpt213.a5.courseplanner.model.DataProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class CoursePlannerController {


    @GetMapping("/dump-model")
    public void processToModelDumpFormat() {

//        String pathname = "./";
//        String pathname = "./data/test_data.csv";
//        String pathname = "./data/course_data_2016.csv";
        String pathname = "./data/course_data_2018.csv";

        File csvFile = new File(pathname);

        Data data;

        data = DataProcessor.processCourseDataFile(csvFile);

        data.printInModelDumpFormat();

    }


}
