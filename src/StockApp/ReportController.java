package StockApp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class ReportController {

    // Reference to the main application
    private MainApp mainApp;
    @FXML private VBox vBox;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setCompanyData(List<Company> companies){

        // for each company create label and value
        for (Company c : companies){
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

            // company symbol
            Label symbolLabel = new Label("Stock Symbol: ");
            symbolLabel.setId("labelText");
            grid.add(symbolLabel,0,0);
            Text symbolTxt = new Text(c.getCompanySymbol());
            //txtSymbol.setId("");
            grid.add(symbolTxt,1,0);

            // company name
            Label nameLabel = new Label("Company Name: ");
            nameLabel.setId("labelText");
            grid.add(nameLabel,0,1);
            Text nameTxt = new Text(c.getCompanyName());
            //txtSymbol.setId("");
            grid.add(nameTxt,1,1);

            // date with highest stock price
            Label hLabel = new Label("Highest: ");
            hLabel.setId("labelText");
            grid.add(hLabel,0,2);
            Text hTxt = new Text(c.getDateOfHighestPrice());
            //txtSymbol.setId("");
            grid.add(hTxt,1,2);

            // date with lowest stock price
            Label lLabel = new Label("Lowest: ");
            lLabel.setId("labelText");
            grid.add(lLabel,0,3);
            Text lTxt = new Text(c.getDateOfLowestPrice());
            //txtSymbol.setId("");
            grid.add(lTxt,1,3);

            // average closing price
            Label avLabel = new Label("Average close: ");
            avLabel.setId("labelText");
            grid.add(avLabel,0,4);
            Text avTxt = new Text(c.getAverageClosePrice());
            //txtSymbol.setId("");
            grid.add(avTxt,1,4);

            // latest closing stock price
            Label cLabel = new Label("Close: ");
            cLabel.setId("labelText");
            grid.add(cLabel,0,5);
            Text cTxt = new Text(c.getLatestPrice());
            //txtSymbol.setId("");
            grid.add(cTxt,1,5);

            vBox.getChildren().add(grid);
        }

    }


}
