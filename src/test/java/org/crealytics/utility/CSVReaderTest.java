package org.crealytics.utility;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVReaderTest {
    @Test
    public void readPreLoadedHeader() throws Exception {
        CSVReader csvReader = createCSVReader();
        List<String> header = null;
        header = csvReader.getHeaders();
        assertThat(header)
                .contains("requests")
                .contains("revenue (USD)")
                .contains("site")
                .doesNotContain("sites")
                .hasSize(6);
    }

    @Test
    public void readPreLoadedRecords() throws Exception {
        List<List<String>> records = createCSVReader().getRecords();
        assertThat(records)
                .contains(Arrays.asList("desktop web","12483775","11866157","30965","7608","23555.46"))
                .hasSize(4);
    }
    @Test
    public void getRecords() throws IOException {
        List<List<String>> records = CSVReader.getRecords("src/main/resources/exportData/2018_02_report.csv");
        assertThat(records)
                .contains(Arrays.asList("iOS","5000221","4512765","18987","6001","11931.37"))
                .doesNotContain((Arrays.asList("desktop web","12483775","11866157","30965","7608","23555.46")))
                .hasSize(4);
    }

    @Test
    public void getHeaders() throws IOException {
        List<String> header = CSVReader.getHeaders("src/main/resources/exportData/2018_02_report.csv");
        assertThat(header)
                .contains("requests")
                .contains("revenue (USD)")
                .contains("site")
                .doesNotContain("sites")
                .hasSize(6);
    }


    private CSVReader createCSVReader() {
        return new CSVReader("src/main/resources/exportData/2018_01_report.csv");
    }

}