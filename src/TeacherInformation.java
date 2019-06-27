import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherInformation {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String name="";
    String teacherID="";
    String email="";
    Map teacherInformationMap=new HashMap();
    String sql="";
    public TeacherInformation(String teacherID) {
        this.teacherID = teacherID;
    }

    public Map teacherInformationQuery(){
        try {
                sql = "select * from 班主任 where 班主任编号 like '%" + teacherID + "%'";
                preparedStatement = Main.connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                teacherID=resultSet.getString("班主任编号");
                name=resultSet.getString("班主任姓名");
                email=resultSet.getString("班主任电子邮件");
            }
                teacherInformationMap.put("班主任编号",teacherID.trim());
                teacherInformationMap.put("班主任姓名",name.trim());
                teacherInformationMap.put("班主任电子邮件",email.trim());
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("失败");
        }
        return teacherInformationMap;
    }

}
