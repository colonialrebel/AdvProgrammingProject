package mainEngine;

public class StatRequest {
	/*
	 * This class is a cheeky way of making a queuable object that simply stores the numbers without regards for event descriptions
	 * and is easy to unpack and use
	 */

	public int _pop;
	public double _covidRate;
	public double _density;
	public boolean _wearingMask;
	
	public StatRequest(int pop,double covidRate, double density, int wearingMask) {
		_pop=pop;
		_covidRate = covidRate;
		_density = density/(double)100;
		if(wearingMask>=1) {_wearingMask=true;}else {_wearingMask=false;}	
	}

}
