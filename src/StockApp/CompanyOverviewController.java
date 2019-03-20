package StockApp;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;


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

    // company stock graph
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private LineChart lineChart;

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

        // Listen for selection changes and show company history details and
        // stock graph when  selected
        companyOverviewTable.getSelectionModel().selectedItemProperty()
         .addListener((observable, oldValue, newValue) -> {
             showCompanyHistory(newValue);
             showLineGraph(newValue);
                 });
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
    // .com/t/663384/java/Populating-TableView-method; TODO: add credits @19
    // .03.18
    private ObservableList<ObservableList<String>> buildData(String[][] dataArray) {
        ObservableList<ObservableList<String>> data =
                FXCollections.observableArrayList();

            for (String[] row : dataArray) {
                data.add(FXCollections.observableArrayList(row));
            }

        return data;
    }

    private void showLineGraph(Company company){
        if (company != null){
            // clear graph if already populated
            lineChart.getData().clear();

            // Get company history data
            String[][] dataArr = company.getCompanyHistoryData();
            // Sort dataArr in dates ascending order
            Arrays.sort(dataArr, new Comparator<String[]>(){
                @Override
                public int compare(final String[] first, final String[] second)  {
                    try{
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM" +
                                "/yyyy");
                        return sdf.parse(first[0]).compareTo(sdf.parse(second[0]));
                    } catch (ParseException e){
                        e.getErrorOffset();
                    }
                    return -1;
                }
            });

            // Create data series
            XYChart.Series<String, Number> series = new XYChart.Series();
            series.setName(company.getCompanyName());

            for (String[] row: dataArr){
                series.getData().add(new XYChart.Data(row[0],
                        Double.parseDouble(row[6])));
            }
            // populate chart
            lineChart.getData().add(series);
            lineChart.setCreateSymbols(false);

        } else {
            // Clear the graph
          lineChart.getData().clear();
        }
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
