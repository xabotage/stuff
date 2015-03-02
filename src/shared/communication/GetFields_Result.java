package shared.communication;

import java.util.List;

import shared.model.Field;

/**
 * Communication result class for retrieving the fields of a project
 * @author phelpsdb
 *
 */
public class GetFields_Result extends Result {
	/**
	 * A list of fields retrieved from the database
	 */
	private List<Field> fields;

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}
