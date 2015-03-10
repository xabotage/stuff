package shared.communication;

import java.util.List;

/**
 * Communication parameter class for a search query
 * @author phelpsdb
 *
 */
public class Search_Params extends Params {
	/**
	 * The string value to be searched for
	 */
	private List<String> searchValues;

	/**
	 * The fields to be searched
	 */
	private List<Integer> fieldIds;

	/**
	 * @return the search value
	 */
	public List<String> getSearchValues() {
		return searchValues;
	}

	/**
	 * @param searchValue the new search value
	 */
	public void setSearchValue(List<String> searchValues) {
		this.searchValues = searchValues;
	}

	/**
	 * @return the field ids to be searched
	 */
	public List<Integer> getFieldIds() {
		return fieldIds;
	}

	/**
	 * @param fieldIds the field ids to be searched
	 */
	public void setFieldIds(List<Integer> fieldIds) {
		this.fieldIds = fieldIds;
	}

	public Search_Params() {
	}

}
