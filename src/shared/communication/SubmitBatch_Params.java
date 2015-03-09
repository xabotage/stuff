package shared.communication;

import shared.model.*;

/**
 * Communication parameter class for submitting an indexed batch
 * @author phelpsdb
 *
 */
public class SubmitBatch_Params extends Params {
	/**
	 * The batch to be submitted
	 */
	private Batch batch;

	public SubmitBatch_Params() {
		batch = null;
	}

	/**
	 * @return the current batch
	 */
	public Batch getBatch() {
		return batch;
	}

	/**
	 * @param batch the new batch
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
}
