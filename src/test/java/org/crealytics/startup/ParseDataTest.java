package org.crealytics.startup;

import org.assertj.core.api.Assertions;
import org.crealytics.bean.AdDetail;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ParseDataTest {

    @Test
    public void getRecordFromFile() {
        List<AdDetail> list = new ParseData().getRecordFromFile(Paths.get("src/main/resources/exportData/2018_02_report.csv"));
        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(4)
                .hasOnlyElementsOfType(AdDetail.class);
    }

    @Test
    public void getRecordFromDirectory() throws IOException {
        List<AdDetail> list = new ParseData().getRecordFromDirectory(Paths.get("src/main/resources/exportData/"));
        Assertions.assertThat(list)
                .isNotNull()
                .hasSize(8)
                .hasOnlyElementsOfType(AdDetail.class);
    }
}