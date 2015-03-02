package shared.model;

/**
 * Model class describing attributes of a field or column in a project
 * @author phelpsdb
 *
 */
public class Field {
	/**
	 * unique id of this field
	 */
	private int fieldId;
	/**
	 * The id of the project to which this field belongs
	 */
	private int projectId;
	/**
	 * The title of the field
	 */
	private String title;
	/**
	 * The x coordinate, in pixels, of where this field's column is located horizontally
	 */
	private int xCoord;
	/**
	 * The width of this field's column
	 */
	private int width;
	/**
	 * A url to help information about indexing this field
	 */
	private String helpUrl;
	/**
	 * Data already known about this field
	 */
	private String knownData;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}
	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the helpUrl
	 */
	public String getHelpUrl() {
		return helpUrl;
	}
	/**
	 * @param helpUrl the helpUrl to set
	 */
	public void setHelpUrl(String helpUrl) {
		this.helpUrl = helpUrl;
	}
	/**
	 * @return the knownData
	 */
	public String getKnownData() {
		return knownData;
	}
	/**
	 * @param knownData the knownData to set
	 */
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}
	
}
