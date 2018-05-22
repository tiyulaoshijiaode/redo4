package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class JdbcTemplate {

	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	static{
		try {
			Properties properties = new Properties();
			properties.load(JdbcTemplate.class.getResourceAsStream("jdbc_properties.properties"));
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			username = properties.getProperty("jdbc.username");
			password = properties.getProperty("jdbc.password");
			System.out.println("初始化数据库连接信息完成");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("初始化数据库连接信息失败");
		}
		
		try {
			//加载驱动
			Class.forName(driver);
			System.out.println("加载驱动成功");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println("加载驱动失败");
		}
		
	}
	
	public static Connection get(){
		//连接数据库
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,username,password);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("数据库连接失败");
		}
		return connection;
	}
	
	public static List<Map<String, Object>> query(String sql,Object...args){
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//获取连接
		connection = get();
		try {
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0;i < args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Map<String, Object> map = new HashMap<String,Object>();
				ResultSetMetaData result = resultSet.getMetaData();
				int count = result.getColumnCount();
				for(int i = 0;i < count;i++){
					String columnName = result.getColumnName(i+1);
					Object columnValue = resultSet.getObject(columnName);
					map.put(columnName, columnValue);
				}
				resultMap.add(map);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			relase(connection, preparedStatement, resultSet);
		}
		
		return resultMap;
	}
	
	/**
	 * 添加,修改,删除
	 * @param sql
	 * @param args
	 * @return
	 */
	public static int execute(String sql,Object...args){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int modify = 0;
		try {
			//连接数据库
			connection = get();
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 0; i < args.length; i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			modify = preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			relase(connection, preparedStatement, null);
		}
		return modify;
	}
	
	public static void relase(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
		try{
			if(connection != null){
				connection.close();
			}else if(preparedStatement != null){
				preparedStatement.close();
			}else if(resultSet != null){
				resultSet.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
