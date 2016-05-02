package MyFirstServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewMemberSQL {
	private Connection connect = null;

	private PreparedStatement preparedStatement = null;

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date currentDate = new Date();

	public void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");

		connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarymanagement", "root", "sesame");
	}
	
	public void closeConnection(){
		try {
			if(connect != null){
				connect.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Members> readPatrons(String Phone) throws SQLException {
		ArrayList<Members> membersList = new ArrayList<Members>();
		try {
			getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM librarymanagement.members where Phone = ?");
		preparedStatement.setString(1, Phone);
		ResultSet results = preparedStatement.executeQuery();
		while(results.next()){
			String memberID = results.getString("MemberID");
			String memberName = results.getString("FirstName");
			String memberLast = results.getString("LastName");
			String memberPhone = results.getString("Phone");
			String memberAddress = results.getString("Address");
			String memberDate = results.getString("LastVisitDate");
			Members members = new Members(memberID, memberName, memberLast, memberPhone, memberAddress, memberDate);
			membersList.add(members);
			
		}
		closeConnection();
		return membersList;
	}

	public void addLibraryPatron(String firstName, String lastName, String Phone, String Address) throws SQLException {
		try {
			getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preparedStatement = connect.prepareStatement(
				"INSERT INTO librarymanagement.members (FirstName, LastName, Phone, Address, LastVisitDate) VALUES (?, ?, ?, ?, ?)");

		preparedStatement.setString(1, firstName);
		preparedStatement.setString(2, lastName);
		preparedStatement.setString(3, Phone);
		preparedStatement.setString(4, Address);
		preparedStatement.setString(5, dateFormat.format(currentDate));
		preparedStatement.executeUpdate();
		closeConnection();
	}

	public ArrayList<Members> readAllPatrons(String ID, String name, String last, String phone, String address, String date) throws SQLException {
		ArrayList<Members> allMembers = new ArrayList<Members>();
		try {
			getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		preparedStatement = connect.prepareStatement(
				"SELECT * FROM librarymanagement.members");
		ResultSet results = preparedStatement.executeQuery();
		while(results.next()){
			String memberID = results.getString("MemberID");
			String memberName = results.getString("FirstName");
			String memberLast = results.getString("LastName");
			String memberPhone = results.getString("Phone");
			String memberAddress = results.getString("Address");
			String memberDate = results.getString("LastVisitDate");
			Members patronListing = new Members(memberID, memberName, memberLast, memberPhone, memberAddress, memberDate);
			allMembers.add(patronListing);
			
		}
		closeConnection();
		return allMembers;
	}
}