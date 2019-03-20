package StockApp;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;


public class CompanyOverviewController {
    // text entry
    @FXML private TextField filterInput;
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
//    @FXML private CategoryAxis xAxis;
//    @FXML private NumberAxis yAxis;
//    @FXML private LineChart lineChart;
     @FXML private AnchorPane chartPlaceholder;

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

        // Add label to chartPlaceholder;
        Text label = new Text("Select Company to See Stock Chart.");

        chartPlaceholder.getChildren().add(label);
        chartPlaceholder.setTopAnchor(label, 20.0);
        chartPlaceholder.setLeftAnchor(label, 20.0);


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

    private void showLineGraph(Company company) {
        if (company != null) {
            // clear graph if already populated
            chartPlaceholder.getChildren().clear();

            // declare axis
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Date");
            final LineChart<String, Number> lineChart =
                    new LineChart<String, Number>(xAxis, yAxis);

            lineChart.setTitle("Stock Monitoring, 2010");

            // Get company history data
            String[][] dataArr = company.getCompanyHistoryData();
            // Sort dataArr in dates ascending order
            Arrays.sort(dataArr, new Comparator<String[]>() {
                @Override
                public int compare(final String[] first, final String[] second) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM" +
                                "/yyyy");
                        return sdf.parse(first[0]).compareTo(sdf.parse(second[0]));
                    } catch (ParseException e) {
                        e.getErrorOffset();
                    }
                    return -1;
                }
            });


            // Create data series
            XYChart.Series<String, Number> series = new XYChart.Series();
            series.setName(company.getCompanyName());

            for (String[] row : dataArr) {
                series.getData().add(new XYChart.Data(row[0],
                        Double.parseDouble(row[6])));
            }
            // populate chart
            lineChart.getData().add(series);
            lineChart.setCreateSymbols(false);

            chartPlaceholder.getChildren().add(lineChart);

        }
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;

        /**
         * Implementing Filter Search to companyOverview table.
         * Reference: JavaFX 8 TableView Sorting and Filtering by Marco Jakob
         * https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
         * accessed @ 20.03.2019
          */

        // Wrap ObservableList to FilteredList
        ObservableList<Company> companyOverview = mainApp.getCompanyOverview();

        FilteredList<Company> filteredData =
                new FilteredList<Company>(companyOverview,
                        p -> true);

        // Set the filter Predicate whenever the filter changes
        filterInput.textProperty().addListener((observable, oldValue,
                                                newValue) -> { filteredData.setPredicate(company -> {
            // if filter text is empty, display all companies.
            if (newValue == null){
                return true;
            }
            // Compare company name and symbol with filter text
            String lowerCaseFilter = newValue.toLowerCase();

            if (company.getCompanyName().toLowerCase().contains(lowerCaseFilter)){
                return true; // filter matches company name
            } else if (company.getCompanySymbol().toLowerCase().contains(lowerCaseFilter)){
                return true; // filter matches company symbol
            }
            return false; // does not match
            });
        });
        // Wrap the FilteredList in a SortedList.
        SortedList<Company> sortedData = new SortedList<>(filteredData);

        // Bind thr SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(companyOverviewTable.comparatorProperty());

        // Populate companyOverview table with sorted data.
        companyOverviewTable.setItems(sortedData);
    }
}
