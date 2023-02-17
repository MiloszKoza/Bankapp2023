package com.company;

import java.sql.*;

public class BankHistory {

    Connection con = null;

    public  void connect () {


        String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
        String dbur1 = "jdbc:mysql://localhost:3306/test";
        String dbuser = "root";
        String dbpass = "";

        try {
            Class.forName(JDBC_Driver);
            con = DriverManager.getConnection(dbur1, dbuser, dbpass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void showBankHistory(int accountNumber) {

        String query = "SELECT mbanktransactions.kwota,mbanktransactions.tytuł,mbanktransactions.Date, mbanktransactions.na_Rachunek, mbanktransactions.z_Rachunku, mbankcustomers.firstName,mbankcustomers.lastName, a1.firstName, a1.lastName  FROM mbanktransactions\n" +
                "\n" +
                "JOIN mbankcustomers on mbanktransactions.z_Rachunku = mbankcustomers.account_no\n" +
                "JOIN mbankcustomers as a1 on mbanktransactions.na_Rachunek = a1.account_no\n" +
                "WHERE(z_Rachunku=" + accountNumber + " OR " + " na_Rachunek= " + accountNumber+" ) ORDER BY Date DESC;";

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while(rs.next()) {
              int check =   rs.getInt(4);

              if(check == accountNumber) {
                  System.out.println("Data: " + rs.getString(3));
                  System.out.println("Nadawca: " + rs.getString(6) +" " + rs.getString(7));
                  System.out.println("Tytul: " + rs.getString(2) + " Kwota: " + rs.getInt(1) );

                  System.out.println();
              } else {
                  System.out.println("Data: " + rs.getString(3));
                  System.out.println("Odbiorca: " + rs.getString(8 ) +" "+ rs.getString(9));
                  System.out.println("Tytul: " + rs.getString(2) + " Kwota: -" + rs.getInt(1) );
                  System.out.println();
              }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

}
