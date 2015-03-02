/**
 * 
 */
package shared.communication;

import java.io.File;

/**
 * The Communication result class for downloading a file
 * @author phelpsdb
 *
 */
public class DownloadFile_Result extends Result {
	/**
	 * the file retrieved from the server
	 */
	private File file;

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

}
