package StockApp;

import com.opencsv.CSVReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Model class for a Company
 */
public class Company {
    private final StringProperty companySymbol;
    private final StringProperty companyName;
    private final StringProperty companyDataFileName;
    private final StringProperty latestPrice;
    private final String[][] companyHistoryData;

    /**
     * Default constructor
     */
    public Company() {
        this(null, null, null);
    }

    /**
     * Constructor with some initial data
     * @param companyName
     * @param companySymbol
     * @param companyDataFileName
     */
    public Company(String companyName, String companySymbol,
                   String companyDataFileName) {
        this.companyName = new SimpleStringProperty(companyName);
        this.companySymbol = new SimpleStringProperty(companySymbol);
        this.companyDataFileName = new SimpleStringProperty(companyDataFileName);

        // Get Company History Data from corresponding csv file
        String[][] companyData = getDataFromCsvFile(companyDataFileName);
        // Load data from 2d array to Observable List.
        this.companyHistoryData = companyData;
        // TODO
        // get latest share price from company data
        String latest = companyData[0][6];
        this.latestPrice = new SimpleStringProperty(latest);

    }

    // TODO: refactor
    public String[][] getDataFromCsvFile(String companyDataFileName) {
        String[][] dataArr = new String[0][0];
        String csvFile = "StockData/" + companyDataFileName;
        try{

            CSVReader csvReader = new CSVReader(new FileReader(csvFile));
            csvReader.skip(1); // skip header
            List<String[]> list = csvReader.readAll();

            // Convert to 2D array
            dataArr = new String[list.size()][];
            dataArr = list.toArray(dataArr);
            return  dataArr;

        } catch (IOException e){
            e.printStackTrace();
        }
         return dataArr;

      }


    // Getters and Setters.
    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public String getCompanySymbol() {
        return companySymbol.get();
    }

    public void setCompanySymbol(String companySymbol) {
        this.companySymbol.set(companySymbol);
    }

    public StringProperty companySymbolProperty() {
        return companySymbol;
    }

    public String getCompanyDataFileName() {
        return companyDataFileName.get();
    }

    public void setCompanyDataFileName(String companyDataFileName) {
        this.companyDataFileName.set(companyDataFileName);
    }

    public StringProperty companyDataFileNameProperty() {
        return companyDataFileName;
    }

    public String getLatestPrice() {
        return latestPrice.get();
    }


    public StringProperty latestPriceProperty(){
        return latestPrice;
    }

    public String[][] getCompanyHistoryData() {
         return companyHistoryData;
    }
}