package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class AccessingPortal {

    Connection con = null;

    public    void connect() {

    String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    String dbur1 = "jdbc:mysql://localhost:3306/test";
    String dbuser = "root";
    String dbpass = "";


        try {
            Class.forName(JDBC_Driver);
            con = DriverManager.getConnection(dbur1, dbuser,dbpass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public   boolean checkAccount (int inputCustomerNumber) {


        String query = "SELECT customerNumber FROM mbankcustomers";

        boolean corectCustomerNumber = true;

        ArrayList<Integer> myList = new ArrayList<Integer>();

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                myList.add(rs.getInt(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (int i = 0; i < myList.size(); i++) {
            if (inputCustomerNumber == myList.get(i)) {
                //  System.out.println("Account Found :" + inputAccount);
                corectCustomerNumber = true;
                break;

            } else if (myList.get(i) != inputCustomerNumber && i == myList.size() - 1) {
                corectCustomerNumber = false;

            }

        }
        return corectCustomerNumber;
    }

    public   Customer getCustomerDetails (int customerNumber ){

        String query = " SELECT *  FROM mbankcustomers WHERE customerNumber =" +customerNumber ;


        Customer customer1 = new Customer(customerNumber, 0, "", "", "", "",0, 0d);



        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            rs.next();

            customer1.setAccount_no(rs.getInt(2));
            customer1.setFirstName(rs.getString(3));
            customer1.setLastName(rs.getString(4));
            customer1.setPassword(rs.getString(5));
            customer1.setEmail(rs.getString(6));
            customer1.setPhoneNumber(rs.getInt(7));
            customer1.setBalance(rs.getDouble(8));




        } catch (SQLException throwables) {

        }
return customer1;

    }



    public   void launchingPortal ( ) {
        System.out.println("Witaj w portalu bankowosci elektronicznej");
        System.out.println("W celu rejestracji wybierz 1, w przypadku logowania do serwisu 2");

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 ->  System.out.println("Rejestracja do portalu");
            case 2 -> {
                for (int i =0; i < 3; i ++) {

                System.out.println("W celu logowania do portalu podaj numer klienta");

            Scanner scanner1 = new Scanner(System.in);
            int inputCustomerNumber = scanner1.nextInt();



                   String checkedPassword =  getCustomerDetails(inputCustomerNumber).getPassword();

                    System.out.println("Podaj haslo do logowania");

                    Scanner scanner2 = new Scanner(System.in);
                    String password = scanner2.nextLine();

             if (password.equals(checkedPassword ) && checkAccount(inputCustomerNumber) ) {
                 System.out.println("Logowanie przebieglo  poprawnie");
                 System.out.println();
                 Menu menu = new Menu();
                 menu.accessingMenu(inputCustomerNumber);
                 break;

             }  else  if ( i == 2) {
                 System.out.println("Numer klienta albo haslo nieprawidołowe");
                     System.out.println(" 3 proby nieprawdilowego logowania - konto zablokowane ");
             } else   {
                 System.out.println("Numer klienta albo haslo nieprawidolowe - sprobuj ponownie");
                 System.out.println();
             }



        }

    }
}}}
