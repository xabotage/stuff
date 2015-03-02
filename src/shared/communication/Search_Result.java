package shared.communication;

import java.util.List;

/**
 * Result class for search function
 */
public class Search_Result extends Result {
	/**
	 * Results returned from the search
	 */
	private List<SearchResultTuple> searchResults;
	
	
	/**
	 * @return the searchResults
	 */
	public List<SearchResultTuple> getSearchResults() {
		return searchResults;
	}


	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<SearchResultTuple> searchResults) {
		this.searchResults = searchResults;
	}


	/**
	 * Utility class for storing the search results
	 * @author phelpsdb
	 *
	 */
	public class SearchResultTuple {
		/**
		 * The id of the batch where the result was found
		 */
		private int batchId;
		/**
		 * The url of the image for this batch
		 */
		private String imageUrl;
		/**
		 * The row number of this record
		 */
		private int recordNumber;
		/**
		 * The id of the field for this match
		 */
		private int fieldId;
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
		 * @return the recordNumber
		 */
		public int getRecordNumber() {
			return recordNumber;
		}
		/**
		 * @param recordNumber the recordNumber to set
		 */
		public void setRecordNumber(int recordNumber) {
			this.recordNumber = recordNumber;
		}
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


		
	}

}
