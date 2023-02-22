package utils;

import com.opencsv.CSVWriter;
import org.tinylog.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static data.Constants.DATE_FORMAT;

public class Utilities {

    private static CSVWriter csvWriter;
    private static final String TITLE_TIRE_SIZE = "tire_size";
    private static final String TITLE_TIRE_COUNT = "tire_count";
    private static final String TITLE_URL = "url";

    /**
     * Method to create a CSV file to report on.
     *
     * @param reportName
     */
    public static void createCSVReport(String reportName) {
        try {
            Logger.info("Creating CSV Report.");
            csvWriter = new CSVWriter(new FileWriter(reportName, true));
            csvWriter.writeNext(new String[]{TITLE_TIRE_SIZE, TITLE_TIRE_COUNT, TITLE_URL});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method used to report results on the CSV file created.
     *
     * @param values
     */
    public static void updateCSVReport(String[] values) {
        Logger.info("Updating CSV Report with new values.");
        csvWriter.writeNext(values);
    }

    /**
     * Method used to save the CSV report created.
     */
    public static void saveCSVReport() {
        try {
            Logger.info("Saving CSV Report.");
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method use to measure test run time.
     * @param startTime
     * @param finishTime
     * @return
     */
    public static String getRunTimeMeasure(long startTime, long finishTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        return "TEST RUN TIME - " + formatter.format(new Date(finishTime - startTime));
    }

}