import javax.mail.MessagingException;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static Connection connection = null;
    public static void main(String[] args) throws SQLException {
        LinkSQL linkSQL = new LinkSQL("教务系统");
        connection = linkSQL.link();
        String studentID = "";
        String teacherID = null;
        for(int num=1;num<100;num=num+1) {
            studentID = "1111111111";

            System.out.println("");

            if (num < 10) {
                studentID = studentID + "0" + num;
            } else if (num >= 10) {
                studentID = studentID + num;
            }

            //输出学生信息表
            StudentInformation studentInformation = new StudentInformation(studentID);
            Map studentInformationMap = studentInformation.studentInformationquery();
            if (!studentInformationMap.isEmpty()) {
                System.out.println(studentInformationMap);

                //输出班主任信息表
                teacherID = studentInformationMap.get("班主任编号").toString();
                Map teacherInformationMap=null;
                if(!teacherID.equals("")) {
                    TeacherInformation teacherInformation = new TeacherInformation(teacherID);
                    teacherInformationMap = teacherInformation.teacherInformationQuery();
                    if (!teacherInformationMap.isEmpty()) {
                        System.out.println(teacherInformationMap);
                    }
                }

                //输出科目名与成绩
                SubjectScore subjectScore = new SubjectScore(studentID);
                Map subjectMap = subjectScore.subjectScoreQuery();
                if (!subjectMap.isEmpty()) {
                    System.out.println(subjectMap);
                }

                //输出课程名与课程属性
                SubjectAttribute subjectAttribute = new SubjectAttribute(studentID);
                Map subjectAttributeMap = subjectAttribute.subjectAttributeQuery();
                if (!subjectAttributeMap.isEmpty()) {
                    System.out.println(subjectAttributeMap);
                }

                //输出不及格科目数，绩点，不及格科目列表
                String majorAttribute=String.valueOf(studentInformationMap.get("专业属性"));
                    UpdateWarningInformation updateWarningInformation = new UpdateWarningInformation();
                    Map failingCourseMap= updateWarningInformation.classificationWarningLevels(subjectMap, subjectAttributeMap,majorAttribute);
                    System.out.println(failingCourseMap);

                    //生成PDF文档
                if(!String.valueOf(failingCourseMap.get("预警等级")).equals("通过")) {
                    PDF pdf = new PDF();
                    pdf.CreatePDF(studentInformationMap, failingCourseMap);

                    WriteInSQLServer writeInSQLServer=new WriteInSQLServer();
                    boolean result=writeInSQLServer.write(studentInformationMap,failingCourseMap);
                    if(result) {
                        System.out.println("预警数绩插入预警数据库成功！");
                    }

                Email email = new Email();
                    try {
                        email.SendEmailAutomatically(studentInformationMap, teacherInformationMap, failingCourseMap, true);
                        email.SendEmailAutomatically(studentInformationMap, teacherInformationMap, failingCourseMap, false);
                        System.out.println("发送成功");
                    } catch (MessagingException e) {
                        System.out.println("发送失败");
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
