package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.model.*;

public class FieldDAO {
	private Database db;
	
	public FieldDAO(Database db) {
		this.db = db;
	}
	
	public Field readField(int fieldId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String getFieldSQL = "SELECT * FROM Field where fieldId = ?";
			ps = db.getConnection().prepareStatement(getFieldSQL);
			ps.setInt(1, fieldId);
			rs = ps.executeQuery();
			while(rs.next()) {
				field = new Field();
				field.setFieldId(rs.getInt("fieldId"));
				field.setHelpUrl(rs.getString("helpUrl"));
				field.setKnownData(rs.getString("knownData"));
				field.setProjectId(rs.getInt("projectId"));
				field.setTitle(rs.getString("title"));
				field.setxCoord(rs.getInt("xCoord"));
				field.setWidth(rs.getInt("width"));
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
		return field;
	}

	public void updateField(Field field) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateFieldSQL = "UPDATE Field SET title = ?, knownData = ?, helpUrl = ?, projectId = ?, "
					+ "xCoord = ?, width = ? WHERE fieldId = ?";
			ps = db.getConnection().prepareStatement(updateFieldSQL);
			ps.setString(1, field.getTitle());
			ps.setString(2, field.getKnownData());
			ps.setString(3, field.getHelpUrl());
			ps.setInt(4, field.getProjectId());
			ps.setInt(5, field.getxCoord());
			ps.setInt(6, field.getWidth());
			ps.setInt(7, field.getFieldId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update field", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	public void createField(Field field) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createFieldSQL = "INSERT INTO Field (title, knownData, helpUrl, projectId, xCoord, width) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			ps = db.getConnection().prepareStatement(createFieldSQL);
			ps.setString(1, field.getTitle());
			ps.setString(2, field.getKnownData());
			ps.setString(3, field.getHelpUrl());
			ps.setInt(4, field.getProjectId());
			ps.setInt(5, field.getxCoord());
			ps.setInt(6, field.getWidth());
			ps.setInt(7, field.getFieldId());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				field.setFieldId(id);
			}
			else {
				throw new DatabaseException("Could not insert field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert field", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	public void deleteField(int fieldId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteFieldSQL = "DELETE FROM Field WHERE fieldId = ?";
			ps = db.getConnection().prepareStatement(deleteFieldSQL);
			ps.setInt(1, fieldId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete field");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete field", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}

}
