package stock.app;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class ReportController {

    @FXML private VBox vBox;

    public void setCompanyData(List<Company> companies){

        // for each company create label and value
        for (Company c : companies){
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 40, 20, 40));

            // company symbol
            Label symbolLabel = new Label("Stock Symbol: ");
            symbolLabel.getStyleClass().add("report-label");
            grid.add(symbolLabel,0,0);
            Text symbolTxt = new Text(c.getCompanySymbol());
            grid.add(symbolTxt,1,0);

            // company name
            Label nameLabel = new Label("Company Name: ");
            nameLabel.getStyleClass().add("report-label");
            grid.add(nameLabel,0,1);
            Text nameTxt = new Text(c.getCompanyName());
            grid.add(nameTxt,1,1);

            // date with highest stock price
            Label hLabel = new Label("Highest: ");
            hLabel.getStyleClass().add("report-label");
            grid.add(hLabel,0,2);
            Text hTxt = new Text(c.getDateOfHighestPrice());
            grid.add(hTxt,1,2);

            // date with lowest stock price
            Label lLabel = new Label("Lowest: ");
            lLabel.getStyleClass().add("report-label");
            grid.add(lLabel,0,3);
            Text lTxt = new Text(c.getDateOfLowestPrice());
            grid.add(lTxt,1,3);

            // average closing price
            Label avLabel = new Label("Average close: ");
            avLabel.getStyleClass().add("report-label");
            grid.add(avLabel,0,4);
            Text avTxt = new Text(c.getAverageClosePrice());
            grid.add(avTxt,1,4);

            // latest closing stock price
            Label cLabel = new Label("Close: ");
            cLabel.getStyleClass().add("report-label");
            grid.add(cLabel,0,5);
            Text cTxt = new Text(c.getLatestPrice());
            grid.add(cTxt,1,5);

            vBox.getChildren().add(grid);
        }
    }

}
