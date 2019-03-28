package StockApp;


import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


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
     @FXML private TilePane priceChartPlaceholder;
     @FXML private TilePane volumeChartPlaceholder;

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

        // Add label to priceChartPlaceholder;
        Text label = new Text("Select Company to See Stock Charts.");
        label.setId("selectLabel");

        priceChartPlaceholder.getChildren().add(label);



        // Listen for selection changes and show company history details and
        // stock graph when  selected
        companyOverviewTable.getSelectionModel().selectedItemProperty()
         .addListener((observable, oldValue, newValue) -> {
             showCompanyHistory(newValue);
             showLineGraphs(newValue);
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

    private void showLineGraphs(Company company) {
        if (company != null) {
            // clear graph if already populated
            priceChartPlaceholder.getChildren().clear();
            volumeChartPlaceholder.getChildren().clear();

            // Get company history data
            String[][] dataArr = company.getCompanyHistoryData();
            // Sort dataArr in dates ascending order
            Arrays.sort(dataArr, new Comparator<String[]>() {

                @Override
                public int compare(final String[] first, final String[] second) {
                  // DateTimeFormatterBuilder with reference to code on StockOverflow,
                    // answer by Alexis C.
                    // https://stackoverflow.com/questions/23488721/how-to-check-if-string-matches-date-pattern-using-time-api
                    // accessed @ 25/03/19
                    DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                            .appendOptional(DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy"))
                            .appendOptional(DateTimeFormatter.ofPattern(
                                    "yyyy-MM-dd"))
                            .toFormatter();

                    return LocalDate.parse(first[0], formatter).compareTo(LocalDate.parse(second[0], formatter));
                }
            });

             // get min and max opening/closing value to set Y-axis of Scatter
            // Chart

            List<Double> openValues = new ArrayList<Double>();
            List<Double> closeValues = new ArrayList<Double>();
            for (String[] row: dataArr){
                openValues.add(Double.parseDouble(row[1]));
                closeValues.add(Double.parseDouble(row[4]));
            }

            openValues.sort(null); // sort list in ascending order
            closeValues.sort(null);

            double minOpen = openValues.get(0);
            double maxOpen =
                    openValues.get(openValues.size()-1);
            double minClose = closeValues.get(0);
            double maxClose=
                    closeValues.get(closeValues.size()-1);

            double min = Math.min(minOpen, minClose);
            double max = Math.max(maxOpen, maxClose);

            // Declare charts and axis
            // Scatter chart for closing and opening price
            final CategoryAxis xAxis = new CategoryAxis();
            final NumberAxis yAxis = new NumberAxis(min-50,max+50,
                    50);

            final ScatterChart<String, Number> scatterChart =
                    new ScatterChart<String, Number>(xAxis, yAxis);
            scatterChart.setTitle("Opening and Closing Prices, Nov 2016 - Feb 2017");

            // Bar chart for Volume sold
            final CategoryAxis xAxis2 = new CategoryAxis();
            final NumberAxis yAxis2 = new NumberAxis();

            final BarChart<String, Number> barChart =
                    new BarChart<String, Number>(xAxis2, yAxis2);
            barChart.setTitle("Volume Sold, Nov 2016 - Feb 2017");


            // Create data series
            // for scatter chart
            XYChart.Series<String, Number> scatterChartSeriesOpen =
                    new XYChart.Series();
            scatterChartSeriesOpen.setName("Opening Price");

            XYChart.Series<String, Number> scatterChartSeriesClose =
                    new XYChart.Series();
            scatterChartSeriesClose.setName("Closing Price");

            // for bar chart
            XYChart.Series<String, Number> barChartSeries =
                    new XYChart.Series();
            barChartSeries.setName(company.getCompanyName());

            for (String[] row : dataArr) {
                scatterChartSeriesOpen.getData().add(new XYChart.Data(row[0],
                        Double.parseDouble(row[1])));
                scatterChartSeriesClose.getData().add(new XYChart.Data(row[0],
                        Double.parseDouble(row[4])));
                barChartSeries.getData().add(new XYChart.Data(row[0],
                        Double.parseDouble(row[5])));
            }
            // populate charts
            scatterChart.getData().addAll(scatterChartSeriesOpen, scatterChartSeriesClose);
            barChart.getData().add(barChartSeries);

            priceChartPlaceholder.getChildren().add(scatterChart);
            volumeChartPlaceholder.getChildren().add(barChart);

        } else {
            // Clear the graphs.
            System.out.println("No data selected");
            priceChartPlaceholder.getChildren().clear();
            volumeChartPlaceholder.getChildren().clear();

            // Add label to priceChartPlaceholder;
            Text label = new Text("Select Company to See Stock Chart.");
            label.setId("selectLabel");
              priceChartPlaceholder.getChildren().add(label);
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
