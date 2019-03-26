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
        createCompanyReport(companyDataFileName);

    }

    // TODO: refactor to different class
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

      public void createCompanyReport(String companyDataFileName){
        String[][] dataArr = getDataFromCsvFile(companyDataFileName);
        // data for report:
          // Stock Symbol
          String symbol = getCompanySymbol();
          // Company Name
          String name = getCompanyName();
          // Highest --> date with the highest price of the stock
          String dateOfHighest = "";
          // Lowest
          String dateOfLowest = "";
          // Average close
          Double averageClose;
          // Close --> latest closing price
          String latestClose = getLatestPrice();

          // Get all the prices to find min and max
          List<Double> allPrices = new ArrayList<Double>();
          List<Double> closeValues = new ArrayList<Double>();

          for (String[] row: dataArr){
              allPrices.add(Double.parseDouble(row[1]));
              allPrices.add(Double.parseDouble(row[2]));
              allPrices.add(Double.parseDouble(row[3]));
              allPrices.add(Double.parseDouble(row[4]));
              closeValues.add(Double.parseDouble(row[4]));
          }

          allPrices.sort(null); // sort list in ascending order
          closeValues.sort(null);

          double min = allPrices.get(0);
          double max = allPrices.get(allPrices.size()-1);

          for (String[] row: dataArr){
              // find date for lowest and highest price
              // convert array to arrayList for easier look up
              List<String> rowList = Arrays.asList(row);
              // check if given string has 2 or 3 places after period
              if (rowList.contains(String.format("%.3f", max))){
                    dateOfHighest = rowList.get(0);
              } else if (rowList.contains(String.format("%.2f", max))){
                  dateOfHighest = rowList.get(0);
              } else if (rowList.contains(max)){
                  dateOfHighest = rowList.get(0);
              }

              if (rowList.contains(min)){
                  dateOfLowest = rowList.get(0);
              } else if (rowList.contains(String.format("%.2f",min))){
                  dateOfLowest = rowList.get(0);
              } else if (rowList.contains(String.format("%.3f",min))){
                  dateOfLowest = rowList.get(0);
              }
          }
          // find average
          double total = 0.0;
          for (Double closeValue : closeValues) {
              total += closeValue;
          }
          averageClose = total / closeValues.size();

          // print to console
          System.out.println("===========================");
          System.out.println("company name: " + name);
          System.out.println("symbol: " + symbol);
          System.out.println("Highest: " + dateOfHighest);
          System.out.println("Lowest: " + dateOfLowest);
          System.out.println("Average close: " + String.format("%.3f",
                  averageClose));
          System.out.println(" close: " + latestClose);
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