package StockApp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a Company
 */
public class Company {
    private final StringProperty companySymbol;
    private final StringProperty companyName;
    private final StringProperty companyDataFileName;
    private final StringProperty lastPrice;

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
        // dummy data, for testing
        // To get this data we need to open corresponding csv file
        this.lastPrice = new SimpleStringProperty("0");
    }

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

    public String getLastPrice() {
        return lastPrice.get();
    }
    public void setLastPrice(String lastPrice){
        this.lastPrice.set(lastPrice);
    }
    public StringProperty lastPriceProperty(){
        return lastPrice;
    }
}