package shared.communication;

import java.util.List;

import shared.model.Project;

/**
 * Communication result class for retrieving projects from the database
 * @author phelpsdb
 *
 */
public class GetProjects_Result extends Result {
	/**
	 * A list of projects retrieved
	 */
	private List<Project> projects;

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	/**
	 * Stringify the results as defined in the project spec
	 */
	public String toString() {
		return "";
	}

}
