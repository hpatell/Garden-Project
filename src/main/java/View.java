import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.stage.Stage;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public class View extends Application {
	
	Stage currentstage;
	
	IntroScreen intro;
	ImportGardenScreen importgarden;
	GuideScreen guide;
	GardenInitializationScreen gardeninit;
	ModifyPlotScreen modify;
	faunaScreen fauna;
	SummaryScreen summary;
	settingsScreen settings;
	
	HashMap<String, String> plantPNG = new HashMap<>();
	
	HashMap<PagesEnum, Screen> nameToScreenMap = new HashMap<>();
	
	PagesEnum firstPage = PagesEnum.IntroScreen;
	PagesEnum previouspage = PagesEnum.IntroScreen;
	PagesEnum currentPage = PagesEnum.IntroScreen;
	
	/**
	* Constructor for View. Creates an instance of every screen used.
	*/
    public View() {
    	intro = new IntroScreen(this);
    	importgarden = new ImportGardenScreen(this);
    	guide = new GuideScreen(this);
    	gardeninit = new GardenInitializationScreen(this);
    	modify = new ModifyPlotScreen(this);
    	fauna = new faunaScreen(this);	
    	summary = new SummaryScreen(this);
    	settings = new settingsScreen(this);
    }
    
	/**
	* Takes the enum value of the page being switched to set the scene to that page.
	* Sets the previous page to current page to allow the settings screen to know what 
	* page to return to after exiting the screen.
	*
	* @param  PE  the enum value of the page being switched to
	*/   
    public void switchPage(PagesEnum PE) {
    	currentstage.setScene(nameToScreenMap.get(PE).getScene());
    	previouspage = currentPage;
    	currentPage = PE;
    }
    
	/**
	* Searches the HashMap taken in as an argument to create a new HashMap plantPNG
	* that has the same key, common names of the plants, and assigns the value as the
	* path to the PNG of the plant in the resources folder.
	* 
	* @param  plants  the HashMap of all plants in the application with their common name as the key
	*/    
    public void setPlantPNG(HashMap<String, Plant> plants) 
    {
    	for (Entry<String, Plant> mapElement : plants.entrySet()) 
    	{
            String key = mapElement.getKey();
            
            System.out.println("/plant_images/" + key + ".png");
            
            String png = "/plant_images/" + key + ".png";
            
            plantPNG.put(key, png);
    	}
    	System.out.println(plantPNG.size());
    }
    
	/**
	* The main entry point for the JavaFX application. The start method is called after the 
	* init method has returned, and after the system is ready for the application to begin running.
	* Sets the current scene of the program and displays it.
	* 
	* @param  stage  the primary stage for this application, onto which the application scenes can be set
	*/
    @Override
    public void start(Stage stage) {
    	currentstage = stage;
    	
    	switchPage(firstPage);
    	
    	currentstage.setScene(intro.scene);
    	currentstage.setTitle("Garden Application");
    	currentstage.show();
    }

	/**
	* Main method. The starting point for JVM to start execution of a Java program.
	* Calls the launch() method to launch a standalone application.
	* 
	* @param  args  accepts a string array to hold command line arguments
	*/
    public static void main(String[] args) {
        launch(args);
    }

}