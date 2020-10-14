package mainEngine;

import java.util.concurrent.ArrayBlockingQueue;

public class StatThreadReceiver {
	private ArrayBlockingQueue<String> _q = null;

	protected StatThreadReceiver(ArrayBlockingQueue<String> q) {
		_q=q;

	}
	 
	
	private void receive(String str) throws Exception {
		if(str == null) {
			throw new Exception("Command is null");
		}else {
			_q.add(str); //command is added to the queue
		}
		
	}
}
