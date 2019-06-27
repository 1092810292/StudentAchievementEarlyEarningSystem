import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    public void SendEmailAutomatically(Map studentInformationMap, Map teacherInformationMap, Map failingCourseMap, boolean student) throws MessagingException, UnsupportedEncodingException {
        // 创建邮件的发送过程中用到的主机和端口号的属性文件
        String text="";
        String title="";
        String studentName=String.valueOf(studentInformationMap.get("姓名"));
        String teacherName=String.valueOf(teacherInformationMap.get("班主任姓名"));
        String email="";
        String warningLevel=String.valueOf(failingCourseMap.get("预警等级"));
        if (student){
            title="警告！"+studentName+"同学你可能无法正常毕业！";
            text=studentName+"你好！根据学校教务处的成绩预警系统分析生成的预警信息表来看，" +
                    "你的预警等级已经达到"+warningLevel+"，现发此电子邮件提醒你注意，希望你能对今后的学习认真对待。";
            email=String.valueOf(studentInformationMap.get("电子邮件"));
        }else{
            title="警告！你的学生"+studentName+"你可能无法正常毕业！";
            text=teacherName+"你好！根据学校教务处的成绩预警系统分析生成的预警信息表来看，你的学生"+studentName+"的预警等级已经达到"+warningLevel+"，现发此电子邮件提醒你注意，希望你能其对今后的学习进行监督。";
            if(warningLevel.equals("红色")){
                text=text+"请通知其家长。";
            }
            email=String.valueOf(teacherInformationMap.get("班主任电子邮件"));
        }
        Properties pro = new Properties();
        // 设置邮件发送方的主机地址如果是163邮箱，则为smtp.163.com
        // 如果是其他的邮箱可以参照http://wenku.baidu.com/link?url=Cf-1ggeW3e7Rm9KWfz47UL7vvkRpPxAKBlYoTSGpnK4hxpJDiQ0A4lRoPDncMlcMIvUpEn6PD0aObgm5zJaM7AOGkRdccSx6HDH2fSWkxIq这个文档
        pro.put("mail.smtp.host", "smtp.163.com");
        // 设置发送邮件端口号
        pro.put("mail.smtp.port", "25");
        // 设置邮件发送需要认证
        pro.put("mail.smtp.auth", "true");
        // 创建邮件验证信息，即发送邮件的用户名和密码
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 重写验证方法，填写用户名，密码
                return new PasswordAuthentication("13558169918@163.com", "wang172839456");// 网易的这是授权码！！
            }
        };
        // 根据邮件会话 构建一个邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        // 创建一个邮件消息
        Message message = new MimeMessage(sendMailSession);
        // 创建邮件发送者地址
        Address sourceAddress = new InternetAddress("13558169918@163.com");
        // 将原地址设置到消息的信息中
        message.setFrom(sourceAddress);
        // 创建邮件的接收者地址
        Address destAddress = new InternetAddress(email);//收件箱找不到去垃圾箱找！！竟然直接去了垃圾箱！
        // 将接收者的地址设置到消息的信息中
        message.setRecipient(Message.RecipientType.TO, destAddress);
        // 设置邮件的主题
        message.setSubject(title);
        // 设置邮件的发送内容
        Multipart multipart = new MimeMultipart();// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件         
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setText(text);// 设置邮件的文本内容
        multipart.addBodyPart(contentPart);

        BodyPart messageBodyPart = new MimeBodyPart();// 添加附件
        Date date=new Date();//此时date为当前的时间
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYMMdd");//设置当前时间的格式，为年-月-日
        String No=dateFormat.format(date)+studentInformationMap.get("学号");
        String fixationName=studentInformationMap.get("姓名")+"学生预警信息表";


         DataSource source = new FileDataSource("C:\\Users\\10928\\Desktop\\软件工程课程设计\\成绩预警系统\\"+fixationName+No+".pdf");

        messageBodyPart.setDataHandler(new DataHandler(source));// 添加附件的内容
        messageBodyPart.setFileName(MimeUtility.encodeText(studentName+"学生预警信息表.pdf"));
         multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);//将multipart对象放到message中            
         message.saveChanges();// 保存邮件            
        Transport transport = sendMailSession.getTransport("smtp");// 发送邮件            
         transport.connect("smtp.163.com", "13558169918@163.com", "wang172839456");// 连接服务器的邮箱            
         transport.sendMessage(message, message.getAllRecipients());// 把邮件发送出去
         transport.close();
    }
}
