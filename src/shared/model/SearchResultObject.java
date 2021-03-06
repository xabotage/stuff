package shared.model;


public class SearchResultObject {
	private int batchId;
	private String imageUrl;
	private int recordNumber;
	private int fieldId;
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
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
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
	 * @return the fieldId
	 */
	public int getFieldId() {
		return fieldId;
	}
	/**
	 * @param fieldId the fieldId to set
	 */
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

}
