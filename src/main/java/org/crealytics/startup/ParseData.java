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

@Component
public class ParseData implements ApplicationRunner {

    @Autowired
    AdService adService;

    @Value("${app.csv.dir}")
    private String dirPath;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<AdDetail> adDetails = getRecordFromDirectory(Paths.get(dirPath));
            insertInDatabase(adDetails);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public List<AdDetail> getRecordFromDirectory(Path dirPath) throws IOException {
        List<AdDetail> adDetails = new ArrayList<>();
        Files.newDirectoryStream(dirPath,path -> path.getFileName().toString().matches("^2018_[0-1][1-9]+_report.csv$"))
                .forEach(path -> {
                    adDetails.addAll(getRecordFromFile(path));
                });
        return adDetails;
    }

    public void insertInDatabase(List<AdDetail> list){
        adService.insertAll(list);
    }
}
