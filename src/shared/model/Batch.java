package shared.model;

import java.util.List;

import shared.model.Record;

/**
 * Model class for a batch or image of records to be indexed
 * @author phelpsdb
 *
 */
public class Batch {
	/**
	 * The unique id of this batch
	 */
	private int batchId;
	/**
	 * The id of the project that this batch belongs to
	 */
	private int projectId;
	/**
	 * The filename of the image for this batch
	 */
	private String imageFile;
	/**
	 * Whether or not the batch is indexed
	 */
	private boolean isIndexed;
	/**
	 * The record objects associated with an indexed batch.
	 */
	private List<Record> records;

	/**
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the imageFile
	 */
	public String getImageFile() {
		return imageFile;
	}
	/**
	 * @param imageFile the imageFile to set
	 */
	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	/**
	 * @return the isIndexed
	 */
	public boolean isIndexed() {
		return isIndexed;
	}
	/**
	 * @param isIndexed the isIndexed to set
	 */
	public void setIndexed(boolean isIndexed) {
		this.isIndexed = isIndexed;
	}
	/**
	 * @return the records
	 */
	public List<Record> getRecords() {
		return records;
	}
	/**
	 * @param records the records to set
	 */
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	
	public Batch() {
		batchId = -1;
	}
}
