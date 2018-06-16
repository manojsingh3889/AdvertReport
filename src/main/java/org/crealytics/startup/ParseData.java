package org.crealytics.startup;

import org.crealytics.bean.AdDetail;
import org.crealytics.service.AdService;
import org.crealytics.utility.CSVReader;
import org.crealytics.utility.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link ParseData} is an application startup listener, which perform initial data upload into DB
 */
@Component
public class ParseData implements ApplicationRunner {

    @Autowired
    AdService adService;

    /**
     * fetch value from spring properties stored in application.properties
     */
    @Value("${app.csv.dir}")
    private String dirPath;

    /**
     * This method is reponsible for parsing csv in {@link #dirPath} and load them into DB.
     * @param args startup arg injected by IOC
     * @throws Exception upper level exception to capture all
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<AdDetail> adDetails = getRecordFromDirectory(Paths.get(dirPath));
            insertInDatabase(adDetails);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method convert csv file stored at path and convert them into List of {@link AdDetail}
     * @param path path to csv file
     * @return List of {@link AdDetail}
     */
    public List<AdDetail> getRecordFromFile(Path path){
        CSVReader csv = new CSVReader(path);

        //add extra column for month name
        List<String> columns = new ArrayList<>(csv.getHeaders());
        columns.add("month");

        String month = path.getFileName().toString().split("_")[1];
        //add extra month value in each record
        List<List<String>> rows = new ArrayList<>(csv.getRecords());
        rows.stream().forEach(row->row.add(month));

        return ObjectMapper.getObjects(AdDetail.class,columns,rows);
    }

    /**
     * This method convert all csv files stored at directory matching to below mentioned regex
     * and convert them one-by-one into List of {@link AdDetail} with the help {@link #getRecordFromFile(Path)}
     * @param dirPath path to director containing matching to 2018_[0-1][1-9]+_report.csv e.g. 2018_11_report.csv, 2018_1_report.csv
     * @return List of {@link AdDetail}, merged result of all files
     * @throws IOException if IO failure happens
     */
    public List<AdDetail> getRecordFromDirectory(Path dirPath) throws IOException {
        List<AdDetail> adDetails = new ArrayList<>();
        Files.newDirectoryStream(dirPath,path -> path.getFileName().toString().matches("^2018_[0-1][1-9]+_report.csv$"))
                .forEach(path -> {
                    adDetails.addAll(getRecordFromFile(path));
                });
        return adDetails;
    }

    /**
     * This method simple sends insert request for List of {@link AdDetail} to {@link AdService}
     * @param list List of {@link AdDetail}
     */
    public void insertInDatabase(List<AdDetail> list){
        adService.insertAll(list);
    }
}
