package shared.communication;

import java.net.URL;

/**
 * Communication result class for retrieving a sample image
 * @author phelpsdb
 *
 */
public class GetSampleImage_Result extends Result {
	/**
	 * The image URL obtained from the server
	 */
	private URL imageUrl;

	/**
	 * @return the imageUrl
	 */
	public URL getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		return imageUrl.toString();
	}

}
