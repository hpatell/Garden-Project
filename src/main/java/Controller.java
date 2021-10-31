import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
* @author	Himanshu Patel
* @author	Kush Patel
*/
public class Controller {

	private final boolean DEBUG = true;
    Model model;
    View view;

    /**
	* Constructor for the Controller, assigns value to view. Creates an instance of model.
	* If statement used for debugging problems with code.
	* 
	* @param  view  takes in the instance of View accessed in Screen
	*/
    public Controller(View view) {
    	this.view = view;
    	model = new Model();
    	if (DEBUG) System.out.println("ic created");
    }
    
	/**
	* Updates the dynamic aspects of every screen such as remaining budget and lep count.
	*/
    public void update() {
    	model.setCurrentBudget(Integer.parseInt(view.gardeninit.gardenbudgetlocal));
    	model.calculateLeps(view.modify.getCommonName(), false);
    	model.calculateBudget(view.modify.getCommonName(), false);
       	view.modify.setRemainingBudget(String.valueOf(model.remainingBudget));
    	view.modify.setTotalLeps(String.valueOf(model.leps));
    	view.summary.update(model.calculateRemainOverTotalBudget(), model.plants.get(view.modify.getCommonName()).scientificName, 
    			String.valueOf(model.plants.get(view.modify.getCommonName()).lepsSupported), String.valueOf(model.plants.get(view.modify.getCommonName()).cost));
    	view.modify.update(model.plants.get(view.modify.getCommonName()).plantType, String.valueOf(model.plants.get(view.modify.getCommonName()).lepsSupported), String.valueOf(model.plants.get(view.modify.getCommonName()).cost), model.plants.get(view.modify.getCommonName()).scientificName);
    	model.setCommonName(view.modify.getCommonName());
    }

	/**
	* Drag logic, gets the plant that was dragged and assigns it to n, then updates the x, y locations of the plant
	* based on the x, y location set in model and the x, y location identified by the mouse movements. Translates 
	* the movements for the circle plant object. Sets the drag boundaries so plants can't be dragged out of the garden
	* plot. Checks for drag collision of plants and prevents the collision.
	* 
	* @param  event  the MouseEvent for dragging the plants in the garden, mouse movements by user
	* @param  iv     the circle object of the plant being dragged
	*/ 
    public void drag(MouseEvent event, Circle iv) {   	
    	Node n = (Node)event.getSource();
		if (DEBUG) System.out.println("ic mouse drag tx: " + n.getTranslateX() + ", ex: " + event.getX() );
		model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		model.setY(model.getY() + event.getY());
		
		double oldX = n.getTranslateX();
		double oldY = n.getTranslateY();
		
		n.setTranslateX(n.getTranslateX() + event.getX() - view.modify.scaledImgWidth/2);
		n.setTranslateY(n.getTranslateY() + event.getY() - view.modify.scaledImgHeight/2);
		
		if(view.modify.checkCollision(iv))
		{
			n.setTranslateX(oldX);
			n.setTranslateY(oldY);
		}
		
		// Check Garden Bounds
		if(n.getTranslateX() > view.modify.anchorPane.getWidth() - view.modify.scaledImgWidth)
		{
			n.setTranslateX(view.modify.anchorPane.getWidth() - view.modify.scaledImgWidth);
		}
		if(n.getTranslateX() < 0)
		{
			n.setTranslateX(0);
		}
		if(n.getTranslateY() < 0)
		{
			n.setTranslateY(0);
		}
		if(n.getTranslateY() > view.modify.anchorPane.getHeight() - view.modify.scaledImgHeight)
		{
			n.setTranslateY(view.modify.anchorPane.getHeight() - view.modify.scaledImgHeight);
		}
    }

	/**
	* EventHandler of MouseEvent for drag, used in ModifyPlotScreen for when the plant is dragged in the garden.
	* Uses lambda to get mouse movements.
	* 
	* @param  iv  the circle object of the plant being dragged
	* @return     the MouseEvent for drag, event of mouse movement
	*/
    public EventHandler<MouseEvent> getDragHandler(Circle iv)
    {
    	return event -> drag((MouseEvent) event, iv);
    }

