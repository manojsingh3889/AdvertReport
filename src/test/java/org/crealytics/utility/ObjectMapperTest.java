package org.crealytics.utility;

import org.crealytics.extra.DummyProduct;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ObjectMapperTest {
    @Test
    public void getObjects() throws Exception {
        List<String> columns = Arrays.asList("quantity","cost_price","sell_price");
        List<List<String>> rows = Arrays.asList(Arrays.asList("1","23.0","25.0"),Arrays.asList("33","231.0","252.0"));

        List<DummyProduct> list = ObjectMapper.getObjects(DummyProduct.class,columns,rows);

        assertThat(list)
                .isNotEmpty()
                .hasSize(2);

        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("quantity",1);

        assertThat(list.get(1))
                .hasFieldOrPropertyWithValue("costPrice",231.0f);
    }

    @Test
    public void getObject() throws Exception {
        List<String> columns = Arrays.asList("quantity","cost_price","sell_price");
        List<String> row = Arrays.asList("1","2326.0","2523.0");
        DummyProduct detail = ObjectMapper.getObject(DummyProduct.class,columns,row);
        assertThat(detail)
                .isNotNull()
                .isInstanceOf(DummyProduct.class)
                .hasFieldOrPropertyWithValue("quantity",1)
                .hasFieldOrPropertyWithValue("costPrice",2326.0f);
    }
}