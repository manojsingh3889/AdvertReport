package org.crealytics.service;

import org.crealytics.bean.AdDetail;
import org.crealytics.repository.AdRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdServiceTest {
    @Autowired
    AdService adService;

    @MockBean
    AdRepository repository;

    @Before
    public void setup(){
        AdDetail returnThis = new AdDetail();
        returnThis.setId(1L);
        Mockito.when(repository.save(Mockito.any(AdDetail.class)))
                .thenAnswer(invocationOnMock -> AdDetail.class.cast(invocationOnMock.getArgument(0)));
        Mockito.when(repository.saveAll(Mockito.any(List.class)))
                .thenAnswer(invocationOnMock -> List.class.cast(invocationOnMock.getArgument(0)));
    }

    @Test
    public void test_insert() {
        AdDetail ad = new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,3);
        AdDetail found = adService.insert(ad);
        //expect Id as repository has mock object
        assertEquals(ad,found);
    }

    @Test
    public void test_insertAll() {
        List<AdDetail> list = new ArrayList<>();
        list.add(new AdDetail("desktop web",100L,1000L,12L,2L,100.0f,1));
        list.add(new AdDetail("desktop web",120L,1001L,13L,11L,100.1f,2));

        List<AdDetail> found = adService.insertAll(list);
        //expect Id as repository has mock object
        assertEquals(list,found);
    }
}