import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
* @author	Himanshu Patel
* @author	Kush Patel
*/
public class Model {

	private double x = 100;
	private double y = 200;
	int leps;
	double currentBudget;
	double remainingBudget;
	boolean initialbudget;
	String commonname;
	HashMap<String, Plant> plants = new HashMap<>();
	
	/**
	* Constructor for Model. Calls addPlants method to add all the plants to the Application.
	* Initializes the values for leps, current budget, remaining budget and initial budget.
	*/
	public Model()
	{
		addPlants();
		leps = 0;
		currentBudget = 0;
		remainingBudget = currentBudget;
		initialbudget = true;
	}
	
	/**
	* Calls makeHashSet method to make the HashSet of all the plants in the Application.
	* Try, catch block prints error message if there is an Exception and loading file fails.
	*/
	public void addPlants()
	{	
		try {makeHashSet();}
        catch(Exception ex) {System.out.println("Loading file error with: " + ex.toString());}	
	}
	
	/**
	* Reads/scans a text file containing all the plants and their information. Creates Plant objects 
	* with that information and adds all the plants to a HashMap. Plant information is parsed/separated
	* by a ',' indicating a split.
	* 
	* @throws FileNotFoundException thrown when attempting to access a file that doesn't exist
	*/
	public void makeHashSet() throws FileNotFoundException
	{
		File file = new File("Plants.txt");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine())
		{
			String tempFullString = scanner.nextLine();
			String[] array = tempFullString.split(",");
			String plantType = "herbaceous";
			if(array[4].equals("w"))
			{
				plantType = "woody";
			}
			plants.put(array[1], new Plant(array[0], array[1], plantType, Integer.parseInt(array[2]), Integer.parseInt(array[3]), array[5], array[6], array[7]));
		}
	}
	
	/**
	* Getter for the HashMap of all the plants in the Application.
	* 
	* @return the HashMap of all the plants in the Application
	*/
	public HashMap<String, Plant> getPlants() 
    {
		return plants;
    }	
	
	/**
	* Updates the lep count when a plant is added or removed from the garden.
	* 
	* @param  plantname  common name of a plant
	* @param  remove     a boolean identify if the plant is being added/removed
	* @return            the total lep count
	*/
    public int calculateLeps(String plantname, Boolean remove) {
    	if(remove) {
    		leps = leps - plants.get(plantname).lepsSupported;
    		System.out.println(leps);
    	} else {
    		leps = leps + plants.get(plantname).lepsSupported;
    	}
    	return leps;
    }

	/**
	* Updates the budget when a plant is added or removed from the garden.
	* 
	* @param  plantname  common name of a plant
	* @param  remove     a boolean identify if the plant is being added/removed
	* @return            the remaining budget
	*/
    public double calculateBudget(String plantname, Boolean remove) {
    	if(initialbudget) {
    		remainingBudget = currentBudget;
    		initialbudget = false;
    	}
    	
    	if(remove) {
    		System.out.println(remainingBudget);
    		remainingBudget = remainingBudget + plants.get(plantname).cost;
    	} else {
    		remainingBudget = remainingBudget - plants.get(plantname).cost;
    	}
    	return remainingBudget;
    }

	/**
	* Sets the current budget.
	* 
	* @param  budget  an int value of the current budget for the garden
	*/
    public void setCurrentBudget(int budget) {
    	currentBudget = budget;
    }
        
	/**
	* Calculates the ratio of remaining budget over total budget.
	* 
	* @return the ratio of remaining budget over total budget
	*/
    public double calculateRemainOverTotalBudget() {
    	return remainingBudget/currentBudget;
    }
        
	/**
	* Sets common name of the current/latest plant edited in the garden.
	* 
	* @param  s  the current/latest plant edited in the garden
	*/
    public void setCommonName(String s) {
    	commonname = s;
    }
    
	/**
	* Gets the x position of the current/latest plant edited in the garden.
	* 
	* @return the x position of the current/latest plant edited in the garden
	*/
    public double getX() {
		return x;
	}

	/**
	* Sets the x position of the current/latest plant edited in the garden.
	* 
	* @param  x  the x location of a plant
	*/
	public void setX(double x) {
		this.x = x;
	}

	/**
	* Gets the y location of the current/latest plant edited in the garden.
	* 
	* @return the y location of the current/latest plant edited in the garden
	*/
	public double getY() {
		return y;
	}

	/**
	* Sets the y location of the current/latest plant edited in the garden.
	* 
	* @param  y  the y location of a plant
	*/
	public void setY(double y) {
		this.y = y;
	}
}
