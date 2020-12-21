package eventObject;

public class EventObject {
	public String _name;
	public String _description;
	private String[] _c1;
	private String[] _c2;
	private String[] _c3;
	/*
	 * The order of the array in the choice arrays is as follows
	 * index		item
	 * 0			choice title
	 * 1			choice description
	 * 2			population
	 * 3			covid rate
	 * 4			density (whole number that needs to be divided by 100)	
	 * 5 			wearing a mask ('true' or 'false')
	 */
	public EventObject(String eventName, String eventDescription, String[] choice1, String[] choice2,String[] choice3) {
		_name = eventName;
		_description = eventDescription;
		_c1 = choice1;
		_c2 = choice2;
		_c3 = choice3;
	}
	
	public String getChoiceTitle(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[0];
		if(i==2) return _c2[0];
		if(i==3) return _c3[0];
		return "Not a valid choice";	
	}
	public String getChoiceDescription(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[1];
		if(i==2) return _c2[1];
		if(i==3) return _c3[1];
		return "Not a valid choice";	
	}
	public String getChoicePop(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[2];
		if(i==2) return _c2[2];
		if(i==3) return _c3[2];
		return "Not a valid choice";	
	}
	public String getChoiceCovidRate(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[3];
		if(i==2) return _c2[3];
		if(i==3) return _c3[3];
		return "Not a valid choice";	
	}
	public String getChoiceDensity(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[4];
		if(i==2) return _c2[4];
		if(i==3) return _c3[4];
		return "Not a valid choice";	
	}
	public String getChoiceMask(int i) {
		if(i>3 || i<0) return "Not a valid choice";
		if(i==1) return _c1[5];
		if(i==2) return _c2[5];
		if(i==3) return _c3[5];
		return "Not a valid choice";	
	}
}

