/**
 * 
 */
package server;

import java.sql.*;
import java.util.*;

import common.*;

/**
 * 
 *
 */
public class DataBase {
	
	 private static String get_defaults()
     {
         return "jdbc:mysql://localhost/test";
     }
     
     public static Connection get_connection()
     {
         String connection_string = get_defaults();
         try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Connection conn = DriverManager.getConnection(connection_string,"root","1234");
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
     }
     
	/**
	 * Update current HP of monster after a fight
	 */
	public static SQLOutput UpdateMosterHP (int ID, int damage)
	{
		try{
			SQLOutput flag = SQLOutput.EXISTS;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Update_Monster_HP(?,?,?)}");
			prc.setInt(1,ID);
			prc.setInt(2, damage);
			prc.registerOutParameter(3, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(3);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			con.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
	}
	
	public static SQLOutput IsMonsterDead(int ID)
	{
		try{
			SQLOutput flag = SQLOutput.EXISTS;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Is_Monster_Dead(?,?)}");
			prc.setInt(1,ID);
			prc.registerOutParameter(2, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(2);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			else if(result == 1)
				flag = SQLOutput.YES;
			else
				flag = SQLOutput.NO;
			con.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
	}
	
	public static SQLOutput RemoveMonster(int ID)
	{
		try{
			SQLOutput flag = SQLOutput.EXISTS;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Remove_Monster(?,?)}");
			prc.setInt(1,ID);
			prc.registerOutParameter(2, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(2);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			else
				flag = SQLOutput.EXISTS;
			con.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
	}
	
	public static SQLOutput AddMonster(Monster mnst)
	{
		try{
			SQLOutput flag = SQLOutput.EXISTS;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Add_Monster(?,?,?,?,?,?,?,?)}");
			prc.setInt(1,mnst.getID());
			prc.setInt(2,mnst.getType());
			prc.setInt(3,mnst.getMaxHP());
			prc.setInt(4,mnst.getX());
			prc.setInt(5,mnst.getY());
			prc.setInt(6,mnst.getCurrentHP());
			prc.setInt(7,mnst.getHunger());
			prc.registerOutParameter(8, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(8);
			if(result == 0)
				flag =  SQLOutput.EXISTS;
			else
				flag = SQLOutput.OK;
			con.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
	}
	
	public static Monster GetMonster(int x, int y)
	{
		try{
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{Get_Monster(?,?,?,?,?,?,?)}");
			prc.registerOutParameter(1, Types.INTEGER);
			prc.registerOutParameter(2, Types.INTEGER);
			prc.registerOutParameter(3, Types.INTEGER);
			prc.setInt(4,x);
			prc.setInt(5,y);
			prc.registerOutParameter(6, Types.INTEGER);
			prc.registerOutParameter(7, Types.INTEGER);
			prc.registerOutParameter(8, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(8);
			if(result == 0)
			{
				con.close();
				return null;
			}
			else
			{
				Monster mnst = new Monster(prc.getInt(1),prc.getInt(2),prc.getInt(3),x,y,prc.getInt(6),prc.getInt(7));
				con.close();
				return mnst;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Coordinate GetMonsterCoordinate(int ID)
	{
		try{
			Coordinate crdnt = null;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Get_Monster_Coordinate(?,?,?,?)}");
			prc.setInt(1,ID);
			prc.registerOutParameter(2, Types.INTEGER);
			prc.registerOutParameter(3, Types.INTEGER);
			prc.execute();
			int X = prc.getInt(2);
			int Y = prc.getInt(3);
			int Result = prc.getInt(4);
			if(Result == 0)
				return null;
			else
				crdnt = new Coordinate(X,Y);
			con.close();
			return crdnt;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Equipment> GetEquipment(int ID)
	{
		List<Equipment> Eqp = new ArrayList<Equipment>();
		try{
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Get_Equipment(?,?)}");
			prc.setInt(1,ID);
			prc.registerOutParameter(2, Types.INTEGER);
			prc.execute();
			int Result = prc.getInt(4);
			ResultSet Res = prc.getResultSet();
			if(Result == 0)
				return Eqp;
			else
				{
					while(Res.next())
					{
						Eqp.add(new Equipment(Res.getInt(1),Res.getString(2),Res.getString(3)));
					}
					Res.close();
				}
			con.close();
			return Eqp;
		} catch (SQLException e) {
			e.printStackTrace();
			return Eqp;
		}
	}
	
	public static SQLOutput AddEqipment(int ID, Equipment eq)
	{
		try{
			SQLOutput flag = SQLOutput.OK;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Add_Equipment(?,?,?)}");
			prc.setInt(1,ID);
			prc.setInt(2, eq.getID());
			prc.registerOutParameter(3, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(3);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			else if(result == 1)
				flag = SQLOutput.EXISTS;
			con.close();
			return flag;
		} catch (SQLException e) {
			e.printStackTrace();
			return SQLOutput.SQL_ERROR;
		}
	}
	
	public static SQLOutput RemoveEquipment(int ID, Equipment eq)
	{
		try{
			SQLOutput flag = SQLOutput.OK;
			Connection con = get_connection();
			CallableStatement prc = con.prepareCall("{call Remove_Equipment(?,?,?)}");
			prc.setInt(1,ID);
			prc.setInt(2, eq.getID());
			prc.registerOutParameter(3, Types.INTEGER);
			prc.execute();
			int result = prc.getInt(3);
			if(result == 0)
				flag =  SQLOutput.NOT_FOUND;
			else if(result == 1)
				flag = SQLOutput.OK;
			con.close();
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
	
//public static void main(String[] args) {
//		
//		SQLOutput r = UpdateMosterHP(1,1);
//		System.out.println(r);
//	}
}
