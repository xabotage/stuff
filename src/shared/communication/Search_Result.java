package shared.communication;

import java.util.List;

import shared.model.SearchResultObject;

/**
 * Result class for search function
 */
public class Search_Result extends Result {
	/**
	 * Results returned from the search
	 */
	private List<SearchResultObject> searchResults;
	
	
	/**
	 * @return the searchResults
	 */
	public List<SearchResultObject> getSearchResults() {
		return searchResults;
	}


	/**
	 * @param searchResults the searchResults to set
	 */
	public void setSearchResults(List<SearchResultObject> searchResults) {
		this.searchResults = searchResults;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(SearchResultObject sro : searchResults) {
			result.append(sro.getBatchId());
			result.append('\n');
			result.append(sro.getImageUrl().toString());
			result.append('\n');
			result.append(sro.getRecordNumber());
			result.append('\n');
			result.append(sro.getFieldId());
			result.append('\n');
		}
		return result.toString();
	}
}
