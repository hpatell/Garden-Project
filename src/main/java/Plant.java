/**
* @author	Himanshu Patel
*/
public class Plant {
	
    String scientificName, commonName, plantType, weatherType, moistureType, soilType;
    int lepsSupported;
    int cost;
    
    /**
	* Constructor for Plant. Assigns all attributes of plant an appropriate value.
	* 
	* @param  scientificName  the scientific name of the plant
	* @param  commonName      the common name of the plant
	* @param  plantType       if the plant type is either woody or herbaceous
	* @param  lepsSupported   the number of leps the plant supports
	* @param  cost            the cost of the plant
	* @param  weatherType     the amount of sun exposure of the plant
	* @param  moistureType    the moisture level of the soil the plant is grown in
	* @param  soilType        the soil type the plant is grown in
	*/
    public Plant(String scientificName, String commonName, String plantType, int lepsSupported, int cost, String weatherType, String moistureType, String soilType) 
    {
        this.scientificName = scientificName; 
        this.commonName = commonName;
        this.plantType = plantType;
        this.lepsSupported = lepsSupported;
        this.cost = cost;
        this.weatherType = weatherType;
        this.moistureType = moistureType;
        this.soilType = soilType;
    }
}
