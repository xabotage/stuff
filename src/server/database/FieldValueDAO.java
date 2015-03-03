package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.model.*;

/**
 * Database access class for FieldValue objects
 * @author phelpsdb
 *
 */
public class FieldValueDAO {
	private Database db;
	
	public FieldValueDAO(Database db) {
		this.db = db;
	}
	
	/**
	 * Reads a field value from the database
	 * @param valueId the id of the field to be read
	 * @return A FieldValue object
	 */
	public FieldValue readFieldValue(int valueId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		FieldValue fv = null;
		try {
			String getFieldSQL = "SELECT * FROM FieldValue where valueId = ?";
			ps = db.getConnection().prepareStatement(getFieldSQL);
			ps.setInt(1, valueId);
			rs = ps.executeQuery();
			while(rs.next()) {
				fv = new FieldValue();
				fv.setValueId(rs.getInt("valueId"));
				fv.setFieldId(rs.getInt("fieldId"));
				fv.setRecordId(rs.getInt("recordId"));
				fv.setValue(rs.getString("value"));
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
		return fv;
	}

	/**
	 * Update a field value in the database
	 * @param fv field value object to update
	 */
	public void updateFieldValue(FieldValue fv) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateFieldSQL = "UPDATE FieldValue SET fieldId = ?, recordId = ?, value = ? "
					+ " WHERE valueId = ?";
			ps = db.getConnection().prepareStatement(updateFieldSQL);
			ps.setInt(1, fv.getFieldId());
			ps.setInt(2, fv.getRecordId());
			ps.setString(3, fv.getValue());
			ps.setInt(4, fv.getValueId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update fieldvalue");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update fieldvalue", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	/**
	 * Create a new field value in the database
	 * @param fv field value object to create
	 */
	public void createFieldValue(FieldValue fv) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createFieldSQL = "INSERT INTO FieldValue (fieldId, recordId, value) "
					+ "VALUES (?, ?, ?)";
			ps = db.getConnection().prepareStatement(createFieldSQL);
			ps.setInt(1, fv.getFieldId());
			ps.setInt(2, fv.getRecordId());
			ps.setString(3, fv.getValue());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				fv.setFieldId(id);
			}
			else {
				throw new DatabaseException("Could not insert fieldvalue");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert fieldvalue", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	/**
	 * Delete a fieldValue from the database
	 * @param fieldValueId unique id of the field value to delete
	 */
	public void deleteFieldValue(int valueId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteFieldSQL = "DELETE FROM FieldValue WHERE valueId = ?";
			ps = db.getConnection().prepareStatement(deleteFieldSQL);
			ps.setInt(1, valueId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete fieldvalue");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete fieldvalue", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}

}
