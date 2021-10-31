import java.util.HashMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class serializable {
	Model model;
	View view;
	HashMap<String, Object> objects = null;

	public serializable() {
		// NO parameters
		objects = new HashMap<String, Object>();
		this.model = null;
		this.view = null;
	}
	
	public void saveData(Model model, View view) {

		String savedGardenName = "hardCodedFileName";
		this.objects.put("model", model);
		this.objects.put("view", view);

		try 
		{
			FileOutputStream fos = new FileOutputStream(savedGardenName + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(objects);
			oos.close();
		}
		catch (Exception ex)
		{
			System.out.println("Exception thrown while trying to save the garden:" + ex.toString());
		}
	}

	public void loadData() {

		String selectedFile = "hardCodedFileName";
		try 
		{
			FileInputStream fis = new FileInputStream(selectedFile + ".ser");	
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.objects = (HashMap<String, Object>) ois.readObject(); 
			//warning: "unchecked cast" but should still work

			// loading data from saved file into serializable.java class' fields
			this.model = (Model) this.objects.get("model");
			this.view = (View) this.objects.get("view");
		}
		catch(Exception ex) 
		{
			System.out.println("Exception thrown while loading " + selectedFile + ": " + ex.toString());
		}


		if(this.model != null) {
			System.out.println("SUCCESS! model is not null");
		}
		if(this.view != null) {
			System.out.println("SUCCESS! view is not null");
		}

	}

}
