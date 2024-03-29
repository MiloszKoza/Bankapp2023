package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

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


    public void showBankHistory(int accountNumber, int customeNumber) {
          int transactionCounter =1;
        ArrayList<Transaction> myTransactionsList = new ArrayList<>();


        AccessingPortal as = new AccessingPortal ();
        as.connect();

        String  nameOfHoldr =  as.getCustomerDetails(customeNumber).getFirstName();
        String  lastNameOfHolder = as.getCustomerDetails(customeNumber).getLastName();





        String query = "SELECT mbanktransactions.kwota,mbanktransactions.tytuł,mbanktransactions.Date, mbanktransactions.na_Rachunek, mbanktransactions.z_Rachunku, mbankcustomers.firstName,mbankcustomers.lastName, a1.firstName, a1.lastName  FROM mbanktransactions\n" +
                "\n" +
                "JOIN mbankcustomers on mbanktransactions.z_Rachunku = mbankcustomers.account_no\n" +
                "JOIN mbankcustomers as a1 on mbanktransactions.na_Rachunek = a1.account_no\n" +
                "WHERE(z_Rachunku=" + accountNumber + " OR " + " na_Rachunek= " + accountNumber+" ) ORDER BY Date DESC;";

        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            String firstName1;
            String lastName1;

            boolean runMethod=true;
            while(runMethod) {

                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    int check = rs.getInt(4);


                    int counter = transactionCounter++;
                    System.out.println("Numer: " + counter);


                    if (check == accountNumber) {
                        transaction.setNumber(counter);
                        String date = rs.getString(3);
                        transaction.setDate(date);
                        firstName1 = rs.getString(6);
                        transaction.setRecipientSenderFirstName(firstName1);
                        lastName1 = rs.getString(7);
                        transaction.setRecipientSenderLastName(lastName1);
                        String title = rs.getString(2);
                        transaction.setTitle(title);
                        int amount = rs.getInt(1);
                        transaction.setAmount(amount);
                        int account = rs.getInt(5);
                        transaction.setAccountNumber(account);
                        transaction.setBeingRecipient(true);
                        myTransactionsList.add(transaction);

                        System.out.println("Data: " + date);

                        System.out.println("Nadawca: " + firstName1 + " " + lastName1);

                        System.out.println("Tytuł: " + title + " Kwota: " + amount);

                        System.out.println();

                    } else {
                        transaction.setNumber(counter);
                        String date = rs.getString(3);
                        transaction.setDate(date);
                        System.out.println("Data: " + date);
                        String firstName = rs.getString(8);
                        transaction.setRecipientSenderFirstName(firstName);
                        String lastName = rs.getString(9);
                        transaction.setRecipientSenderLastName(lastName);
                        System.out.println("Odbiorca: " + firstName + " " + lastName);
                        String title = rs.getString(2);
                        transaction.setTitle(title);
                        int amount = rs.getInt(1);
                        transaction.setAmount(amount);
                        System.out.println("Tytuł: " + title + " Kwota: " + amount);
                        int account = rs.getInt(4);
                        transaction.setAccountNumber(account);
                        transaction.setBeingRecipient(false);
                        myTransactionsList.add(transaction);
                        System.out.println();


                    }


                }

                while(runMethod) {

                System.out.println("Dostępne są operacje:");
                System.out.println("1. Generowanie potwierdzenia transakcji");
                System.out.println("2. Wyszukiwanie określonej transakcji");
                System.out.println("3. Powrót do menu");
                System.out.println("Wybierz operację od 1 do 2 aby kontynuować albo wybierz  3 aby wrócić do menu");

                Scanner scannerSearchGenerate = new Scanner(System.in);
                int inputSG = scannerSearchGenerate.nextInt();
                runMethod =true;

                switch (inputSG) {

                    case 1 -> {


                        System.out.println("Jeśli chcesz wygenerować potwierdzenie wybierz odpowiedni numer transakcji z listy wyżej");


                        System.out.println("Jeśli chcesz wrócić do menu - wciśnij x");
                        Scanner scanner1 = new Scanner(System.in);

                        String input = scanner1.nextLine();
                        if (input.equals("x") || input.equals("X")) {
                            runMethod = false;
                            break;


                        }
                        int convertedString = Integer.parseInt(input);


                        if (convertedString <= myTransactionsList.size()) {

                            Transaction myTransaction = myTransactionsList.get(convertedString - 1);


                            PDF mypdf = new PDF();
                            mypdf.generateConfirmationPDF(myTransaction, accountNumber, nameOfHoldr, lastNameOfHolder);
                            System.out.println("Potwierdzenie operacji numer:" + input + " wygenerowano pomyślnie");
                            System.out.println();
                        } else {
                            System.out.println("Error - nieprawidłowy numer transakcji. Podaj numer ponownie");
                            System.out.println();
                        }


                }
                case 2 -> {
                         SearchHistory.run = true;
                    SearchHistory.searchTransactionsBy(accountNumber);


                }
                case 3 -> {
                        runMethod = false;
                        break;

                }
            }


            }





        } } catch (SQLException e) {
            throw new RuntimeException(e);
        }}}



