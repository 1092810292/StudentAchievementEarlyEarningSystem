import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SubjectAttribute {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String studentID="";
    String subjectAttribute="";
//    String subjectAttributeNum="0";
    String subjectName="";
    int subjectNo=0;
    Map subjectAttributeMap=new HashMap();

    public SubjectAttribute(String studentID) {
        this.studentID = studentID;
    }

    public Map subjectAttributeQuery(){
        for(subjectNo=1;subjectNo<11;subjectNo=subjectNo+1){
            try {
                String sql = "select 科目"+subjectNo+",科目属性"+subjectNo+" from 学生信息与成绩表 where 学号 like '%"+studentID+"%'";
                preparedStatement = Main.connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()) {
                    subjectName=resultSet.getString("科目"+subjectNo);
                    subjectAttribute=resultSet.getString("科目属性"+subjectNo);
//                    if(subjectAttribute.trim().equals("专业课")){
//                        subjectAttributeNum="1";
//                    }
                    if(!subjectName.equals("")) {
                        subjectAttributeMap.put(subjectName.trim(),subjectAttribute.trim());
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("失败");
            }
        }
        return subjectAttributeMap;
    }
}
