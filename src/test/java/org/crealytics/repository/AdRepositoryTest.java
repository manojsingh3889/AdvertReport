package org.crealytics.repository;

import org.crealytics.bean.AdDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdRepositoryTest {
    @Autowired
    AdRepository repository;

    @Test
    public void verify_insert(){
        AdDetail ad = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,3);

        //check for null id before insert
        assertNull(ad.getId());

        //save it
        repository.save(ad);

        //check if entity is persisted by check generated id
        assertNotNull(ad.getId());

        //verify complete data insertion
        Optional<AdDetail> found = repository.findById(ad.getId());
        assertEquals(ad,found.get());

        //post-test: delete the entry
        repository.delete(ad);
    }

    @Test
    public void verify_bulk_insert(){
        List<AdDetail> list = new ArrayList<>();
        list.add(new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,1));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,2));

        //check for null id before insert
        list.stream().forEach(ad->assertNull(ad.getId()));

        //insert all
        repository.saveAll(list);

        //verify complete data insertion
        list.stream().forEach(ad->assertNotNull(ad.getId()));

        //verify insertion
        List<AdDetail> found = repository.findAllById(list.stream().map(AdDetail::getId).collect(Collectors.toList()));
        assertEquals(found,list);

        //delete the entry
        repository.deleteAll(list);
    }

    @Test
    public void test_findByMonthAndSite(){
        List<AdDetail> list = new ArrayList<>();
        list.add(new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,11));
        list.add(new AdDetail("andriod",120L,1001L,13L,11L,100.1f,11));
        list.add(new AdDetail("iOS",100L,1000L,12L,2L,100.0f,10));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,11));
        list.add(new AdDetail("mobile web",100L,1000L,12L,2L,100.0f,12));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,12));

        //insert all
        repository.saveAll(list);

        //verify -1
        List<AdDetail> found = repository.findByMonthAndSite(11,"desktop web");
        assertNotNull(found);
        assertEquals(found,list.stream().filter(adDetail -> adDetail.getMonth()==11 && adDetail.getSite().equals("desktop web")).collect(Collectors.toList()));
        //verify -2
        found = repository.findByMonthAndSite(10,"iOS");
        assertNotNull(found);
        assertEquals(found,list.stream().filter(adDetail -> adDetail.getMonth()==10 && adDetail.getSite().equals("iOS")).collect(Collectors.toList()));

        //delete
        repository.deleteAll(list);
    }

    @Test
    public void test_findByMonth(){
        List<AdDetail> list = new ArrayList<>();
        list.add(new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,11));
        list.add(new AdDetail("andriod",120L,1001L,13L,11L,100.1f,11));
        list.add(new AdDetail("iOS",100L,1000L,12L,2L,100.0f,10));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,11));
        list.add(new AdDetail("mobile web",100L,1000L,12L,2L,100.0f,11));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,10));

        //insert all
        repository.saveAll(list);

        //verify -1
        List<AdDetail> found = repository.findByMonth(11);
        assertNotNull(found);
        assertEquals(found,list.stream().filter(adDetail -> adDetail.getMonth()==11).collect(Collectors.toList()));
        //verify -2
        found = repository.findByMonth(10);
        assertNotNull(found);
        assertNotEquals(found,list.stream().filter(adDetail -> adDetail.getMonth()==10 && adDetail.getSite().equals("iOS")).collect(Collectors.toList()));


        //delete
        repository.deleteAll(list);
    }

}