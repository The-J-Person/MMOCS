/**
 * 
 */
package server;

import java.sql.*;
import java.util.*;

import common.*;
import common.SQLOutput;

/**
 * 
 *
 */
public class DataBase {
	
	 private static String get_defaults()
     {
         return "jdbc:mysql://localhost/test?";
     }
     
     public static Connection get_connection()
     {
         String connection_string = get_defaults();
         Connection conn;
		try {
			conn = DriverManager.getConnection(connection_string);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
     }
     
	/**
	 * Update current HP of monster after a fight
	 * return 1 if monster is still alive, 0 if dead
	 */
	public static SQLOutput UpdateMosterHP (int ID, int damage)
	{
		SQLOutput flag = SQLOutput.EXISTS;
		Connection con = get_connection();
		try {
			CallableStatement prc = con.prepareCall("{call Update_Monster_HP(?,?,?)}");
			prc.setInt(1,ID);
			prc.setInt(2, damage);
			prc.registerOutParameter(3, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(3);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
		
	}
	
	public static List<FloorType> get_possible_neighbors(Tile t)
	{
		//TODO Stub for map generation
		List<FloorType> lst = new ArrayList<FloorType>();
		lst.add(FloorType.GRASS);
		lst.add(FloorType.DIRT);
		lst.add(FloorType.STONE);
		return lst;
	}
	
	public static List<MapObjectType> get_possible_content(Tile t)
	{
		//TODO Stub for map generation
		List<MapObjectType> lst = new ArrayList<MapObjectType>();
		lst.add(MapObjectType.BUSH);
		lst.add(MapObjectType.TREE);
		lst.add(MapObjectType.ROCK);
		lst.add(null);
		return lst;
	}
}
