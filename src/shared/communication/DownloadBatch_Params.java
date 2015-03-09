package shared.communication;

import shared.model.*;

/**
 * Parameter object class for downloading a project
 * @author phelpsdb
 *
 */
public class DownloadBatch_Params {
	/**
	 * The id of the project in which a project will be found 
	 */
	private int projectId;
	/**
	 * The user to whom the batch should be assigned
	 */
	private User user;

	public DownloadBatch_Params() {
		projectId = -1;
	}

	/**
	 * @return projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
