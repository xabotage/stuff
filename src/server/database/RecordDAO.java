package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class RecordDAO {
	private Database db;
	
	public RecordDAO(Database db) {
		this.db = db;
	}
	
	public Record readRecord(int recordId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Record record = null;
		try {
			String getRecordSQL = "SELECT * FROM Record where recordId = ?";
			ps = db.getConnection().prepareStatement(getRecordSQL);
			ps.setInt(1, recordId);
			rs = ps.executeQuery();
			while(rs.next()) {
				record = new Record();
				record.setRecordId(rs.getInt("recordId"));
				record.setBatchId(rs.getInt("batchId"));
				record.setRecordNum(rs.getInt("recordNum"));
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
		return record;
	}

	public List<Record> readRecordsForBatch(int batchId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Record> records = new ArrayList<Record>();
		Record record;
		try {
			String getRecordsSQL = "SELECT * FROM Record WHERE batchId = ?";
			ps = db.getConnection().prepareStatement(getRecordsSQL);
			ps.setInt(1, batchId);
			rs = ps.executeQuery();
			while(rs.next()) {
				record = new Record();
				record.setRecordId(rs.getInt("recordId"));
				record.setBatchId(rs.getInt("batchId"));
				record.setRecordNum(rs.getInt("recordNum"));
				records.add(record);
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
		return records;
	}
	
	public void updateRecord(Record record) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateRecordSQL = "UPDATE Record SET batchId = ?, recordNum = ? WHERE recordId = ?";
			ps = db.getConnection().prepareStatement(updateRecordSQL);
			ps.setInt(1, record.getBatchId());
			ps.setInt(2, record.getRecordNum());
			ps.setInt(3, record.getRecordId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update record", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	public void createRecord(Record record) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createRecordSQL = "INSERT INTO Record (batchId, recordNum) VALUES (?, ?)";
			ps = db.getConnection().prepareStatement(createRecordSQL);
			ps.setInt(1, record.getBatchId());
			ps.setInt(2, record.getRecordNum());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				record.setRecordId(id);
			}
			else {
				throw new DatabaseException("Could not insert record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert record", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	public void deleteRecord(int recordId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteRecordSQL = "DELETE FROM Record WHERE recordId = ?";
			ps = db.getConnection().prepareStatement(deleteRecordSQL);
			ps.setInt(1, recordId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete record");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete record", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}

	/*
	public void deleteRecordsForBatch(int batchId) throws DatabaseException {
		PreparedStatement ps = null;
		List<Integer> recordIds = new ArrayList<Integer>();
		try {
			String deleteRecordSQL = "DELETE FROM Record WHERE batchId = ?";
			ps = db.getConnection().prepareStatement(deleteRecordSQL);
			ps.setInt(1, batchId);
			ps.executeUpdate();
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete records for batch", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	*/

}
