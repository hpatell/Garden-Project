import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public class GuideScreen extends Screen {
	
	public void updatePage(){}
	
	Image backgroundimg;
	ImageView backgroundimgview;
	
	/**
	* Constructor for GuideScreen. Used to create the scene for guide screen.
	* 
	* @param  v  the instance of View accessed in Screen
	*/
	public GuideScreen(View v) {
		super(v, PagesEnum.GuideScreen);
		
		backgroundimg = new Image(getClass().getResourceAsStream("/guideImg.png"));
		backgroundimgview = new ImageView(backgroundimg);
		
		backgroundimgview.setPreserveRatio(true);
		backgroundimgview.setFitHeight(800);
		backgroundimgview.setFitWidth(800);
		
		createSettingsButton();
		createGardenButton();
		createGuideButton();
		createImportButton();
    	
		createGardenButton.setTranslateX(0);
		createGardenButton.setTranslateY(360);
		
    	layout = new StackPane();
    	layout.setMaxHeight(canvasHeight);
    	layout.setMaxWidth(canvasWidth);
    	layout.getChildren().addAll(backgroundimgview, createGardenButton);
    	layout.setStyle(theme);
    	scene = new Scene(layout, canvasWidth, canvasHeight);   	
	}
}
