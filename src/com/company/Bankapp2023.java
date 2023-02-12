package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bankapp2023 {


    static Connection con = null;

    public  void connect () {


         String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
          String dbur1 = "jdbc:mysql://localhost:3306/test";
          String dbuser = "root";
          String dbpass = "";

        try {
            Class.forName(JDBC_Driver);
            con = DriverManager.getConnection(dbur1,dbuser,dbpass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void withdraw(int accountNumber, double amount) {

        String query = " UPDATE mbankaccounts SET account_balance =? WHERE=?";

        try {
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, accountNumber);
            pst.setDouble(2, amount);

            pst.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }




    public   void checkAccount (int inputAccount) {


        String query = "SELECT account_no FROM mbankaccounts";

        ArrayList<Integer> myList = new ArrayList<Integer>();

        try {
            Statement st =   con.createStatement();

            ResultSet rs =  st.executeQuery(query);

            while(rs.next()) {
                myList.add(rs.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for ( int i =0; i < myList.size(); i++){
            if (inputAccount == myList.get(i)) {
                //  System.out.println("Account Found :" + inputAccount);
                break;
            } else if ( myList.get(i) != inputAccount && i==myList.size() -1 ){
                System.out.println();
                throw new RuntimeException("Konto: " + inputAccount +  " nie znalezione");

            }
        }


    }

    public  double getBalance( int accoutNumber) {
        String query = "SELECT account_balance FROM mbankaccounts  WHERE account_no=" + accoutNumber;
        double balanceDouble = 0;
        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            rs.next();
            balanceDouble = rs.getDouble(1);


        } catch (SQLException throwables) {
            //  throwables.printStackTrace();

            // throw new RuntimeException("Error something went wrong");
        }


        return balanceDouble;

    }



    public static void main(String[] args) {


        Bankapp2023 bankapp20231 = new Bankapp2023();
        bankapp20231.connect();

        Transaction t1 = new Transaction();

        boolean runApp = true;
        boolean runApp1 = true;
        boolean runApp2 = true;
        boolean runApp3 = true;

        int inputAccount1 = 0;
        Double currentBalanceAccount1 = 0d;
        int inputAccount2 = 0;
        double currentBalanceAccount2 = 0d;


        double update1 = 0d;
        double update2 = 0d;

        Double amount = 0d;


        while (runApp) {

            System.out.println("Podaj numer konta z ktorego wykonac przelew");
            Scanner scan = new Scanner(System.in);
            inputAccount1 = scan.nextInt();

            currentBalanceAccount1 = bankapp20231.getBalance(inputAccount1);

            if (currentBalanceAccount1 > 0) {
                runApp = false;

            } else {
                System.out.println("Nie prawidlowe konto - wprowadz konto ponownie");
                System.out.println();
            }
        }

        while (runApp1) {

            System.out.println("Podaj numer konta na ktory wykonac przelew");
            Scanner scan1 = new Scanner(System.in);
            inputAccount2 = scan1.nextInt();


            currentBalanceAccount2 = bankapp20231.getBalance(inputAccount2);

            if (currentBalanceAccount2 > 0) {
                runApp1 = false;

            } else {
                System.out.println("Nie prawidlowe konto - wprowadz konto ponownie");
                System.out.println();
            }
        }
        while (runApp2) {

            System.out.println("Podaj kwote przelewu");
            Scanner scan2 = new Scanner(System.in);
            amount = scan2.nextDouble();

            update1 = currentBalanceAccount1 - amount;


         if (amount <= 0) {
                System.out.println("Kwota przelewu musi byc wieksza od zera");




            } else if (update1 < 0) {
                System.out.println("Nie masz wystarczajacych srodkow na dokonanie przelwu");
                System.out.println("Podaj kwote ponownie");
                System.out.println("");
            } else if (update1 > 0)
                runApp2 = false;
            update2 = currentBalanceAccount2 + amount;

        }
        String title = " ";
        while(runApp3) {
        System.out.println("Podaj tytul przelewu");

        Scanner scan = new Scanner(System.in);
          title = scan.nextLine();


            if (title.isEmpty()) {
                System.out.println("Tytul przelewu nie moze byc pusty");
            } else {
                runApp3 = false;
            }

        }

        PreparedStatement pst = null;
        PreparedStatement pst1 = null;

        try {


            con.setAutoCommit(false);



            String query1 = "UPDATE mbankaccounts SET account_balance =? WHERE account_no=?";
            String query2 = "UPDATE mbankaccounts SET account_balance = ? WHERE account_no=?";

            //     main1.checkAccount(inputAccount1);
            //    main1.checkAccount(inputAccount2);

            pst = con.prepareStatement(query1);
            pst1= con.prepareStatement(query2);

            pst.setDouble(1, update1);
            pst.setInt(2, inputAccount1);


            pst1.setDouble(1,update2);
            pst1.setInt(2,inputAccount2 );





            pst.executeUpdate();

            pst1.executeUpdate();

            con.commit();
            t1.connect();
            t1.saveTransaction(Transaction.generateRandomString(20), inputAccount1, inputAccount2, amount, title );
            System.out.println("Transaction performed successfully");
            System.out.println("Wykonano przelew z konta: " + inputAccount1 + " na konto: " + inputAccount2+ " w wysokosci: " + amount);
            System.out.println("Tytul przelewu: " + title);
            //System.out.println("Numer transakcji: " + Transaction.generateRandomString(20));

        } catch(SQLException e){
            System.out.println(e);

            if(con!= null){
                System.out.println("Transaction is being rollback");
                try {
                    con.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }}}}




