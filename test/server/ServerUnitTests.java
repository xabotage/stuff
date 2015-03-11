package server;

public class ServerUnitTests {
	
	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.database.TestUserDAO",
				"server.database.TestProjectDAO",
				"server.database.TestRecordDAO",
				"server.database.TestBatchDAO",
				"server.database.TestFieldDAO",
				"server.database.TestFieldValueDAO",
				"client.communication.TestClientCommunicator"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

