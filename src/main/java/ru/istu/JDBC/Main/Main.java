package ru.istu.JDBC.Main;


import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            Connect.createUsersTables();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            Connect.createMailsTables();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        for (int i=0;i<30;i++) {
            try {
                Connect.insertUsersTable("Zaharov Rorik Vladimirovich ver:" + Math.random()*10+1,
                        (int) (Math.random() * 30 + 1), (int) (Math.random() * 13 + 1), (int) (Math.random() * 32 + 1));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Method insertUsersTable completed successfully!");

        for (int i=0;i<40;i++) {
            try {
                Connect.insertMailTable((int) (Math.random()*30+1),(int) (Math.random()*30+1));
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Method insertMailTable completed successfully!");
        //реализация 2-х запросов к бд


        try {
            querySelect.selectMinTablesql();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            querySelect.selectCountMailsTablesql();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
