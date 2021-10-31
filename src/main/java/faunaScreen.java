import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class faunaScreen extends Screen {
	
	public void updatePage(){}
	
	HBox hboxtop;
	HBox hboxbottom;
	VBox vbox;
	BorderPane borderPane;
	StackPane hstackPaneTop;	
	StackPane hstackPaneBottom;
	StackPane vstackPaneBottom;

	Text lepsandplantstext;
	
	String spacing;
	
	
	public faunaScreen(View v) {
		super(v, PagesEnum.FaunaScreen);
		
		borderPane = new BorderPane();
		hstackPaneTop = new StackPane();	
		vstackPaneBottom = new StackPane();
		hstackPaneBottom = new StackPane();	

    	borderPane.setTop(hstackPaneTop);
    	borderPane.setCenter(vstackPaneBottom);
    	borderPane.setBottom(hstackPaneBottom);
    	
    	hboxtop = new HBox();
    	hboxbottom = new HBox(50);
    	vbox = new VBox(10);
    	
    	spacing = "            ";
    	
		lepsandplantstext = new Text(spacing + "Plants In Garden" + spacing + "Leps Supported" + spacing +"Cost of Plant");
		
    	createReturnToPlotButton();
		createSettingsButton();
		
		hboxtop.setAlignment(Pos.TOP_RIGHT);
		hboxtop.setPadding(new Insets(10, 10, 0, 0));
		hboxtop.getChildren().addAll(settingsButton);
		
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(lepsandplantstext);
		
		hboxbottom.setAlignment(Pos.BOTTOM_CENTER);
		hboxbottom.setPadding(new Insets(0, 0, 10, 0));
		hboxbottom.getChildren().addAll(returnToPlotButton);
		
		hstackPaneTop.getChildren().addAll(hboxtop);
		vstackPaneBottom.getChildren().addAll(vbox);
		hstackPaneBottom.getChildren().addAll(hboxbottom);
		
		borderPane.setMaxHeight(canvasHeight);
		borderPane.setMaxWidth(canvasHeight);
		borderPane.setStyle(theme); 
		borderPane.setStyle(theme);
		
    	layout = borderPane;
    	scene = new Scene(layout, canvasWidth, canvasHeight);
	}
	
	public void addPlants() {
	}
}
	