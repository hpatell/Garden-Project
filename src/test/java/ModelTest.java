import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class ModelTest {
	
	Model model = new Model();
	
	@Test
	public void testModel() {
		
		model = new Model();
	}
	
	@Test
	public void testAddPlants() {
		
		model.addPlants();
	}
	
	@Test
	public void testMakeHashSet() throws FileNotFoundException {
		
		model.makeHashSet();
	}
	
	@Test
	public void testGetPlants() {
		
		assertEquals(model.plants, model.getPlants());
	}
	
	@Test
	public void testCalculateLeps() {
		
		assertEquals(5, model.calculateLeps("copperleaf", false));
	}
	
	@Test
	public void testCalculateBudget() {
        
		assertEquals(-6, model.calculateBudget("copperleaf", false));
    }
	
	@Test
	public void testSetCurrentBudget() {
        
		model.setCurrentBudget(10);
		assertEquals(10, model.currentBudget);
    }
	
	@Test
	public void testCalculateRemainOverTotalBudget() {
        
		model.remainingBudget = 10;
		model.currentBudget = 5;
		assertEquals(2, model.calculateRemainOverTotalBudget());
    }
	
	@Test
	public void testSetCommonName() {
        
		model.setCommonName("copperleaf");
		assertEquals("copperleaf", model.commonname);
    }
	
	@Test
	public void testGetX() {
		
		assertEquals(100, model.getX());
	}
	
	@Test
	public void testSetX() {
		
		model.setX(275);
		assertEquals(275, model.getX());
	}
	
	@Test
	public void testGetY() {
		
		assertEquals(200, model.getY());
	}

	@Test
	public void testSetY() {
		
		model.setY(361);
		assertEquals(361, model.getY());
	}
}