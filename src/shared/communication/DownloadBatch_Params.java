package shared.communication;

/**
 * Parameter object class for downloading a batch
 * @author phelpsdb
 *
 */
public class DownloadBatch_Params {
	/**
	 * The id of the batch to download
	 */
	private int batchId;

	public DownloadBatch_Params() {
		batchId = -1;
	}

	/**
	 * @return batchId
	 */
	public int getBatchId() {
		return batchId;
	}

	/**
	 * @param batchId batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
}
