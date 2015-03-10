package shared.communication;

/**
 * Communication result class for retrieving a sample image
 * @author phelpsdb
 *
 */
public class GetSampleImage_Result extends Result {
	/**
	 * The image String obtained from the server
	 */
	private String imageUrl;

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		return imageUrl.toString();
	}

}
