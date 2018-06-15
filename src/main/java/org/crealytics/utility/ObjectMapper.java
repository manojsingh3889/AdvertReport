package org.crealytics.utility;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link ObjectMapper} is mapping utility class which convert string data matrix into supplied class type List
 * using mapping
 */
public class ObjectMapper {

    /**
     * This method parse arrays of string data into supplied class type object using headers.
     * <p>
     *     [Set of data] + [Mapping] = Object
     * </p>
     * @param type required type
     * @param columns mapping array
     * @param row data array
     * @param <T> Generic type to infer return type from Class
     * @return object of given Class
     */
    public static <T> T getObject(Class<T> type, List<String> columns, List<String> row){
        T obj = null;
        try {

            obj = type.newInstance();
            T finalObj = obj;
            Arrays.stream(Introspector.getBeanInfo(type).getPropertyDescriptors())
                    .forEach(propertyDescriptor -> {
                        try {
                            //if field exist this statement will not exit with exception
                            Field field = type.getDeclaredField(propertyDescriptor.getName());
                            int index = columns.indexOf(propertyDescriptor.getName());

                            if(index==-1 && field.getAnnotation(CSVProperty.class)!=null)
                                index = columns.indexOf(field.getAnnotation(CSVProperty.class).value());


                            if (index != -1)
                                propertyDescriptor.getWriteMethod().invoke(finalObj,
                                        GlobalUtils.parseValue(propertyDescriptor.getPropertyType(),row.get(index)));

                        } catch (NoSuchFieldException e){
                            //ignore this as this is can happen for valid reasons such as absence of header match in class type
                        }catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (InstantiationException|IllegalAccessException|IntrospectionException e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * This method parse matrix of string data into supplied class type object using headers by calling {@link #getObjects(Class, List, List)}.
     * <p>
     *     [Set of data] + [Mapping] = Object
     * </p>
     * @param type required type
     * @param columns mapping array
     * @param rows data matrix
     * @param <T> Generic type to infer return type from Class
     * @return list of object of given Class
     */
    public static <T> List<T> getObjects(Class<T> type, List<String> columns, List<List<String>> rows){
        return rows.parallelStream()
                .map(record -> getObject(type, columns, record))
                .collect(Collectors.toList());
    }
}
