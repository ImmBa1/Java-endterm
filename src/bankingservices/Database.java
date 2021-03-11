package bankingservices;

import java.sql.DriverManager;

public class Database{
    private static final String Connection = "jdbc:postgresql://localhost:5432/ATM";
    private static final String user = "postgres";
    private static final String password = "12345";

    public static java.sql.Connection connection() throws Exception{//this connection method is used in other classes, so its possible to connect to database from any point of app
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(Connection,user,password);
    }
}
