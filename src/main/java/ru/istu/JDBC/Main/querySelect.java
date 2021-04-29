package ru.istu.JDBC.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class querySelect {
    public static void selectMinTablesql() throws SQLException {
        String selectTableSql = "SELECT users.id,`User name`,length(`Text of the mail`)" +
                " from users left join mails m on users.id = m.`Senders id`" +
                "where length(`Text of the mail`) = (select min(length(`Text of the mail`))from mails)" +
                "order by id";
        Connect.selectData(selectTableSql, "FindMin");
    }

    public static void selectCountMailsTablesql() throws SQLException {
        String multiSelectTableSql ="CREATE table dd (select users.id, count(`Senders id`) as 'Senders id'\n" +
                "from users\n" +
                "         left join mails m on users.id = m.`Senders id`\n" +
                "GROUP BY users.id\n" +
                "order by users.id)";
        String multiSelectTableSql1="CREATE TABLE df (select users.id, count(`Recipients id`) as 'Recipients id'\n" +
                "from users\n" +
                "         left join mails m on users.id = `Recipients id`\n" +
                "GROUP BY users.id\n" +
                "order by users.id)";
        String multiSelctTableSql2 = "select t1.id,`Senders id`,`Recipients id`\n" +
                "from dd t1 left join df t2 on t1.id=t2.id";
        Connect.selectData(multiSelectTableSql, multiSelectTableSql1, multiSelctTableSql2, "selectUser");
    }


}
