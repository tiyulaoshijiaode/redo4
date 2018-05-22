package jdbc;

import java.util.List;
import java.util.Map;

public class JdbcTemplateTest {

	public static void main(String[] args) {
		
		String sql = "select * from user_account";
		String[] queryParams = new String[0];
		List<Map<String, Object>> listMap = JdbcTemplate.query(sql, queryParams);
		for(Map<String, Object> column : listMap){
			System.out.println(column);
		}
		
		//添加语句
/*		String insertSql = "insert into user_account values(1,?,?)";
		for(int i = 0;i < 100;i++){
			String[] addParams = new String[]{"ac" + i,"pw" + i};
			int addmodify = JdbcTemplate.execute(insertSql, addParams);
			System.out.println("add " + addmodify);
		}*/
		
		//修改语句
/*		String updateSql = "update user_account set password=? where aid=?";
		String[] updateParams = new String[]{"pw3","1"};
		int updatemodify = JdbcTemplate.execute(updateSql, updateParams);
		System.out.println("update " + updatemodify);*/
		
		//删除语句
/*		String deleteSql = "delete from user_account where aid=?";
		String[] deleteParams = new String[]{"1"};
		int deletemodify = JdbcTemplate.execute(deleteSql, deleteParams);
		System.out.println("delete " + deletemodify);*/
	}
	
}