    /**
	* EventHandler of MouseEvent for setOnDragDetected when starting the Drag and Drop Gesture on a plant ImageView.
	* Used in ModifyPlotScreen when the plant ImageView is clicked and dragged.
	* 
	* @param  iv  the ImageView of the plant being drag and dropped
	* @return     the EventHandler of the DRAG_DETECTED MouseEvent
	*/
    public EventHandler<MouseEvent> getOnGardenDragDetected(ImageView iv)
    {
    	return new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event)
    		{
    			view.modify.onGardenDragDetected(event, iv);
    		}
    	};
    }
    
    /**
	* EventHandler of DragEvent for setOnDragOver when handling a DRAG_OVER event on a target.
	* Used in ModifyPlotScreen when the plant ImageView is dragged over a potential drop target.
	* 
	* @return the EventHandler of the DRAG_OVER DragEvent
	*/
    public EventHandler<DragEvent> getOnGardenDragOver()
    {
    	return new EventHandler<DragEvent>() {
    		public void handle(DragEvent event)
    		{
    			view.modify.onGardenDragOver(event);
    		}
    	};
    }
    
	/**
	* EventHandler of DragEvent for setOnDragDropped when handling a DRAG_DROPPED event on a target.
	* Used in ModifyPlotScreen when the plant ImageView is dropped on the garden plot/AnchorPane.
	* 
	* @return the EventHandler of the DRAG_DROPPED DragEvent
	*/ 
    public EventHandler<DragEvent> getOnGardenDragDropped()
    {
    	return new EventHandler<DragEvent>() {
    		public void handle(DragEvent event) 
    		{
    			view.modify.onGardenDragDropped(event);
    			update();
    		}
    	};
    }
    
	/**
	* EventHandler of setOnAction ActionEvent for CheckBox filtering of Woody and Herbaceous plant types.
	* Used in ModifyPlotScreen when the Woody and Herbaceous CheckBoxes are clicked on.
	* 
	* @param  imageViews  the HashMap of current ImageViews in the plant selection menu based on user selected conditions
	* @return the EventHandler of the setOnAction ActionEvent
	*/ 
    public EventHandler<ActionEvent> getCheckboxHandler(HashMap<String, ImageView> imageViews)
    {
    	return new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent event)
    		{
    			view.modify.checkbox(event, imageViews, model.plants);
    		}
    	};
    }
    
	/**
	* 
	* EventHandler of MouseEvent for removing the plant in the garden on mouse right click.
	* Updates the dynamic aspects of relevant screens.
	* 
	* @param  iv  the circle object of the plant being removed from the garden
	* @return     the EventHandler of the setOnMouseClicked MouseEvent
	*/ 
    public EventHandler<MouseEvent> removePlant(Circle iv)
    {
       	return new EventHandler<MouseEvent>() {
       		public void handle(MouseEvent event) {
       			if(event.getButton() == MouseButton.SECONDARY) {
	       			view.modify.removePlant(event, iv);
	       	    	model.calculateLeps(view.modify.getCommonNameRemove(), true);
	       	    	model.calculateBudget(view.modify.getCommonNameRemove(), true);
	       	       	view.modify.setRemainingBudget(String.valueOf(model.remainingBudget));
	       	    	view.modify.setTotalLeps(String.valueOf(model.leps));
	       	    	view.modify.update(model.plants.get(view.modify.getCommonName()).plantType, String.valueOf(model.plants.get(view.modify.getCommonName()).lepsSupported), String.valueOf(model.plants.get(view.modify.getCommonName()).cost), model.plants.get(view.modify.getCommonName()).scientificName);
       			}
       		}
       	};
     }
    
	/**
	* Getter method to get the HashMap of all plants in the application in model.
	* 
	* @return the HashMap of all plants in the application from model
	*/
    public HashMap<String, Plant> getplants()
    {
    	return model.plants;
    }

}
