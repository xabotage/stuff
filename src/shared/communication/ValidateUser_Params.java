package shared.communication;

import shared.model.*;

/**
 * Communicator parameter class for validating a user
 * @author phelpsdb
 *
 */
public class ValidateUser_Params {
	private User user;

	public ValidateUser_Params() {
		user = null;
	}

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
	
}
