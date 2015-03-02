package server.database;

import shared.model.*;

/**
 * Database access class for FieldValue objects
 * @author phelpsdb
 *
 */
public class FieldValueDAO {
	
	/**
	 * Reads a field value from the database
	 * @param valueId the id of the field to be read
	 * @return A FieldValue object
	 */
	public FieldValue readFieldValue(int valueId) {
		return null;
	}

	/**
	 * Update a field value in the database
	 * @param fv field value object to update
	 */
	public void updateFieldValue(FieldValue fv) {
	}
	
	/**
	 * Create a new field value in the database
	 * @param fv field value object to create
	 */
	public void createFieldValue(FieldValue fv) {
	}
	
	/**
	 * Delete a fieldValue from the database
	 * @param fieldValueId unique id of the field value to delete
	 */
	public void deleteFieldValue(int fieldValueId) {
	}

}
