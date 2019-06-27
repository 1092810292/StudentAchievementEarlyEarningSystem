import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class WriteInSQLServer {
    public boolean write(Map studentInformationMap, Map failingCourseMap) throws SQLException {
        LinkSQL linkSQL = new LinkSQL("预警信息数据库");
        Connection connection = linkSQL.link();
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean result=false;
        String sql ="";
        String studentID=String.valueOf(studentInformationMap.get("学号"));
        String name=String.valueOf(studentInformationMap.get("姓名"));
        String major=String.valueOf(studentInformationMap.get("专业"));
        float GPA=Float.valueOf(String.valueOf(failingCourseMap.get("绩点")));
        String warningLevel=String.valueOf(failingCourseMap.get("预警等级"));
        ArrayList<String> failingCourseList=new ArrayList();
        for(int count=0;count<failingCourseMap.size()-3;count=count+1){
            failingCourseList.add(String.valueOf(failingCourseMap.get("不及格科目"+(count+1))));
        }

        try {
            sql="insert into 预警信息表(学号,姓名,专业,绩点,预警等级";
            for(int count=0;count<failingCourseList.size();count=count+1){
                sql=sql+",不及格科目"+(count+1);
            }
            sql =sql+ ") VALUES ('"+studentID+"','"+name+"','"+major+"','"+GPA+"','"+warningLevel;
            for(int count=0;count<failingCourseList.size();count=count+1){
                sql=sql+"','"+String.valueOf(failingCourseList.get(count));
            }
            sql=sql+"')";
            preparedStatement = connection.prepareStatement(sql);
            int rs = statement.executeUpdate(sql);
            if(rs>0) {
                result=true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("插入失败");
        }
        connection.close();
        statement.close();
        preparedStatement.close();
        return result;
    }
}
