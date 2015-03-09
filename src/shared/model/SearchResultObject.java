package shared.model;

import java.net.URL;

public class SearchResultObject {
	private int batchId;
	private URL imageUrl;
	private int recordNumber;
	private int fieldValueId;
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
	 * @return the imageUrl
	 */
	public URL getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the recordNumber
	 */
	public int getRecordNumber() {
		return recordNumber;
	}
	/**
	 * @param recordNumber the recordNumber to set
	 */
	public void setRecordNumber(int recordNumber) {
		this.recordNumber = recordNumber;
	}
	/**
	 * @return the fieldValueId
	 */
	public int getFieldValueId() {
		return fieldValueId;
	}
	/**
	 * @param fieldValueId the fieldValueId to set
	 */
	public void setFieldValueId(int fieldValueId) {
		this.fieldValueId = fieldValueId;
	}

}
