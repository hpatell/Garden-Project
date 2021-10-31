import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.ComboBox;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public class GardenInitializationScreen extends Screen {
	
	private Controller imc;
	View view;
	
	Button createGardenButton;
	Label name;
	TextField nametf;
	Label budget;
	TextField budgettf;
	Label plotSize;
	TextField plotSizetf;

	ComboBox<String> weather;
	ComboBox<String> soil;
	ComboBox<String> moisture;
	
	String gardenbudgetlocal;
	VBox vbox;
	HBox hbox;
	BorderPane borderPane;
	StackPane hstackPane;	
	StackPane vstackPane;
	
	/**
	* Constructor for GardenInitializationScreen, assigns new instance of Controller to imc.
	* Creates the scene for GardenInitializationScreen.
	* 
	* @param  v  the instance of View accessed in Screen
	*/
	public GardenInitializationScreen(View v) {
		super(v, PagesEnum.InitializationScreen);
		view = v;
		imc = new Controller(v);
		
		borderPane = new BorderPane();
    	hstackPane = new StackPane();	
    	vstackPane = new StackPane();
    	
    	borderPane.setTop(hstackPane);
    	borderPane.setCenter(vstackPane);
    	
    	vbox = new VBox(10);
    	hbox = new HBox();
    	
		createSettingsButton();
	
		createGardenButton = new Button("Create Garden");
		createGardenButton.setFont(new Font("Arial", 20));
		name = new Label("Garden Name: ");
		name.setFont(new Font("Arial", 20));
		nametf = new TextField();
        nametf.setFont(new Font("Arial", 20));
		budget = new Label("Budget: ");
        budget.setFont(new Font("Arial", 20));
		budgettf = new TextField();
        budgettf.setFont(new Font("Arial", 20));
		plotSize = new Label("Length x Width ft (Between 5 and 20)");
        plotSize.setFont(new Font("Arial", 20));
		plotSizetf = new TextField();
        plotSizetf.setFont(new Font("Arial", 20));

		weather = new ComboBox<String>();
		soil = new ComboBox<String>();
		moisture = new ComboBox<String>();
		vbox = new VBox(20);
		
		weather.setPromptText("Weather Type");
		moisture.setPromptText("Moisture Type");
		soil.setPromptText("Soil Type");
	
		weather.getItems().addAll("Full Sun", "Half Sun", "Shade");
		soil.getItems().addAll("Sand", "Silt", "Clay");
		moisture.getItems().addAll("Dry", "Moist", "Wet");
		
		nametf.setMaxWidth(canvasHeight/2);
		plotSizetf.setMaxWidth(canvasHeight/2);
		budgettf.setMaxWidth(canvasHeight/2);
		
    	createGardenButton.setOnAction(e -> {
    		if((nametf.getText() != null) && (budgettf.getText() != "") && (weather.getValue() != null) && (soil.getValue() != null) && (moisture.getValue() != null) && (plotSizetf.getText() != null)) {
    			if((Integer.parseInt(plotSizetf.getText()) <= 20) && (Integer.parseInt(plotSizetf.getText()) >= 5)) {
        			view.switchPage(PagesEnum.ModifyPlotScreen);
        			gardenname = nametf.getText();
        	    	gardenbudget = budgettf.getText();
        	    	gardenWeatherCondition = weather.getValue();
        	    	gardenSoilCondition = soil.getValue();
        	    	gardenMoistureCondition = moisture.getValue();
        	    	gardenDimensions = plotSizetf.getText();
        	    	gardenbudgetlocal = gardenbudget;
        			updatePage();
    			}
    		}
    	});
    	
		hbox.setAlignment(Pos.TOP_RIGHT);
		hbox.setPadding(new Insets(10, 10, 0, 0));
		hbox.getChildren().addAll(settingsButton);
		
		vbox.getChildren().addAll(name, nametf, budget, budgettf, plotSize, plotSizetf, weather, soil, moisture, createGardenButton);
		vbox.setAlignment(Pos.CENTER);
		
		hstackPane.getChildren().addAll(hbox);
		vstackPane.getChildren().addAll(vbox);

		borderPane.setMaxHeight(canvasHeight);
		borderPane.setMaxWidth(canvasHeight);
		borderPane.setStyle(theme); 
		layout = borderPane;
    	scene = new Scene(layout, canvasWidth, canvasHeight);
	}
	
	/**
	* Used to update the user input based variables in the garden, such as name, budget, and conditions in modify plot screen.
	*/
	public void updatePage() {
		view.modify.gardenNameLabel.setText(gardenname);
		view.modify.gardenBudgetLabel.setText("Total Budget: $" + gardenbudgetlocal);
		view.modify.gardenWeatherConditionLabel.setText("Weather Condition: " + gardenWeatherCondition);
		view.modify.gardenSoilConditionLabel.setText("Soil Condition: " + gardenSoilCondition);
		view.modify.gardenMoistureConditionLabel.setText("Moisture Condition: " + gardenMoistureCondition);
		view.modify.gardenDimensionsLabel.setText("Dimensions: " + gardenDimensions);
		view.modify.createImages(view.plantPNG, gardenWeatherCondition, gardenSoilCondition, gardenMoistureCondition, imc.getplants());
		view.modify.woody.setOnAction(imc.getCheckboxHandler(view.modify.plantIVs));
		view.modify.herbaceous.setOnAction(imc.getCheckboxHandler(view.modify.plantIVs));
		view.modify.scaleGarden(gardenDimensions);
	}	
} 