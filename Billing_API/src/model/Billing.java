package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Billing {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/billingapi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertBilling(String userID,String billingName, String billingDate, String billingUnits, String billingTotal, String billingID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into bill(`billingID`,`userID`,`billingName`,`billingDate`,`billingUnits`,`billingTotal`)"
					+ " values (?,?,?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(4, userID);
			preparedStmt.setString(2, billingName);
			preparedStmt.setString(3, billingDate);
			preparedStmt.setString(5, billingUnits);
			preparedStmt.setString(6, billingTotal);
			

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String readBilling() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\'1\'><tr><th>User ID</th><th>User Name</th><th>Billing Date</th><th>Billing Units </th><th>Total Price</th><th>Update</th><th>Delete</th></tr>";
			String query = "select * from bill";

			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("billingID"));
				String userID = rs.getString("userID");
				String user_name = rs.getString("billingName");
				String date = rs.getString("billingDate");
				String units = rs.getString("billingUnits");
				String total = rs.getString("billingTotal");
				

				// Add into the html table
				output += "<tr><td><input id=\'hidProjectIDUpdate\' name=\'hidProjectIDUpdate\' type=\'hidden\' value=\'"
						+ id + "'>"+userID+"</td>";
				output += "<td>" + user_name + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + total + "</td>";
				

				
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-billid='"
						+ id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updateBilling(String userID,String billingName, String billingDate, String billingUnits, String billingTotal, String billingID, String string) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE bill SET userID=?, billingName=?,billingDate=?,billingUnits=?,billingTotal=? WHERE billingID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values

			preparedStmt.setString(1, userID);
			preparedStmt.setString(2, billingName);
			preparedStmt.setString(3, billingDate);
			preparedStmt.setString(4, billingUnits);
			preparedStmt.setString(5, billingTotal);
			preparedStmt.setInt(6, Integer.parseInt(billingID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String deleteBilling(String billingID) {

		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from bill where billingID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(billingID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newProject = readBilling();
			output = "{\"status\":\"success\", \"data\": \"" + newProject + "\"}";
		} catch (Exception e) {
			output = "Error while deleting the project.";
			System.err.println(e.getMessage());
		}

		return output;
	}

}
