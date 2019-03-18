package StockApp;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class CompanyOverviewController {
    @FXML
    private TableView<Company> companyTable;
    @FXML
    private TableColumn<Company, String> companyNameColumn;
    @FXML
    private TableColumn<Company, String> companySymbolColumn;
    @FXML
    private TableColumn<Company, String> latestPriceColumn;


    // Reference to the main application
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public CompanyOverviewController(){

    }
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize(){
        // Initialize the company overview table with the three columns.
        companySymbolColumn.setCellValueFactory(cellData -> cellData.getValue().companySymbolProperty());
        companyNameColumn.setCellValueFactory(cellData -> cellData.getValue().companyNameProperty());
        latestPriceColumn.setCellValueFactory(cellData -> cellData.getValue().lastPriceProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        // Add observable list data to the table
        companyTable.setItems(mainApp.getCompanyOverview());
    }
}
