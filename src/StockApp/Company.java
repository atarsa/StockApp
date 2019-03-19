package StockApp;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
    private StringProperty latestPrice;

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
        List<List<String>> companyData = getDataFromCsvFile(companyDataFileName);
        // get latest share price from company data
        String latest = companyData.get(1).get(6);
        this.latestPrice = new SimpleStringProperty(latest);

    }

    // TODO: open appropriate csv file  and convert it to companyData Array
    public List<List<String>> getDataFromCsvFile(String companyDataFileName){
        String csvFile = "StockData/" + companyDataFileName;
        String line;
        String csvSplitBy = ",";
        List<List<String>> csvData = new ArrayList<List<String>>();


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null){
                String[] csvDataLine = line.split(csvSplitBy);
                csvData.add(Arrays.asList(csvDataLine));
            }
            System.out.println(csvData.toString());


        } catch (IOException e){
            e.printStackTrace();
        }
        return csvData;
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
//    public void setLatestPrice(List<List<String>> csvData){
//        String latest = csvData.get(1).get(6);
//        this.latestPrice.set(latest);
//    }

//    public void setLatestPrice(String companyDataFileName){
//        List<List<String>> csvData = getDataFromCsvFile(companyDataFileName);
//        String latest = csvData.get(1).get(6);
//        this.latestPrice.set(latest);
//    }
    public StringProperty latestPriceProperty(){
        return latestPrice;
    }


}