package shared.model;

import org.w3c.dom.*;

import server.database.DataImporter;

/**
 * Model class for the value of a field in a record
 * @author phelpsdb
 *
 */
public class FieldValue {
	/**
	 * The unique id of this value
	 */
	private int valueId;
	/**
	 * The id of the field that describes this value
	 */
	private int fieldId;
	/** 
	 * The id of the record containing this field value
	 */
	private int recordId;
	/**
	 * The actual value of this field
	 */
	private String value;
	/**
	 * @return the valueId
	 */
	public int getValueId() {
		return valueId;
	}
	/**
	 * @param valueId the valueId to set
	 */
	public void setValueId(int valueId) {
		this.valueId = valueId;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public FieldValue() {
	}
	
	public FieldValue(Element fvElement) {
		value = DataImporter.getValue(fvElement);
	}
	
}
