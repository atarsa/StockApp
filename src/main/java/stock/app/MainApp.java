package stock.app;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    /**
     * The data as an list of Companies.     *
     */
    private ObservableList<Company> companyOverview =
            FXCollections.observableArrayList();

    public MainApp(){
        // Add symbols and companies names
        companyOverview.add(new Company("Ashtead Group plc", "AHT.L", "AHT" +
                ".csv"));
        companyOverview.add(new Company("Antofagasta plc", "ANTO.L", "ANTO" +
                ".csv"));
        companyOverview.add(new Company("BAE Systems plc", "BA.L",  "BA.csv"));
        companyOverview.add(new Company("British American Tobacco plc", "BATS" +
                ".L" , "BATS.csv"));
        companyOverview.add(new Company("Coca-Cola HBC AG", "CCH.L", "CCH" +
                ".csv"));
        companyOverview.add(new Company("Carnival plc", "CCL.L", "CCL.csv"));
        companyOverview.add(new Company("Centrica plc", "CNA.L", "CNA.csv"));
        companyOverview.add(new Company("Compass Group plc", "CPG.L", "CPG" +
                ".csv"));
        companyOverview.add(new Company("Experian plc", "EXPN.L", "EXPN.csv"));
        companyOverview.add(new Company("EasyJet plc", "EZJ.L", "EZJ.csv"));
        companyOverview.add(new Company("GKN plc", "GKN.L", "GKN.csv"));
        companyOverview.add(new Company("Mediclinic International plc", "MDC" +
                ".L", "MDC.csv"));
        companyOverview.add(new Company("Provident Financial plc", "PFG.L",
                "PFG.csv"));
        companyOverview.add(new Company("Paddy Power Betfair plc", "PPB.L",
                "PPB.csv"));
        companyOverview.add(new Company("Prudential plc", "PRU.L", "PRU.csv"));
        companyOverview.add(new Company("Persimmon plc", "PSN.L", "PSN.csv"));
        companyOverview.add(new Company("Reckitt Benckiser Group plc", "RB.L"
                , "RB.csv"));
        companyOverview.add(new Company("Royal Dutch Shell plc", "RDSA.L",
                "RDSA.csv"));
        companyOverview.add(new Company("Rolls-Royce Holdings plc", "RR.L",
                "RR.csv"));
        companyOverview.add(new Company("Schroders plc", "SDR.L", "SDR.csv"));
        companyOverview.add(new Company("Shire plc", "SHP.L", "SHP.csv"));
        companyOverview.add(new Company("Sky plc", "SKY.L", "SKY.csv"));
        companyOverview.add(new Company("SSE plc", "SSE.L", "SSE.csv"));
        companyOverview.add(new Company("St. James's Place plc", "STJ" +
                ".L", "STJ.csv"));
        companyOverview.add(new Company("Tesco plc", "TSCO.L", "TSCO.csv"));
        companyOverview.add(new Company("TUI AG", "TUI.L", "TUI.csv"));
        companyOverview.add(new Company("Vodafone Group plc", "VOD.L", "VOD" +
                ".csv"));
        companyOverview.add(new Company("Worldpay Group plc", "WPG" +
                ".L", "WPG.csv"));


    }

    /**
     * Returns the data as an observable list of Companies.
     * @return
     */
    public ObservableList<Company>getCompanyOverview(){
        return companyOverview;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Stock Explorer");

        initRootLayout();

        showCompanyOverview();


  }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout(){
        try{
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml" +
                    "/RootLayout" +
                    ".fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * Shows the company overview inside the root layout.
     */
    public void showCompanyOverview(){
        try{
            // Load company overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/CompanyOverview" +
                    ".fxml"));
            AnchorPane companyOverview = (AnchorPane) loader.load();

            // Set company overview into the center of root layout.
            rootLayout.setCenter(companyOverview);

            // Give the controller access to the main app.
            CompanyOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Opens a window with generated report.
     */
    public void showCompaniesReport(){
        try{
            // Load the fxml file and create a new stage for the window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/fxml/Report.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage reportStage = new Stage();
            reportStage.setTitle("Summary Report");
            reportStage.initModality(Modality.WINDOW_MODAL);
            reportStage.initOwner(primaryStage);
            Scene scene = new Scene(page, 600,800);

            reportStage.setScene(scene);
            // add stylesheet
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
            // Set companies into the controller
            ReportController controller = loader.getController();
            controller.setCompanyData(companyOverview);

            reportStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Saves companies report to one .txt file
     * @param file - path to text file created by user,
     *            retrieved from FileChooser
     * @throws IOException
     */
    public void saveCompaniesDataToFile(File file) throws IOException{
        List<Company> companyOverview = getCompanyOverview();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            for (int i=0; i < companyOverview.size(); i++){
                fileWriter.write("\n");
                fileWriter.write("Number: " + (i+1) + "\n");
                fileWriter.write("\n");
                fileWriter.write("Stock Symbol: " + companyOverview.get(i).getCompanySymbol()+ "\n");
                fileWriter.write("Company Name: " + companyOverview.get(i).getCompanyName()+ "\n");
                fileWriter.write("Highest: " + companyOverview.get(i).getDateOfHighestPrice()+
                        "\n");
                fileWriter.write("Lowest: " + companyOverview.get(i).getDateOfLowestPrice()+
                        "\n");
                fileWriter.write("Average close: " + companyOverview.get(i).getAverageClosePrice()+ "\n");
                fileWriter.write("Close: " + companyOverview.get(i).getLatestPrice() + "\n");
                fileWriter.write("\n======================== \n");
            }
        }
        finally {
            if (fileWriter != null){
                fileWriter.close();
            }
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
