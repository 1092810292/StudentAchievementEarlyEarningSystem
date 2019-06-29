import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**该类用于从教务系统数据库中使用SQL语言搜索学生基本信息
 * @return 返回类型为Map
 */

public class StudentInformation {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String name="";
    String studentID="";
    String school="";
    String major="";
    String majorAttribute="";
    String father="";
    String mather="";
    String email="";
    String teacherID="";
    String teacherName="";
    Map studentInformationMap=new HashMap<>();

    public StudentInformation(String studentID) {
        this.studentID = studentID;
    }

    public Map studentInformationquery(){
        try {
            //搜索学生信息
            String sql = "select * from 学生信息与成绩表 where 学号 like '%"+studentID+"%'";
            preparedStatement = Main.connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                name = resultSet.getString("姓名");
                studentID = resultSet.getString("学号");
                school = resultSet.getString("学院");
                major = resultSet.getString("专业");
                majorAttribute = resultSet.getString("专业属性");
                father = resultSet.getString("父亲姓名与联系方式");
                mather = resultSet.getString("母亲姓名与联系方式");
                email = resultSet.getString("电子邮件");
                teacherID = resultSet.getString("班主任编号");
            }
                //搜索班主任编号对应的班主任名字
                sql = "select * from 班主任 where 班主任编号 like '%"+teacherID+"%'";
                preparedStatement = Main.connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                teacherName = resultSet.getString("班主任姓名");
            }
            if(!name.equals("")) {
                studentInformationMap.put("姓名", name.trim());
                studentInformationMap.put("学号", studentID.trim());
                studentInformationMap.put("学院", school.trim());
                studentInformationMap.put("专业", major.trim());
                studentInformationMap.put("专业属性", majorAttribute.trim());
                studentInformationMap.put("父亲姓名与联系方式", father.trim());
                studentInformationMap.put("母亲姓名与联系方式", mather.trim());
                studentInformationMap.put("电子邮件", email.trim());
                studentInformationMap.put("班主任编号", teacherID.trim());
                studentInformationMap.put("班主任姓名", teacherName.trim());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("失败");
        }
        return studentInformationMap;
    }
}
