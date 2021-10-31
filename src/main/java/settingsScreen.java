import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public class settingsScreen extends Screen {
	
	public void updatePage(){}
	
	BorderPane borderPane;
	StackPane hstackPane;	
	StackPane vstackPane;
	
	VBox vbox;
	HBox hbox;
	
	Button exitButton;
	Button dchangeThemebutton;
    Button lchangeThemebutton;
    Button returnToMenuButton;
	
	/**
	* Constructor for settingsScreen. Used to create the scene for settings screen.
	* 
	* @param  v  the instance of View accessed in Screen
	*/
    public settingsScreen(View v) {
        super(v, PagesEnum.SettingsScreen);	
    	
		borderPane = new BorderPane();	
    	hstackPane = new StackPane();	
    	vstackPane = new StackPane();	

    	borderPane.setTop(hstackPane);
    	borderPane.setCenter(vstackPane);
    	
    	vbox = new VBox(10);
    	hbox = new HBox();
        
        exitButton();
        changeTheme();
        returnToMenuButton();
        
		hbox.setAlignment(Pos.TOP_RIGHT);
		hbox.setPadding(new Insets(10, 10, 0, 0));
		hbox.getChildren().addAll(exitButton);
		
		vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(dchangeThemebutton, lchangeThemebutton, returnToMenuButton);
        
		hstackPane.getChildren().addAll(hbox);
		vstackPane.getChildren().addAll(vbox);
		
		borderPane.setMaxHeight(canvasHeight);
		borderPane.setMaxWidth(canvasHeight);
		borderPane.setStyle(theme);
		
        layout = borderPane;
        scene = new Scene(layout, canvasWidth, canvasHeight);
    }
    
	/**
	* Assigns the exitButton a value and assigns the screen to swap to previous screen upon button press.
	*/
    public void exitButton() {
    	exitButton = new Button("Exit");
    	exitButton.setFont(new Font("Arial", 20));
        exitButton.setOnAction(e -> view.switchPage(view.previouspage));
    }
    
	/**
	* Assigns values to the dark and light mode button as well as changing the theme
	* on the appropriate button press.
	*/
    public void changeTheme() {
        dchangeThemebutton = new Button("Dark");
        dchangeThemebutton.setFont(new Font("Arial", 20));
        lchangeThemebutton = new Button("Light");
        lchangeThemebutton.setFont(new Font("Arial", 20));
        dchangeThemebutton.setOnAction(e -> applyTheme("dark"));
        lchangeThemebutton.setOnAction(e -> applyTheme("light"));
    }
    
	/**
	* Changes the theme of every screen in the program by accessing the HashMap of every screen and setting
	* their layout theme to the new theme.
	* 
	* @param  s  the theme of the screen, dark or light
	*/
    public void applyTheme(String s) {
    	view.nameToScreenMap.forEach((name, screen) -> {
    		screen.changeTheme(s);
    	});
    }
    
	/**
	* Assigns the returnToMenuButton a value and assigns the screen to swap to Introduction Screen upon button press.
	*/
    public void returnToMenuButton() {
    	returnToMenuButton = new Button("Return To Menu");
    	returnToMenuButton.setFont(new Font("Arial", 20));
    	returnToMenuButton.setOnAction(e -> view.switchPage(PagesEnum.IntroScreen));
    }
}