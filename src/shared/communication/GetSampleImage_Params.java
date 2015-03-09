package shared.communication;

/**
 * Communication class for retrieving a sample image from the database
 * @author phelpsdb
 *
 */
public class GetSampleImage_Params extends Params {
	/**
	 * The id of the project to retrieve a sample image from
	 */
	private int projectId;

	public GetSampleImage_Params() {
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
