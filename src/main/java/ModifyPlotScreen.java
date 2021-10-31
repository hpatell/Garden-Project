import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;

/**
* @author	Himanshu Patel
* @author	Kush Patel
*/
public class ModifyPlotScreen extends Screen {
	
	public void updatePage(){}
	
	private Controller imc;
	
	double imgHeight = 100;
	double imgWidth = 100;
	int gardenSize;
	double scaledImgHeight;
	double scaledImgWidth;
	
	Collection<String> wPlantNames;
	Collection<String> hPlantNames;
	
	BorderPane borderPane;
	AnchorPane anchorPane;
	StackPane stackPaneTop;
	StackPane stackPaneBottom;
	ScrollPane scrollPane;
	VBox vBoxImages;
	HBox hBoxBottom;
	
	Label plantName;
	VBox plantBox;
	
	CheckBox woody;
	CheckBox herbaceous;
	
	Label gardenNameLabel;
	Label gardenBudgetLabel;
	Label gardenWeatherConditionLabel;
	Label gardenSoilConditionLabel;
	Label gardenMoistureConditionLabel;
	Label gardenDimensionsLabel;
	Label plantType;
	Label plantWeather;
	Label plantSoil;
	Label plantMoisture;
	Label gardenBudgetRemainingLabel;
	Label gardenTotalLeps;
	
	String commonname;
	String totalLeps;
	String remainingBudget;
	String commonnameRemove;
	
	HashMap<String, ImageView> plantIVs;
	ArrayList<Circle> plantsInGarden;
	HashMap<Circle, String> plantsToName;
	ImageView currentIV;
	
	/**
	* Constructor for ModifyPlotScreen, assigns new instance of Controller to imc.
	* Creates the scene for ModifyPlotScreen.
	* 
	* @param  v  takes in the instance of View accessed in Screen
	*/
    public ModifyPlotScreen(View v) {
		super(v, PagesEnum.ModifyPlotScreen);
		imc = new Controller(v);
		v.setPlantPNG(imc.getplants());
    	
		plantsInGarden = new ArrayList<Circle>();
		plantsToName = new HashMap<Circle, String>();
		plantType = new Label();
        plantWeather = new Label();
        plantSoil = new Label();
        plantMoisture = new Label();
        totalLeps = "0";
		
		setScreenOutline();
    	label();
    	settingsButton();
    	summaryButton();
    	faunaButton();
    	
    	scene = new Scene(borderPane, canvasWidth, canvasHeight);
    }
    
    /**
	* Updates the variables for Total Leps, Budget Remaining, Plant Name, Leps Supported, Plant Type, and Plant Cost in ModifyPlotScreen.
	* 
	* @param  type  the plant type Herbaceous or Woody
	* @param  leps  the number of leps supported by the plant
	* @param  cost  the cost of the plant
	* @param  name  the name of the plant
	*/
    public void update(String type, String leps, String cost, String name)
    {
    	gardenTotalLeps.setText("Total Leps: " + totalLeps);
       	gardenBudgetRemainingLabel.setText("Budget Remaining: $" + remainingBudget);
       	plantWeather.setText("Type: " + type);
       	plantType.setText("Leps Supported: " + leps);
       	plantSoil.setText("Cost: $" + cost);
       	plantMoisture.setText("Name: " + name);
    }

    /**
	* Calls the DragEvent methods setOnDragDetected, setOnDragOver and setOnDragDropped for drag and drop of plants.
	* 
	* @param  iv  the ImageView of the plant
	*/
    public void DragAndDrop(ImageView iv)
    {  	
		iv.setOnDragDetected(imc.getOnGardenDragDetected(iv));
    	anchorPane.setOnDragOver(imc.getOnGardenDragOver());
    	anchorPane.setOnDragDropped(imc.getOnGardenDragDropped());
    }

    /**
	* Starts the Drag and Drop Gesture on a plant ImageView by calling the startDragAndDrop method. The transfer
	* mode supported by the gesture source is defined, and the data to be transferred is placed onto the dragboard.
	* 
	* @param  event  the MouseEvent for when the plant is clicked on
	* @param  iv     the ImageView of the plant
	*/
    public void onGardenDragDetected(MouseEvent event, ImageView iv)
    {
    	Dragboard db = iv.startDragAndDrop(TransferMode.ANY);
		ClipboardContent content = new ClipboardContent();
		content.putImage(iv.getImage());
		db.setContent(content);
		event.consume();
		setCurrentPlantImage(iv);
    }
    
