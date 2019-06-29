/**用于连接数据库，数据库管理系统使用SQL Server，用户名为sa，密码为123456，连接端口为1433，连接数据库名为教务系统
 * @author 王陆斌
 * @since JDK1.8
 */
import java.sql.*;

public class LinkSQL {
    private Connection connection = null;
    private String user="sa";
    private String pass="123456";
    private String url;

    LinkSQL() {
        url = "jdbc:sqlserver://localhost:1433;databaseName=教务系统";//端口：localhost，访问的数据库名称：databaseName
    }
    LinkSQL(String databaseName) {
        url = "jdbc:sqlserver://localhost:1433;databaseName=" + databaseName;//端口：localhost，访问的数据库名称：databaseName
    }
    LinkSQL(String databaseName,String user,String pass) {
        url = "jdbc:sqlserver://localhost:1433;databaseName=" + databaseName;//端口：localhost，访问的数据库名称：databaseName
        this.user=user;
        this.pass=pass;
    }

    /**
     * 连接数据库的方法
     * @return 返回类型为connection
     */
    public Connection link(){
        try {
            //1.加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("加载驱动成功！");
        } catch (Exception e) {
            System.out.println("加载驱动失败！");
            e.printStackTrace();
        }
        try {
            //2.连接
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("连接数据库成功！");
        } catch (Exception e) {
            System.out.println("连接数据库失败！");
            e.printStackTrace();
        }
        return connection;
    }
}
