package shared.model;

import org.w3c.dom.*;

import server.database.DataImporter;

/**
 * Model class for a user of the indexer program
 * @author phelpsdb
 *
 */
public class User {
	
	public User() {
	}
	
	public User(int id, String userName, String firstName, String lastName, String password, String email,
				int currentBatch, int indexedRecords) {
		this.userId = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.currentBatch = currentBatch;
		this.indexedRecords = indexedRecords;
	}
	/**
	 * the unique id of this user
	 */
	private int userId;
	/**
	 * The username
	 */
	private String userName;
	/**
	 * The first name of the user
	 */
	private String firstName;
	/**
	 * The last name of the user
	 */
	private String lastName;
	/**
	 * The user's password
	 */
	private String password;
	/**
	 * The user's email address
	 */
	private String email;
	/**
	 * The id of the batch currently assigned to this user
	 */
	private int currentBatch;
	/**
	 * The number of records indexed by this user
	 */
	private int indexedRecords;
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the currentBatch
	 */
	public int getCurrentBatch() {
		return currentBatch;
	}
	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setCurrentBatch(int currentBatch) {
		this.currentBatch = currentBatch;
	}
	/**
	 * @return the indexedRecords
	 */
	public int getIndexedRecords() {
		return indexedRecords;
	}
	/**
	 * @param indexedRecords the indexedRecords to set
	 */
	public void setIndexedRecords(int indexedRecords) {
		this.indexedRecords = indexedRecords;
	}
	
	public User(Element userElement) {
		userName = DataImporter.getValue((Element)userElement.getElementsByTagName("username").item(0));
		firstName = DataImporter.getValue((Element)userElement.getElementsByTagName("firstname").item(0));
		lastName = DataImporter.getValue((Element)userElement.getElementsByTagName("lastname").item(0));
		password = DataImporter.getValue((Element)userElement.getElementsByTagName("password").item(0));
		email = DataImporter.getValue((Element)userElement.getElementsByTagName("email").item(0));
		indexedRecords = Integer.parseInt(DataImporter.getValue((Element)userElement.getElementsByTagName("indexedrecords").item(0)));
		currentBatch = -1;
	}
	
}
