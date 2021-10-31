/**
* Used to create the enum associated to all the screens.
* 
* @author	Kush Patel
*/
public enum PagesEnum {
	
    IntroScreen("intro"),
    ImportScreen("import"),
    GuideScreen("guide"),
    InitializationScreen("initilize"),
    ModifyPlotScreen("modify"),
    FaunaScreen("fauna"),
    SummaryScreen("summary"),
	SettingsScreen("settings"),
	PreviousScreen("Previous");
	
	private String name = null;
	
	/**
	* Takes in the name of a garden screen and assigns it to name.
	* 
	* @param  s  the name of a garden screen
	*/
	private PagesEnum(String s) {
		name = s;
	}

	/**
	* Gets the name of a garden screen.
	* 
	* @return the name of a garden screen
	*/
	public String getName() {
		return name;
	}
}
