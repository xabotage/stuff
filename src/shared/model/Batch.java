package shared.model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;

import server.database.DataImporter;
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
	 * The userId of the user assigned to complete this batch
	 */
	private int assignedUser;
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
	 * @return the assignedUser
	 */
	public int getAssignedUser() {
		return assignedUser;
	}
	/**
	 * @param assignedUser the assignedUser to set
	 */
	public void setAssignedUser(int assignedUser) {
		this.assignedUser = assignedUser;
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

	public Batch(int batchId, int projectId, String imageFile, boolean isIndexed, int assignedUser) {
		this.batchId = batchId;
		this.projectId = projectId;
		this.imageFile = imageFile;
		this.isIndexed = isIndexed;
		this.assignedUser = assignedUser;
	}
	
	public Batch(Element batchElement) {
		imageFile = DataImporter.getValue((Element)batchElement.getElementsByTagName("file").item(0));
		Element recordsRootElement = (Element)batchElement.getElementsByTagName("records").item(0);
		NodeList recordElements = recordsRootElement.getElementsByTagName("record");
		records = new ArrayList<Record>();
		for(int i = 0; i < recordElements.getLength(); i++) {
			records.add(new Record((Element)recordElements.item(i)));
		}
		
		isIndexed = false;
	}
}