    /**
	* Handles a DRAG_OVER Event on a Target. Specifies which object accepts the data by implementing the DRAG_OVER
	* event handler. Calls the acceptTransferModes() method on the event, passing the COPY_OR_MOVE transfer mode
	* that the target accepts.
	* 
	* @param  event  the DragEvent for when the plant is Dragged Over
	*/
    public void onGardenDragOver(DragEvent event)
    {
    	if (event.getGestureSource() != anchorPane &&
				event.getDragboard().hasImage()) {
			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		}
		event.consume();
    }
    
    /**
	* Handles a DRAG_DROPPED Event on a Target. The DRAG_DROPPED event is sent to the gesture target when the
	* mouse button is released on the gesture target. A new Circle object of the plant is created and placed
	* on the garden plot/anchorPane in the exact location where the mouse button was released. Completes the
	* drag and drop gesture by calling the setDropCompleted() method on the event.
	* 
	* @param  event  the DragEvent for when the plant is dropped on the gesture target
	*/
    public void onGardenDragDropped(DragEvent event)
    {
    	Dragboard db = event.getDragboard();
		boolean success = false;
		if(db.hasImage()) 
		{
			Circle newIV = new Circle(scaledImgHeight/2, scaledImgWidth/2, scaledImgHeight/2);
			newIV.setFill(new ImagePattern(db.getImage()));
			newIV.setOnMouseDragged(imc.getDragHandler(newIV));
			newIV.setTranslateX(newIV.getTranslateX() + event.getX() - scaledImgWidth/2);
			newIV.setTranslateY(newIV.getTranslateY() + event.getY() - scaledImgHeight/2);
			
	    	if(currentIV != null && plantIVs != null) 
	    	{
	    		commonname = findCommomName(currentIV);
	    	}
			
			newIV.setOnMouseClicked(imc.removePlant(newIV));
			anchorPane.getChildren().add(newIV);
			success = true;
			plantsInGarden.add(newIV);
			plantsToName.put(newIV, commonname);
			checkDragDropCollision(newIV);
		}
		event.setDropCompleted(success);
		event.consume();
    }
    
    /**
	* Removes the plant/circle object from the garden plot/anchorPane.
	* 
	* @param  event  the MouseEvent for when the plant/circle object is right clicked
	* @param  iv     the circle object of the plant being removed from the garden
	*/
    public void removePlant(MouseEvent event, Circle iv)
    {
    		anchorPane.getChildren().remove(iv);
    		commonnameRemove = plantsToName.get(iv);
    		plantsToName.get(iv);
    }
    
    /**
	* Checks if there is a collision when a plant is dragged from the plant menu to the garden plot. Detects
	* collision when the dragged plant is dropped/placed on an existing circle plant object in the garden plot/
	* AnchorPane. If collision is detected, the new circle plant object is placed next to the existing circle 
	* plant object.
	* 
	* @param  iv  the circle object of the plant
	*/
    public void checkDragDropCollision(Circle iv)
    {
    	for (Circle static_bloc : plantsInGarden)
    	{
    		if (static_bloc != iv)
    		{
    			Shape intersect = Shape.intersect(iv, static_bloc);
    			if (intersect.getBoundsInLocal().getWidth() != -1)
    			{
    				if(iv.getTranslateX() > static_bloc.getTranslateX() - imgWidth && iv.getTranslateX() < static_bloc.getTranslateX())
        			{
        				iv.setTranslateX(static_bloc.getTranslateX() - imgWidth);
        			}
    				if(iv.getTranslateX() < static_bloc.getTranslateX() + imgWidth && iv.getTranslateX() > static_bloc.getTranslateX())
        			{
        				iv.setTranslateX(static_bloc.getTranslateX() + imgWidth);
        			}
    			}
    		}
    	}
    }
    
