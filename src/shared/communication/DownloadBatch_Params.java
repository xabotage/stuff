package shared.communication;

/**
 * Parameter object class for downloading a project
 * @author phelpsdb
 *
 */
public class DownloadBatch_Params extends Params {
	/**
	 * The id of the project in which a project will be found 
	 */
	private int projectId;

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
}
