package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class BatchDAO {
	private Database db;
	
	public BatchDAO(Database db) {
		this.db = db;
	}
	
	public Batch readBatch(int batchId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Batch batch = null;
		try {
			String getBatchSQL = "SELECT * FROM Batch where batchId = ?";
			ps = db.getConnection().prepareStatement(getBatchSQL);
			ps.setInt(1, batchId);
			rs = ps.executeQuery();
			while(rs.next()) {
				batch = new Batch();
				batch.setBatchId(rs.getInt("batchId"));
				batch.setImageFile(rs.getString("imageFile"));
				batch.setIndexed(rs.getBoolean("isIndexed"));
				batch.setProjectId(rs.getInt("projectId"));
				batch.setAssignedUser(rs.getInt("assignedUser"));
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
		return batch;
	}
	
	public List<Batch> readBatchesForProject(int projectId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Batch> batches = new ArrayList<Batch>();
		Batch batch;
		try {
			String getBatchSQL = "SELECT * FROM Batch WHERE projectId = ?";
			ps = db.getConnection().prepareStatement(getBatchSQL);
			ps.setInt(1, projectId);
			rs = ps.executeQuery();
			while(rs.next()) {
				batch = new Batch();
				batch.setBatchId(rs.getInt("batchId"));
				batch.setImageFile(rs.getString("imageFile"));
				batch.setIndexed(rs.getBoolean("isIndexed"));
				batch.setProjectId(rs.getInt("projectId"));
				batch.setAssignedUser(rs.getInt("assignedUser"));
				batches.add(batch);
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
		return batches;
	}
	
	public void createBatch(Batch batch) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createBatchSQL = "INSERT INTO Batch (imageFile, isIndexed, projectId, assignedUser) "
					+ "VALUES (?, ?, ?, ?)";
			ps = db.getConnection().prepareStatement(createBatchSQL);
			ps.setString(1, batch.getImageFile());
			ps.setBoolean(2, batch.isIndexed());
			ps.setInt(3, batch.getProjectId());
			ps.setInt(4, batch.getAssignedUser());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				batch.setBatchId(id);
			}
			else {
				throw new DatabaseException("Could not insert batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert batch", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	public void updateBatch(Batch batch) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateBatchSQL = "UPDATE Batch SET imageFile = ?, isIndexed = ?, projectId = ?, assignedUser = ? "
					+ " WHERE batchId = ?";
			ps = db.getConnection().prepareStatement(updateBatchSQL);
			ps.setString(1, batch.getImageFile());
			ps.setBoolean(2, batch.isIndexed());
			ps.setInt(3, batch.getProjectId());
			ps.setInt(4, batch.getAssignedUser());
			ps.setInt(5, batch.getBatchId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update batch", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	public void deleteBatch(int batchId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteBatchSQL = "DELETE FROM Batch WHERE batchId = ?";
			ps = db.getConnection().prepareStatement(deleteBatchSQL);
			ps.setInt(1, batchId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete batch");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete batch", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
}
