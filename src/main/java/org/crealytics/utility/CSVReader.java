package org.crealytics.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link CSVReader} class contains utility method to extract header (array) and records (array of array)  from csv file.
 * @author manoj
 */
public class CSVReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);

    private static final String SEPARATOR = "\\s*,\\s*";
    private static final String COMMA = ",";
    private static final String ENCODING = "UTF-8";
    private Path path;

    /**
     * Create CSVReader object with file paths
     * @param path path string
     */
    public CSVReader(String path){
        this.path = Paths.get(path);
    }

    /**
     * Create CSVReader object with file paths
     * @param path path string
     */
    public CSVReader(Path path){
        this.path = path;
    }

    /**
     * Convert csv file record into 2D-matrix of {@link String}
     * @param path path to file e.g. /src/main/resources/file.csv in {@link Path}
     * @return 2D-matrix of csv data using list
     * @throws UncheckedIOException for IO ambiguity within lambda
     */
    public static List<List<String>> getRecords(Path path){
        LOGGER.info(String.format("Converting csv records at path[%s] into string matrix",path.getFileName()));
        try(Reader reader = Files.newBufferedReader(path, Charset.forName(ENCODING));
            BufferedReader br = new BufferedReader(reader)){
            return br.lines()
                    .skip(1)
                    .map(line -> new ArrayList<String>(Arrays.asList(line.split(SEPARATOR))))
                    .collect(Collectors.toList());
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Convert csv file record into 2D-matrix of {@link String}
     * @param path path to file as {@link String} e.g. /src/main/resources/file.csv
     * @return 2D-matrix of csv data using list
     */
    public static List<List<String>> getRecords(String path){
        return getRecords(Paths.get(path));
    }

    /**
     * Convert pre loaded csv file into 2D-matrix of only records{@link String}
     * @return 2D-matrix of csv data using list
     */
    public List<List<String>> getRecords(){
        return getRecords(path);
    }

    /**
     * Convert csv file header into List of {@link String}
     * @param path path to file e.g. /src/main/resources/file.csv in {@link Path}
     * @return list of headers
     * @throws UncheckedIOException for IO ambiguity within lambda
     */
    public static List<String> getHeaders(Path path){
        LOGGER.info(String.format("Converting all csv records at path[%s] into string array",path.getFileName()));
        try(Reader reader = Files.newBufferedReader(path, Charset.forName(ENCODING));
            BufferedReader br = new BufferedReader(reader)){
            return br.lines().findFirst()
                    .map(line -> new ArrayList<String>(Arrays.asList(line.split(SEPARATOR))))
                    .get();
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Convert csv file header into List of {@link String}
     * @param path path to file as {@link String} e.g. /src/main/resources/file.csv
     * @return list of headers
     */
    public static List<String> getHeaders(String path){
       return getHeaders(Paths.get(path));
    }

    /**
     * Convert pre loaded csv file header into List of {@link String}
     * @return list of headers
     */
    public List<String> getHeaders(){
        return getHeaders(path);
    }
}
