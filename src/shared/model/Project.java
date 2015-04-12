package shared.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import server.database.DataImporter;

/**
 * Model class for a project or collection of batches needing to be indexed
 * @author phelpsdb
 *
 */
public class Project {
	/**
	 * The unique id of the project
	 */
	private int projectId;
	/**
	 * The descriptive title of this project
	 */
	private String title;
	/**
	 * The number of records on each batch of this project
	 */
	private int recordsPerImage;
	/**
	 * The Y coordinate in pixels of the first record in each batch
	 */
	private int firstYCoord;
	/**
	 * The height in pixels of each record on each batch
	 */
	private int recordHeight;
	/**
	 * the Fields associated with this project
	 */
	private List<Field> fields;
	/**
	 * the Batches associated with this project
	 */
	private List<Batch> batches;
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
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage() {
		return recordsPerImage;
	}
	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}
	/**
	 * @return the firstYCoord
	 */
	public int getFirstYCoord() {
		return firstYCoord;
	}
	/**
	 * @param firstYCoord the firstYCoord to set
	 */
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}
	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}
	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}
	
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
	 * @return the batches
	 */
	public List<Batch> getBatches() {
		return batches;
	}
	/**
	 * @param batches the batches to set
	 */
	public void setBatches(List<Batch> batches) {
		this.batches = batches;
	}
	public Project() {
		projectId = -1;
	}
	
	public Project(String title, int recordsPerImage, int recordHeight, int firstYCoord) {
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.recordHeight = recordHeight;
		this.firstYCoord = firstYCoord;
	}
	
	public Project(Element projectElement) {
		title = DataImporter.getValue((Element)projectElement.getElementsByTagName("title").item(0));
		recordsPerImage = Integer.parseInt(DataImporter.getValue(
				 	(Element)projectElement.getElementsByTagName("recordsperimage").item(0)));

		firstYCoord = Integer.parseInt(DataImporter.getValue(
				(Element)projectElement.getElementsByTagName("firstycoord").item(0)));

		recordHeight = Integer.parseInt(DataImporter.getValue(
				(Element)projectElement.getElementsByTagName("recordheight").item(0)));

		fields = new ArrayList<Field>();
		Element fieldsElement = (Element)projectElement.getElementsByTagName("fields").item(0);
		NodeList fieldElements = fieldsElement.getElementsByTagName("field");
		for(int i = 0; i < fieldElements.getLength(); i++) {
			fields.add(new Field((Element)fieldElements.item(i)));
		}
		
		
		Element imagesRootElement = (Element)projectElement.getElementsByTagName("images").item(0);
		NodeList imageElements = imagesRootElement.getElementsByTagName("image");
		batches = new ArrayList<Batch>();
		for(int i = 0; i < imageElements.getLength(); i++) {
			batches.add(new Batch((Element)imageElements.item(i)));
		}
	}
	
	@Override
	public String toString() {
		return this.title;
	}
	
}
