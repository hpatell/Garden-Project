import static org.junit.Assert.*;
import org.junit.Test;

public class PlantTest {

	Model model = new Model();
	
	@Test
	public void testPlant() {
		
		Plant plant = new Plant("Acalypha virginica", "copperleaf", "herbaceous", 5, 6, "Full Sun", "Medium", "Sand");
		
		assertEquals(plant.scientificName, model.plants.get("copperleaf").scientificName);
	}

}
