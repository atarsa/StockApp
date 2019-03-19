package StockApp;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class CompanyOverviewController {
    // company overview table
    @FXML private TableView<Company> companyOverviewTable;
    @FXML private TableColumn<Company, String> companyNameColumn;
    @FXML private TableColumn<Company, String> companySymbolColumn;
    @FXML private TableColumn<Company, String> latestPriceColumn;

    // company history data table
    @FXML private TableView<Company> companyHistoryDataTable;
    @FXML private TableColumn<Company, String> dateColumn;
    @FXML private TableColumn<Company, String> openColumn;
    @FXML private TableColumn<Company, String> highColumn;
    @FXML private TableColumn<Company, String> lowColumn;
    @FXML private TableColumn<Company, String> closeColumn;
    @FXML private TableColumn<Company, String> volumeColumn;
    @FXML private TableColumn<Company, String> adjCloseColumn;

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


    }

    private void showCompanyHistory(Company company){
        if (company != null){
            // Initialize the company company history table
            companyHistoryDataTable.setItems(company.getCompanyHistoryData());
        } else {
            // TODO: clear the table;
        }
    }

//    private TableView<ObservableList<String>> createTableView(String[][] dataArray) {
//        TableView<ObservableList<String>> tableView = new TableView<>();
//        tableView.setItems(buildData(dataArray));
//
//        for (int i = 0; i < dataArray[0].length; i++) {
//            final int curCol = i;
//            final TableColumn<ObservableList<String>, String> column = new TableColumn<>(
//                    "Col " + (curCol + 1)
//            );
//            column.setCellValueFactory(
//                    param -> new ReadOnlyObjectWrapper<>(param.getValue().get(curCol))
//            );
//            tableView.getColumns().add(column);
//        }
//
//        return tableView;
//    }
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
