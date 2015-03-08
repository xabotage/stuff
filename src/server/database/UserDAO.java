package server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import shared.model.*;

public class UserDAO {
	private Database db;
	
	public UserDAO(Database db) {
		this.db = db;
	}
	
	public User readUser(int userId) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			String getUserSQL = "SELECT * FROM Users where userId = ?";
			ps = db.getConnection().prepareStatement(getUserSQL);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setUserName(rs.getString("userName"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setCurrentBatch(rs.getInt("currentBatch"));
				user.setIndexedRecords(rs.getInt("indexedRecords"));
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
		return user;
	}

	public User readUserWithName(String userName) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			String getUserSQL = "SELECT * FROM Users where userName = ?";
			ps = db.getConnection().prepareStatement(getUserSQL);
			ps.setString(1, userName);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setCurrentBatch(rs.getInt("currentBatch"));
				user.setIndexedRecords(rs.getInt("indexedRecords"));
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
		return user;
	}

	public List<User> readUsers() throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();
		User user;
		try {
			String getUsersSQL = "SELECT * FROM Users";
			ps = db.getConnection().prepareStatement(getUsersSQL);
			rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUserId(rs.getInt("userId"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setCurrentBatch(rs.getInt("currentBatch"));
				user.setIndexedRecords(rs.getInt("indexedRecords"));
				users.add(user);
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
		return users;
	}

	public void updateUser(User user) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String updateUserSQL = "UPDATE Users SET firstName = ?, lastName = ?, password = ?, email = ?, "
					+ "currentBatch = ?, indexedRecords = ? WHERE userId = ?";
			ps = db.getConnection().prepareStatement(updateUserSQL);
			ps.setString(1, user.getFirstName());
			ps.setString(2,  user.getLastName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setInt(5, user.getCurrentBatch());
			ps.setInt(6, user.getIndexedRecords());
			ps.setInt(7, user.getUserId());
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not update user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not update user", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}
	
	public void createUser(User user) throws DatabaseException {
		PreparedStatement ps = null;
		ResultSet keyRS = null;		
		try {
			String createUserSQL = "INSERT INTO Users (firstName, lastName, password, email, currentBatch, indexedRecords) "
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			ps = db.getConnection().prepareStatement(createUserSQL);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getEmail());
			ps.setInt(5, user.getCurrentBatch());
			ps.setInt(6, user.getIndexedRecords());
			if (ps.executeUpdate() == 1) {
				Statement keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("SELECT last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				user.setUserId(id);
			}
			else {
				throw new DatabaseException("Could not insert user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not insert user", e);
		}
		finally {
			Database.safeClose(ps);
			Database.safeClose(keyRS);
		}
	}
	
	public void deleteUser(int userId) throws DatabaseException {
		PreparedStatement ps = null;
		try {
			String deleteUserSQL = "DELETE FROM Users WHERE userId = ?";
			ps = db.getConnection().prepareStatement(deleteUserSQL);
			ps.setInt(1, userId);
			if (ps.executeUpdate() != 1) {
				throw new DatabaseException("Could not delete user");
			}
		}
		catch (SQLException e) {
			throw new DatabaseException("Could not delete user", e);
		}
		finally {
			Database.safeClose(ps);
		}
	}

}
