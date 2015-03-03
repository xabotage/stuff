package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class ProjectDAO {
	private Database db;
	
	public ProjectDAO(Database db) {
		this.db = db;
	}

	public Project readProject(int projectId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Project project = null;
		try {
			String getProjectSQL = "SELECT * FROM Project where projectId = ?";
			ps = db.getConnection().prepareStatement(getProjectSQL);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while(rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt("projectId"));
				project.setTitle(rs.getString("title"));
				project.setRecordsPerImage(rs.getInt("recordsPerImage"));
				project.setRecordHeight(rs.getInt("recordHeight"));
				project.setFirstYCoord(rs.getInt("firstYCoord"));
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(ps);
		}
		return project;
	}
	
	public List<Project> readProjects() throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Project> projects = new ArrayList<Project>();
		Project project;
		try {
			String getProjectsSQL = "SELECT * FROM Project";
			ps = db.getConnection().prepareStatement(getProjectsSQL);
			rs = ps.executeQuery();
			while(rs.next()) {
				project = new Project();
				project.setProjectId(rs.getInt("projectId"));
				project.setTitle(rs.getString("title"));
				project.setRecordsPerImage(rs.getInt("recordsPerImage"));
				project.setRecordHeight(rs.getInt("recordHeight"));
				project.setFirstYCoord(rs.getInt("firstYCoord"));
				projects.add(project);
			}
		}
		catch (SQLException e) {
			DatabaseException serverEx = new DatabaseException(e.getMessage(), e);
			throw serverEx;
		}		
		finally {
			Database.safeClose(rs);
			Database.safeClose(ps);
		}
		return projects;
	}
	
	public void createProject(Project project) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createProjectSQL = "INSERT INTO Project (title, recordsPerImage, recordHeight, firstYCoord) "
					+ "VALUES (?, ?, ?, ?)";
			ps = db.getConnection().prepareStatement(createProjectSQL);
			ps.setString(1, project.getTitle());
			ps.setInt(2, project.getRecordsPerImage());
			ps.setInt(3, project.getRecordHeight());
			ps.setInt(4, project.getFirstYCoord());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				project.setProjectId(id);
			}
			else {
				throw new DatabaseException("Could not insert project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert project", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	public void updateProject(Project project) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateProjectSQL = "UPDATE Project SET title = ?, recordsPerImage = ?, recordHeight = ?, "
					+ "firstYCoord = ? WHERE projectId = ?";
			ps = db.getConnection().prepareStatement(updateProjectSQL);
			ps.setString(1, project.getTitle());
			ps.setInt(2, project.getRecordsPerImage());
			ps.setInt(3, project.getRecordHeight());
			ps.setInt(4, project.getFirstYCoord());
			ps.setInt(5, project.getProjectId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update project", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	public void deleteProject(int projectId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteProjectSQL = "DELETE FROM Project WHERE projectId = ?";
			ps = db.getConnection().prepareStatement(deleteProjectSQL);
			ps.setInt(1, projectId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete project");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete project", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
}
