package eventObject;

import java.io.File;

public class ReaderTester {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(".");
		EventReader test = new EventReader();
		String filename = "eventFileUpdated";
		test.buildEvent(filename);

	}

}
