package client;

public class IndexerController {

	public IndexerController(IndexerFrame searchFrame) {
		this.searchFrame = searchFrame;
	}

	private IndexerFrame searchFrame;

	public IndexerFrame getIndexerFrame() {
		return searchFrame;
	}

	public void setIndexerFrame(IndexerFrame searchFrame) {
		this.searchFrame = searchFrame;
	}
	
	public void getProjectsFromServer() {
		ClientCommunicator cu = new ClientCommunicator(searchFrame.getHost(), 
				Integer.parseInt(searchFrame.getPort()));
		GetProjects_Params params = new GetProjects_Params();
		params.setUserName(searchFrame.getUserName());
		params.setPassword(searchFrame.getPassword());

		GetFields_Params fParams = new GetFields_Params();
		fParams.setUserName(searchFrame.getUserName());
		fParams.setPassword(searchFrame.getPassword());
		
		try {

			GetProjects_Result result = cu.getProjects(params);
			List<Project> projects = result.getProjects();
			for(Project p : projects) {
				fParams.setProjectId(p.getProjectId());
				p.setFields(cu.getFields(fParams).getFields());
			}
			searchFrame.generateProjectsComponent(projects);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
