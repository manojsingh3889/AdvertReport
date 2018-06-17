package org.crealytics.bean;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AdDetailReportTest {

    @Test
    public void copy_constructor(){
        AdDetail ad = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,2);
        assertEquals(new AdDetailReport(ad).getClicks(),new Long(12L));
        assertNotEquals(ad.getFillRate(),1000.1);

    }

    @Test
    public void fullBuild() {
        //parameterized constructor calls the full build
        AdDetail ad = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,1);
        assertThat(new AdDetailReport(ad))
                .hasFieldOrPropertyWithValue("cr",0.2f)
                .hasFieldOrPropertyWithValue("ctr",1.2f)
                .hasFieldOrPropertyWithValue("fillRate",1000.0f);
    }
}