    /**
	* Checks if there is a collision between plants when a circle plant object is dragged around the garden plot.
	* Detects collision when the dragged circle plant object touches an existing circle plant object in the 
	* garden plot/AnchorPane. If collision is detected, the drag MouseEvent of the dragged circle plant object
	* is stopped until the mouse cursor is moved to an area in the garden plot where collision is not detected.
	* 
	* @param  iv  the circle object of the plant
	*/
    public boolean checkCollision(Circle iv)
    {
    	for (Circle static_bloc : plantsInGarden)
    	{
    		if (static_bloc != iv)
    		{
    			Shape intersect = Shape.intersect(iv, static_bloc);
    			if (intersect.getBoundsInLocal().getWidth() != -1)
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
	* Setter for currentIV. Takes in the ImageView of a plant and sets it equal to the current ImageView (currentIV).
	* 
	* @param  iv  the ImageView of the plant
	*/
    public void setCurrentPlantImage(ImageView iv) {
    	currentIV = iv;
    }
    
    /**
	* Displays plants in the plant selection menu based upon which check boxes are checked and unchecked. If Herbaceous
	* and Woody is selected, all plants are displayed in the plant selection menu. If only Herbaceous is selected, only
	* Herbaceous plants will be displayed in the plant selection menu. If only Woody is selected, only Woody plants will
	* be displayed in the plant selection menu. If neither Herbaceous nor Woody is selected, no plants will be displayed
	* in the plant selection menu.
	* 
	* @param  event       the ActionEvent of clicking the check box
	* @param  imageViews  the HashMap of current ImageViews in the plant selection menu based on selected conditions
	* @param  plants      the HashMap of all plants in the application
	*/
    public void checkbox(ActionEvent event, HashMap<String, ImageView> imageViews, HashMap<String, Plant> plants)
    {  	
    	vBoxImages.getChildren().clear();
    	
    	for (Entry<String, ImageView> mapElement : imageViews.entrySet()) 
    	{
            String key = mapElement.getKey();
            ImageView value = mapElement.getValue();
            
            plantName = new Label(plants.get(key).scientificName);
            plantName.setFont(new Font("Arial", 22));
        	
        	plantBox = new VBox();
        	plantBox.setAlignment(Pos.CENTER);
        	plantBox.getChildren().addAll(value, plantName);
        	
        	if(woody.isSelected() && plants.get(key).plantType == "woody")
        	{
        		vBoxImages.getChildren().addAll(plantBox);
        	}
            if(herbaceous.isSelected() && plants.get(key).plantType == "herbaceous")
            {
            	vBoxImages.getChildren().addAll(plantBox);
            }
    	}
    }
    
    /**
	* Declares, Initializes and sets the Nodes/Panes for a BorderPane Outline. Creates CheckBoxes, VBoxes, HBoxes,
	* StackPanes, ScrollPane, AnchorPane and BorderPane. vBoxImages contains the names and ImageViews of all the plants
	* in the plant selection menu. scrollPane contains the vBoxImages to be able to scroll through the selection of plants.
	* vBoxLeftBorder contains the CheckBoxes and ScrollPane. anchorPane is the garden plot which contains all the plants in
	* the garden. hBoxBottom contains the settings, summary, and fauna screen buttons.
	*/
    public void setScreenOutline()
    {
    	// Check Boxes
        woody = new CheckBox("Woody");
        herbaceous = new CheckBox("Herbaceous");
        woody.setSelected(true);
        herbaceous.setSelected(true);
        HBox hbox = new HBox(24);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(woody, herbaceous);
        
    	// VBoxImages
        vBoxImages = new VBox();
        vBoxImages.setStyle("-fx-background-color: #add8e6;");
        vBoxImages.setAlignment(Pos.CENTER);
        vBoxImages.setMinWidth(182);
        vBoxImages.setSpacing(25);
    	
    	// ScrollPane
        scrollPane = new ScrollPane();
    	scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
    	scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
    	scrollPane.setMinWidth(200);
    	scrollPane.setContent(vBoxImages);
    	
    	// vBoxLeftBorder
    	VBox vBoxLeftBorder = new VBox();
    	vBoxLeftBorder.getChildren().addAll(hbox, scrollPane);
    	vBoxLeftBorder.setStyle("-fx-background-color: #add8e6;");
    	
    	// AnchorPane
    	anchorPane = new AnchorPane();
    	Image gardenIMG = new Image(getClass().getResourceAsStream("/gardenIMG.png"));
    	anchorPane.setBackground(new Background(new BackgroundImage(
    			gardenIMG,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundPosition.CENTER,  
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
    	
    	// HboxBottom
    	hBoxBottom = new HBox();
    	hBoxBottom.setAlignment(Pos.CENTER);
    	hBoxBottom.setSpacing(50);
    	
    	// BorderPane
    	borderPane = new BorderPane();
    	stackPaneTop = new StackPane();
    	stackPaneBottom = new StackPane();
    	borderPane.setLeft(vBoxLeftBorder);
    	borderPane.setCenter(anchorPane); 
    	borderPane.setTop(stackPaneTop);
    	borderPane.setBottom(stackPaneBottom);
    	stackPaneTop.setStyle("-fx-background-color: #8fbc8f;");
    	stackPaneTop.setMinHeight(45);
    	stackPaneBottom.setStyle("-fx-background-color: #8fbc8f;");
    	stackPaneBottom.setMinHeight(45);
    	stackPaneBottom.getChildren().add(hBoxBottom);
    	layout = borderPane;
    }
    
    /**
	* Initializes and sets the Panes and Labels for the top of the BorderPane Outline. Initializes labels for budget,
	* budget remaining, total leps and adds them to the top right of ModifyPlotScreen. Initializes labels for sun exposure,
	* soil type, moisture level, garden dimensions, plant name, leps supported, plant type, plant cost and adds them to
	* the top left of ModifyPlotScreen. Adds the garden name to the top center of ModifyPlotScreen.
	*/
    public void label()
    {
    	gardenNameLabel = new Label(gardenname);
    	gardenNameLabel.setFont(new Font("Arial", 30));
    	HBox hBoxLabel = new HBox();
    	hBoxLabel.setAlignment(Pos.CENTER);
    	hBoxLabel.getChildren().add(gardenNameLabel);
    	stackPaneTop.getChildren().add(hBoxLabel);
    	
    	gardenBudgetLabel = new Label(gardenbudget);
    	gardenBudgetLabel.setFont(new Font("Arial", 16));
    	
    	gardenBudgetRemainingLabel = new Label("Budget Remaining: ");
    	gardenBudgetRemainingLabel.setFont(new Font("Arial", 16));
    	   	
    	gardenTotalLeps = new Label();
    	gardenTotalLeps.setText("Total Leps: " + totalLeps);
    	gardenTotalLeps.setFont(new Font("Arial", 16));
    	
    	VBox vBoxRight = new VBox();
    	vBoxRight.getChildren().remove(gardenTotalLeps);
    	vBoxRight.setAlignment(Pos.CENTER_RIGHT);
    	vBoxRight.setPadding(new Insets(0, 20, 0, 0));
    	vBoxRight.getChildren().addAll(gardenBudgetLabel, gardenBudgetRemainingLabel, gardenTotalLeps);
    	stackPaneTop.getChildren().add(vBoxRight);
    
    	
    	gardenWeatherConditionLabel = new Label(gardenWeatherCondition);
    	gardenWeatherConditionLabel.setFont(new Font("Arial", 13));
    	
    	gardenSoilConditionLabel = new Label(gardenSoilCondition);
    	gardenSoilConditionLabel.setFont(new Font("Arial", 13));
    	
    	gardenMoistureConditionLabel = new Label(gardenMoistureCondition);
    	gardenMoistureConditionLabel.setFont(new Font("Arial", 13));
    	
    	gardenDimensionsLabel = new Label("Dimensions: ");
    	gardenDimensionsLabel.setFont(new Font("Arial", 13));
    	
    	plantWeather.setFont(new Font("Arial", 13));
       	plantType.setFont(new Font("Arial", 13));
       	plantSoil.setFont(new Font("Arial", 13));
       	plantMoisture.setFont(new Font("Arial", 13));
    	
    	VBox vBoxLeft = new VBox();
    	vBoxLeft.setAlignment(Pos.CENTER_LEFT);
    	vBoxLeft.setPadding(new Insets(0, 0, 0, 20));
    	vBoxLeft.getChildren().addAll(gardenWeatherConditionLabel, gardenSoilConditionLabel, gardenMoistureConditionLabel, gardenDimensionsLabel, plantMoisture, plantType, plantWeather, plantSoil);
    	stackPaneTop.getChildren().add(vBoxLeft);
    }
    
    /**
	* Creates the settings button and adds it to the bottom center of ModifyPlotScreen.
	*/
    public void settingsButton() 
    {
    	Button settings = new Button("Settings");
    	settings.setOnAction(e -> view.switchPage(PagesEnum.SettingsScreen));
    	hBoxBottom.getChildren().add(settings);
    }
    
    /**
	* Creates the summary button and adds it to the bottom center of ModifyPlotScreen.
	*/
    public void summaryButton() {
    	Button summary = new Button("Summary");
    	summary.setOnAction(e -> view.switchPage(PagesEnum.SummaryScreen));
    	hBoxBottom.getChildren().add(summary);
    }
    
    /**
	* Creates the fauna button and adds it to the bottom center of ModifyPlotScreen.
	*/
    public void faunaButton() 
    {
    	Button fauna = new Button("Fauna");
    	fauna.setOnAction(e -> view.switchPage(PagesEnum.FaunaScreen));
    }
    
    /**
	* Iterates through the HashMap of current ImageViews in the plant selection menu. Takes in an ImageView and find the
	* corresponding ImageView in the HashMap plantIVs and returns the name of that plant.
	* 
	* @param   iv          the ImageView of the plant
	* @return  commonname  the common name of the plant
	*/
    public String findCommomName(ImageView iv) {
    	commonname = "";
		for (Entry<String, ImageView> mapElement : plantIVs.entrySet()) 
    	{          
            if(mapElement.getValue() == iv) {
                //System.out.println("The key for value " + iv + " is " + mapElement.getKey());
                commonname = mapElement.getKey();
                break;
              }
    	}
		return commonname;
    }
    
    /**
	* Gets the budget of the garden.
	* 
	* @return  gardenbudget  the budget of the garden
	*/
    public String getGardenBudget() {
    	return gardenbudget;
    }
    
    /**
	* Gets the name of the plant.
	* 
	* @return  commonname  the name of the plant
	*/
    public String getCommonName() {
    	return commonname;
    }
    
    /**
	* Gets the name of the plant to remove from the garden.
	* 
	* @return  commonnameRemove  the name of the plant to remove from the garden
	*/
    public String getCommonNameRemove() {
    	return commonnameRemove;
    }
    
    /**
	* Sets the total leps supported by the garden.
	* 
	* @param  t  the total leps supported by the garden
	*/
    public void setTotalLeps(String t) {
    	totalLeps = t;
    }
    
    /**
	* Sets the remaining budget of the garden.
	* 
	* @param  b  the remaining budget of the garden
	*/
    public void setRemainingBudget(String b) {
    	remainingBudget = b;
    }
    
    /**
	* Sets the Collection of woody plant names
	* 
	* @param  wplants  Collection of woody plant names
	*/
    public void getWoody(Collection<String> wplants) {
    	wPlantNames = wplants;
    }
    
    /**
	* Sets the Collection of herbaceous plant names
	* 
	* @param  hplants  Collection of herbaceous plant names
	*/
    public void getHerb(Collection<String> hplants) {
    	hPlantNames = hplants;
    }
    
    /**
	* Iterates through the HashMap of all plant PNG file names in the application and creates an ImageView for each plant.
	* Displays plant names and ImageViews in the plant selection menu based upon which sun exposure, soil type and moisture
	* level is selected by the user. Only plants that meet all user selected conditions will be displayed in the plant selection menu.
	* 
	* @param  imagePNGs  the HashMap of all plant PNG file names in the application
	* @param  Weather    the sun exposure of the garden
	* @param  Soil       the soil type of the garden
	* @param  Moisture   the moisture level of the garden
	* @param  plants     the HashMap of all plants in the application
	*/
    public void createImages(HashMap<String, String> imagePNGs, String Weather, String Soil, String Moisture, HashMap<String, Plant> plants)
    {
    	vBoxImages.getChildren().clear();
    	HashMap<String, ImageView> plantIVsCopy = new HashMap<>();
    	
    	for (Entry<String, String> mapElement : imagePNGs.entrySet()) 
    	{
            String key = (String)mapElement.getKey();
            String value = (String)mapElement.getValue();
            
            Image image = new Image(getClass().getResourceAsStream(value));
        	ImageView imageV = new ImageView();
        	
        	Circle clip = new Circle(imgWidth/2, imgHeight/2, imgWidth/2);
        	imageV.setClip(clip);
        	imageV.setImage(image);
        	imageV.setFitHeight(imgHeight);
        	imageV.setFitWidth(imgWidth);
        	DragAndDrop(imageV);
        	
        	plantName = new Label(plants.get(key).scientificName);
        	plantName.setFont(new Font("Arial", 22));
        	
        	plantBox = new VBox();
        	plantBox.setAlignment(Pos.CENTER);
        	plantBox.getChildren().addAll(imageV, plantName);
        	
        	if(plants.get(key).weatherType.equals(Weather) && plants.get(key).soilType.equals(Soil) && plants.get(key).moistureType.equals(Moisture))
    		{	
    			vBoxImages.getChildren().addAll(plantBox);
    			plantIVsCopy.put(key, imageV);
    		}
        	
        	//vBoxImages.getChildren().addAll(plantBox);
        	//plantIVsCopy.put(key, imageV);
    	}
    	plantIVs = plantIVsCopy;
    }
    
    /**
	* Sets the scaled plant image height and width based on the user inputed garden dimensions.
	* 
	* @param  dim  the user inputed garden dimensions
	*/
    public void scaleGarden(String dim) 
    {
        gardenSize = Integer.parseInt(dim);
        scaledImgHeight = (imgHeight/gardenSize)*10;
        scaledImgWidth = (imgWidth/gardenSize)*10;
    }
}
