import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
* @author	Himanshu Patel
* @author	Kush Patel
*/
public class IntroScreen extends Screen {
	
	public void updatePage(){}
	
	Image backgroundIMG;
	VBox vbox;
	HBox hbox;
	BorderPane borderPane;
	StackPane hstackPane;	
	StackPane vstackPane;	
	
	/**
	* Constructor for IntroScreen. Used to create the scene for introduction screen.
	* 
	* @param  v  the instance of View accessed in Screen
	*/
	public IntroScreen(View v) {
		super(v, PagesEnum.IntroScreen);
		
		borderPane = new BorderPane();
    	hstackPane = new StackPane();	
    	vstackPane = new StackPane();	

    	borderPane.setTop(hstackPane);
    	borderPane.setCenter(vstackPane);
    	
    	vbox = new VBox(10);
    	hbox = new HBox();
   
    	backgroundIMG = new Image(getClass().getResourceAsStream("/backgroundIMG.png"));
    	vstackPane.setBackground(new Background(new BackgroundImage(
    			backgroundIMG,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundRepeat.NO_REPEAT,  
                BackgroundPosition.CENTER,  
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
    	
		createSettingsButton();
		createGardenButton();
		createGuideButton();
		createImportButton();
		
		hbox.setAlignment(Pos.TOP_RIGHT);
		hbox.setPadding(new Insets(10, 10, 0, 0));
		hbox.getChildren().addAll(settingsButton);
		
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(createGardenButton, guideButton);
		
		hstackPane.getChildren().addAll(hbox);
		vstackPane.getChildren().addAll(vbox);

		borderPane.setMaxHeight(canvasHeight);
		borderPane.setMaxWidth(canvasHeight);
		borderPane.setStyle("-fx-background-color: #FFFFFF");
    	
		layout = borderPane;
    	scene = new Scene(layout, canvasWidth, canvasHeight);
	}
}