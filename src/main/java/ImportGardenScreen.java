import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;

/**
* @author	Kush Patel
* @author	Himanshu Patel
*/
public class ImportGardenScreen extends Screen {
	
	public void updatePage(){}
	
	Button fileOpenerButton;
	
	/**
	* Constructor for ImportGardenScreen. Used to create the scene for import garden screen.
	* 
	* @param  v  the instance of View accessed in Screen
	*/
	public ImportGardenScreen(View v) {
		super(v, PagesEnum.ImportScreen);
		fileOpenerButton = new Button("Open File");
		
   		layout = new StackPane();
    	layout.setStyle(theme);
    	layout.getChildren().addAll(fileOpenerButton);	
    	scene = new Scene(layout, canvasWidth, canvasHeight);
    	fileOpenerButton.setOnAction(e -> view.switchPage(PagesEnum.IntroScreen));
	}
}
