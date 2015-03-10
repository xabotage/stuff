package shared.communication;

import shared.model.*;

/**
 * Communication result class for downloading a batch
 * @author phelpsdb
 *
 */
public class DownloadBatch_Result extends Result {
	/**
	 * The batch retrieved from the server
	 */
	private Batch batch;
	
	/**
	 * The project associated with the batch
	 */
	private Project project;
	
	/**
	 * http://something.com:8989/
	 */
	private String urlBase;

	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	public DownloadBatch_Result() {
		this.setUrlBase("");
	}

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		assert(project.getFields().size() > 0);
		StringBuilder result = new StringBuilder();
		result.append(batch.getBatchId());
		result.append('\n');
		result.append(batch.getProjectId());
		result.append('\n');
		result.append(batch.getImageFile());
		result.append('\n');
		result.append(project.getFirstYCoord());
		result.append('\n');
		result.append(project.getRecordHeight());
		result.append('\n');
		result.append(project.getRecordsPerImage());
		result.append('\n');
		result.append(project.getFields().size());
		result.append('\n');
		// TODO: maybe field model class should just have a number assigned
		int x = 1;
		for(Field f : project.getFields()) {
			result.append(f.getFieldId());
			result.append('\n');
			result.append(x);
			result.append('\n');
			result.append(f.getTitle());
			result.append('\n');
			result.append(urlBase);
			result.append(f.getHelpUrl());
			result.append('\n');
			result.append(f.getxCoord());
			result.append('\n');
			result.append(f.getWidth());
			result.append('\n');
			if(f.getKnownData() != null) {
				result.append(urlBase);
				result.append(f.getKnownData());
				result.append('\n');
			}
			x++;
		}
		return result.toString();
	}

	/**
	 * @return the urlBase
	 */
	public String getUrlBase() {
		return urlBase;
	}

	/**
	 * @param urlBase the urlBase to set
	 */
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

}
