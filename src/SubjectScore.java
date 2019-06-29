import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SubjectScore {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String studentID="";
    String subjectName="";
    String score="";
    int subjectNo=0;
    Map subjectMap=new HashMap();

    public SubjectScore(String studentID) {
        this.studentID = studentID;
    }

    public Map subjectScoreQuery(){
        /**该类用于在教务系统数据库中根据学号搜索学生科目名与该科目成绩
         * @param学号
         * @return 学生科目与成绩表
         */
        for(subjectNo=1;subjectNo<11;subjectNo=subjectNo+1){
        try {
            String sql = "select 科目"+subjectNo+",成绩"+subjectNo+" from 学生信息与成绩表 where 学号 like '%"+studentID+"%'";
            preparedStatement = Main.connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();


            while(resultSet.next()) {
                subjectName=resultSet.getString("科目"+subjectNo);
                score=resultSet.getString("成绩"+subjectNo);
            }
            if(!subjectName.equals("")) {
                subjectMap.put(subjectName.trim(), score.trim());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("失败");
            }
        }
        return subjectMap;
    }
}
