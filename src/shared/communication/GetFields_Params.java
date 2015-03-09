package shared.communication;

/**
 * Communication parameters class for getting the fields for a project
 * @author phelpsdb
 *
 */
public class GetFields_Params extends Params {
	/**
	 * The id of the project for the fields being retrieved
	 */
	private int projectId;

	public GetFields_Params() {
		projectId = -1;
	}

	/**
	 * @return the project id
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the new project id
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
