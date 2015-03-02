package shared.communication;

import shared.model.Batch;

/**
 * Communication result class for retrieving a sample image
 * @author phelpsdb
 *
 */
public class GetSampleImage_Result extends Result {
	/**
	 * The batch obtained from the server
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

}
