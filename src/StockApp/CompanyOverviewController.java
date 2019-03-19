package StockApp;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Arrays;


public class CompanyOverviewController {
    // company overview table
    @FXML private TableView<Company> companyOverviewTable;
    @FXML private TableColumn<Company, String> companyNameColumn;
    @FXML private TableColumn<Company, String> companySymbolColumn;
    @FXML private TableColumn<Company, String> latestPriceColumn;

    // company history data table
    @FXML private TableView<ObservableList<String>> companyHistoryDataTable;
    @FXML private TableColumn<ObservableList<String>, String>  dateColumn;
    @FXML private TableColumn<ObservableList<String>, String>  openColumn;
    @FXML private TableColumn<ObservableList<String>, String>  highColumn;
    @FXML private TableColumn<ObservableList<String>, String>  lowColumn;
    @FXML private TableColumn<ObservableList<String>, String>  closeColumn;
    @FXML private TableColumn<ObservableList<String>, String>  volumeColumn;
    @FXML private TableColumn<ObservableList<String>, String>  adjCloseColumn;

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
        latestPriceColumn.setCellValueFactory(cellData -> cellData.getValue().latestPriceProperty());

        // Clear company details
        //showCompanyHistory(null);

        // Listen for selection changes and show company history details when
        // selected
        companyOverviewTable.getSelectionModel().selectedItemProperty()
         .addListener((observable, oldValue, newValue) -> showCompanyHistory(newValue));

    }

    private void showCompanyHistory(Company company){
        if (company != null){
           // Populate companyHistoryDataTable with data
            String[][] dataArr = company.getCompanyHistoryData();
            companyHistoryDataTable.setItems(buildData(dataArr));

            dateColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(0)));
            openColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(1)));
            highColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(2)));
            lowColumn.setCellValueFactory(param ->new ReadOnlyObjectWrapper<>(param.getValue().get(3)));
            closeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(4)) );
            volumeColumn.setCellValueFactory(param ->new ReadOnlyObjectWrapper<>(param.getValue().get(5)));
            adjCloseColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(6)));

        } else {
            // Clear the table.
            System.out.println("No data selected");
            companyHistoryDataTable.getItems().clear();
         }
    }
    // code from https://coderanch
    // .com/t/663384/java/Populating-TableView-method; add credits @19.03.18
    private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
        ObservableList<ObservableList<String>> data =
                FXCollections.observableArrayList();

            for (String[] row : dataArray) {
                data.add(FXCollections.observableArrayList(row));
            }

        return data;
    }




    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        // Add observable list data to the table
        companyOverviewTable.setItems(mainApp.getCompanyOverview());
    }
}
