package shared.communication;

/**
 * Contains parameters and functions used by all Result classes
 */
public abstract class Result {
	/**
	 * Indicates whether or not the user was successfully authenticated
	 */
	private boolean authenticated;

	/**
	 * @return whether or not the user is authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * @param authenticated sets authentification status
	 */
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

}
