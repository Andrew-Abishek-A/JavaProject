import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class StoreToDataBase {
	
	static void store(String data, String output) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
			String updateDB = "insert into javaproj values(?,?);";
			PreparedStatement ps = con.prepareStatement(updateDB);
			ps.setString(1, data);
			ps.setString(2, output);
			int i = ps.executeUpdate();
			System.out.println("Rows inserted: "+i);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
