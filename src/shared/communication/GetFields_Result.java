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

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Field f : fields) {
			result.append(f.getProjectId());
			result.append('\n');
			result.append(f.getFieldId());
			result.append('\n');
			result.append(f.getTitle());
			result.append('\n');
		}
		return result.toString();
	}

}
