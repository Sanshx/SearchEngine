package javaClasses;
import java.sql.*;
public class Validate {
	public static boolean checkUser(String username,String password) 
    {
     boolean st =false;
     try{

	    Connection con= new Database().returnConnection();
        PreparedStatement ps =con.prepareStatement("select * from maps_admin where username=? and password=?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs =ps.executeQuery();
        st = rs.next();
       
     }catch(Exception e)
     {
         e.printStackTrace();
     }
        return st;                 
 }   

}
