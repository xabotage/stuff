package shared.communication;

import shared.model.User;

/**
 * Result class for validating a user 
 */
public class ValidateUser_Result extends Result {
	/**
	 * The user object that was authenticated
	 */
	private User user;
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	public ValidateUser_Result() {
	}

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(user.getFirstName());
		result.append('\n');
		result.append(user.getLastName());
		result.append('\n');
		result.append(user.getIndexedRecords());
		result.append('\n');
		return result.toString();
	}

}
