package shared.communication;

/**
 * The communication parameter class for downloading a file from the server
 * @author phelpsdb
 *
 */
public class DownloadFile_Params {
	/**
	 * The name of the file to be downloaded
	 */
	private String filename;

	public DownloadFile_Params() {
		filename = null;
	}

	/**
	 * @return The filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename The new filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

}
