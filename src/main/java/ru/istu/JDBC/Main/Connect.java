package ru.istu.JDBC.Main;


import java.sql.*;


public class Connect {

    public static void executeData(String method, String nameMethod) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(method);

        } catch (NullPointerException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }

    }

    public static void selectData(String method, String nameMethod) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(method);
            System.out.println("Method " + nameMethod + " completed successfully!");
            while (rs.next()) {
                String userid = rs.getString("id");
                String username = rs.getString("User name");
                String length = rs.getString("length(`Text of the mail`)");
                System.out.print("userid : " + userid + "  ");
                System.out.print("username : " + username);
                System.out.println(" length : " + length);
            }

        } catch (NullPointerException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static void selectData(String method, String method1, String method2, String nameMethod) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            statement.execute(method);
            statement.execute(method1);
            ResultSet rs = statement.executeQuery(method2);
            while (rs.next()) {
                String userid = rs.getString("id");
                String sendersId = rs.getString("Senders id");
                String recipientsId = rs.getString("Recipients id");
                System.out.print("Id пользователя: " + userid + "  ");
                System.out.print("Отправленно: " + sendersId);
                System.out.println(" Принято: " + recipientsId);

            }
            statement.execute("Drop table dd");
            statement.execute("DROP TABLE df");
            System.out.println("Methods " + nameMethod + " completed successfully!");
        } catch (NullPointerException ex) {
            System.out.println("Error:" + ex.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }


    public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return dbConnection;
    }

    public static void createUsersTables() throws SQLException {
        String createUsersTableSQL = "create table users\n" +
                "(\n" +
                "\tid int(5)  auto_increment,\n" +
                "\t`User name` nvarchar(70) not null,\n" +
                "\t`Date of born` DATETIME not null,\n" +
                "\t\tprimary key (id)\n" +
                ")";
        executeData(createUsersTableSQL, "createUsersTableSQL");
    }

    public static void createMailsTables() throws SQLException {
        String createMailsTableSQL = "create table mails\n" +
                "                (\n" +
                "                    id int(5) auto_increment\n" +
                "                        primary key,\n" +
                "                    `Senders id`       int(5)                    not null,\n" +
                "                    `Recipients id`    int(5)                    null,\n" +
                "                    `Theme mail`       varchar(50) charset utf8  not null,\n" +
                "                    `Text of the mail` varchar(100) charset utf8 null,\n" +
                "                    `Date of send`     datetime                  not null,\n" +
                "                constraint mails_ibfk_1\n" +
                "                foreign key (`Senders id`) references users (id),\n" +
                "                constraint mails_ibfk_2\n" +
                "                foreign key (`Recipients id`) references users (id)\n" +
                "                )";
        executeData(createMailsTableSQL, "createMailsTableSQL");
        String createIndexSQL = "create index `Senders id`\n" +
                "    on mails (`Senders id`)";
        executeData(createIndexSQL, "createIndexSQL");
        String createIndexSQL1 = "create index `mails_Recipients id_index`\n" +
                "    on mails (`Recipients id`)";
        executeData(createIndexSQL1, "createIndexSQL");
    }


    public static void insertUsersTable(String name, int yearRnd, int monthRnd, int dayRnd) throws SQLException {
        String insertTableSQL = "insert into users (`User name`, `Date of born`) " +
                "values ('" + name + "',NOW()- interval " + yearRnd + " year - interval " + monthRnd + " month - interval " + dayRnd + " day)";

        executeData(insertTableSQL, "createTableSQL");
    }

    public static void insertMailTable(int rndValues, int rndValues1) throws SQLException {
        String insertTableSQL = "insert into mails (`Senders id`, `Recipients id`, `Theme mail`, `Text of the mail`, `Date of send`)" +
                "VALUES ((select users.id from users where users.id =" + rndValues + ")," +
                "(select users.id from users where users.id =" + rndValues1 + "),'Text about text'," +
                " 'Я тут был ム" + rndValues * rndValues1 + "',NOW()-interval RAND()*12+1 year -interval RAND()*13+1 month" +
                "-interval RAND()*32+1 day )";

        executeData(insertTableSQL, "createTableSQL");
    }

}
