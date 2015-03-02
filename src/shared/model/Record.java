package shared.model;

import java.util.List;

/**
 * Model class for a Record or row of information in a batch
 * @author phelpsdb
 *
 */
public class Record {
	/**
	 * The unique id of this record
	 */
	private int recordId;
	/**
	 * The id of the batch to which this record belongs
	 */
	private int batchId;
	/**
	 * A list of field values corresponding to this record
	 */
	private List<FieldValue> fieldValues;
	/**
	 * @return the recordId
	 */
	public int getRecordId() {
		return recordId;
	}
	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
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
	 * @return the fieldValues
	 */
	public List<FieldValue> getFieldValues() {
		return fieldValues;
	}
	/**
	 * @param fieldValues the fieldValues to set
	 */
	public void setFieldValues(List<FieldValue> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
}
