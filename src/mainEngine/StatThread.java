package mainEngine;

import java.util.concurrent.ArrayBlockingQueue;

public class StatThread {
	static ArrayBlockingQueue<String> _q;
	static StatThreadReceiver _receiver;
	
	public static void main(String[] args) {
		_receiver = new StatThreadReceiver(_q);
		_q = new ArrayBlockingQueue<String>(100);
		
		/*
		 * Here goes an instantiation of a bunch of statistical elements that can be update (TBD)
		 */
		
		
		while(true) {
			if(!_q.isEmpty()) {
				//if there is an element in the array blocking queue pop it off and handle it
				doStatistics(_q.remove());
			}
		}
	}
	
	private static void doStatistics(String str) {
		/*
		 * This method should not be called by anyone but the StatThread. When the ArrayBlockingQueue is not empty, this method
		 * will be called to update numbers and redo statistical calculations
		 */
	}
	

}
