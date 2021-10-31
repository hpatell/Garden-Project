import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public abstract class Screen {
	
	Pane layout;
    Image backgroundImage;
    View view;
    Scene scene;
    String theme;
    
    double canvasWidth;
    double canvasHeight;
    
	String gardenname;
	String gardenbudget;
	String gardenDimensions;
	String gardenWeatherCondition;
	String gardenSoilCondition;
	String gardenMoistureCondition;
	
	Button settingsButton;
    Image gear;
    ImageView gearIV;
	Button createGardenButton;
	Button guideButton;
	Button importButton;
	Button returnToPlotButton;

    PagesEnum page;
    
	/**
	* Constructor for Screen. Assigns all attributes an appropriate value.
	* 
	* @param  v   allows screen to access methods and attributes in View
	* @param  pe  utilizes the enum to help in changing screens
	*/
    public Screen(View v, PagesEnum pe) {
    	page = pe;
        view = v;
        canvasWidth = 1000;
        canvasHeight = 790;
        theme = "-fx-background-color: #add8e6;";
        view.nameToScreenMap.put(pe, this);  
        settingsButton = new Button("Settings");
        gear = new Image(getClass().getResourceAsStream("/gear.png"));
        gearIV = new ImageView(gear);
        createGardenButton = new Button("Create Garden");
		guideButton = new Button("Guide");
		importButton = new Button("Load Garden");
		returnToPlotButton = new Button("Return to Plot");
    }
    
	/**
	* Abstract method used by every screen to update dynamic aspects of the screen.
	*/
    public abstract void updatePage();
    
	/**
	* Sets the screen to switch to when createGardenButton is called. When the create garden
	* button is clicked, the scene/screen is switched to the garden initialization screen.
	*/
	public void createGardenButton() {
	     createGardenButton.setOnAction(e -> view.switchPage(PagesEnum.InitializationScreen));
	     createGardenButton.setFont(new Font("Arial", 20));
	}  
	
	/**
	* Sets the screen to switch to when settingsButton is called and gives the button a background image.
	* When the settings button is clicked, the scene/screen is switched to the settings screen.
	*/
	public void createSettingsButton() {
	    gearIV.setPreserveRatio(true);
	    gearIV.setFitHeight(20);
	    gearIV.setFitWidth(20);
	    settingsButton.setGraphic(gearIV);
	    settingsButton.setOnAction(e -> view.switchPage(PagesEnum.SettingsScreen));
    }
	
	/**
	* Sets the screen to switch to when GuideButton is called. When the guide button is clicked,
	* the scene/screen is switched to the guide screen.
	*/
	public void createGuideButton() {
	    guideButton.setOnAction(e -> view.switchPage(PagesEnum.GuideScreen));
	    guideButton.setFont(new Font("Arial", 20));
	}
	
	/**
	* Sets the screen to switch to when importButton is called. When the import button is clicked,
	* the scene/screen is switched to the import screen.
	*/
	public void createImportButton() {
		importButton.setOnAction(e -> view.switchPage(PagesEnum.ImportScreen));
		importButton.setFont(new Font("Arial", 20));
	}
	
	/**
	* Sets the screen to switch to when returnToPlotButton is called. When the return to plot button
	* is clicked, the scene/screen is switched to the modify plot screen.
	*/
    public void createReturnToPlotButton() {
    	returnToPlotButton.setOnAction(e -> view.switchPage(PagesEnum.ModifyPlotScreen));
    }
    
	/**
	* Sets the theme of the program light mode or dark mode based on the string taken in.
	* 
	* @param  s  takes in the string "dark" or "light" to set the theme of the program
	*/
    public void changeTheme(String s) {
    	if(s == "dark") {
    		theme = "-fx-background-color: #808080;";
    	} else if(s == "light") {
    		theme = "-fx-background-color: #add8e6;";
    	}
    	layout.setStyle(theme);
    }
    
	/**
	* Setter used to set the name of the garden.
	* 
	* @param  s  the name of the garden
	*/   
    public void setGardenName(String s) {
    	gardenname = s;
    }
    
	/**
	* Getter used to get the name of the garden
	* 
	* @return the name of the garden
	*/ 
    public String getGardenName() {
    	return gardenname;
    }
    
	/**
	* Setter used to set the budget of the garden as a string.
	* 
	* @param  s  the budget of the garden as a string
	*/ 
    public void setGardenBudget(String s) {
    	gardenbudget = s;
    }
    
	/**
	* Getter used to get the enum associated with the the current screen.
	* 
	* @return the enum associated with the current screen of the garden
	*/ 
    public PagesEnum getScreen() {
        return page;
    }

	/**
	* Getter used to get the current scene.
	* 
	* @return the current scene/screen of the garden
	*/ 
    public Scene getScene() {
        return scene;
    }
    
	/**
	* Getter used to get the budget of the garden as a string.
	* 
	* @return the budget of the garden as a string
	*/ 
    public String getgardenbudget() {
    	return gardenbudget;
    }
}