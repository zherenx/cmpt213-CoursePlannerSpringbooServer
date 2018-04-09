package com.cmpt213.a5.courseplanner.model;

import com.cmpt213.a5.courseplanner.model.dataobjects.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataProcessor {


    public static Data processCourseDataFile(File csvFile) {


        Scanner csvScanner = null;
        try {
            csvScanner = new Scanner(new FileReader(csvFile));
        } catch (FileNotFoundException e) {
            System.out.println("Pathname is not valid, stop processing...");
            return new Data();
        }

        String headerLine = csvScanner.nextLine();
        List<String> dataLines = new ArrayList<>();
        while (csvScanner.hasNextLine()) {
            dataLines.add(csvScanner.nextLine());
        }
        csvScanner.close();

        List<RawData> rawData = processLinesToRawData(dataLines);

        Data result = new Data(rawData);

        return result;
    }

    private static List<RawData> processLinesToRawData(List<String> lines) {
        List<RawData> rawData = new ArrayList<>();
        for (String line: lines) {

            String[] courseComponentInfo = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");

//            for (String s: courseComponentInfo) {
//                System.out.println("/" + s + "/");
//            }

            // remove " and space at the beginning and end.
            for (int i = 0; i < courseComponentInfo.length; i++) {
                courseComponentInfo[i] = courseComponentInfo[i].trim().replaceAll("^\"|\"$", "").trim();
            }

//            for (String s: courseComponentInfo) {
//                System.out.println("/" + s + "/");
//            }

            rawData.add(new RawData(courseComponentInfo));
        }
        return rawData;
    }

}
