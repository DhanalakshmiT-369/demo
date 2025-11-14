package optimization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
		
		public static Connection getDBconnection() {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/trains";
			String username = "root";
			String password = "root";
			Connection con = null;
			
			try {
				Class.forName(driver);
				con=DriverManager.getConnection(url,username,password);
			}
			catch (ClassNotFoundException ex) {
					ex.printStackTrace();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			finally {
				
			}
			return con;
		}

}



