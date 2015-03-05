package shared.communication;

import shared.model.Batch;

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
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		return "";
	}

}
