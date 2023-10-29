package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Transaction {

    private int number;
    private String date;
    private String recipientSenderFirstName;
    private String recipientSenderLastName;
    private int accountNumber;
    private String title;
    private int amount;
    private boolean beingRecipient;

    public boolean isBeingRecipient() {
        return beingRecipient;
    }

    public void setBeingRecipient(boolean beingRecipient) {
        this.beingRecipient = beingRecipient;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getRecipientSenderFirstName() {
        return recipientSenderFirstName;
    }

    public void setRecipientSenderFirstName(String recipientSenderFirstName) {
        this.recipientSenderFirstName = recipientSenderFirstName;
    }

    public String getRecipientSenderLastName() {
        return recipientSenderLastName;
    }

    public void setRecipientSenderLastName(String recipientSenderLastName) {
        this.recipientSenderLastName = recipientSenderLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

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

    public static String generateRandomString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789" + "abcdefghijklmnopqrstuvxyz" + "0123456789";

        StringBuilder sb = new StringBuilder(n);


        for (int i=0; i  < n; i++){

            int index = (int) (AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));

        }

        return sb.toString();
}


public void saveTransaction( String transactionReference, int fromAccount, int toAccount, double amount, String title) {

    String query = " INSERT INTO mbanktransactions VALUES(?,?,?,?,?,?)";

    LocalDate date = LocalDate.now();

    String convertedDate = String.valueOf(date);



    try {
        PreparedStatement pst = con.prepareStatement(query);

        pst.setString(1, transactionReference);
        pst.setInt(2, fromAccount);
        pst.setInt(3, toAccount);
        pst.setDouble(4, amount);
        pst.setString(5, title);
        pst.setString(6, convertedDate);
        pst.executeUpdate();

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
}
    public  double getBalance( int accoutNumber) {
        String query = "SELECT balance FROM mbankcustomers  WHERE account_no=" + accoutNumber;
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

// Generic method to perform a transaction
public void performTransaction () {

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

        currentBalanceAccount1 = getBalance(inputAccount1);

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


        currentBalanceAccount2 = getBalance(inputAccount2);

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



        String query1 = "UPDATE mbankcustomers SET balance =? WHERE account_no=?";
        String query2 = "UPDATE mbankcustomers SET balance =? WHERE account_no=?";

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
        connect();
        saveTransaction(Transaction.generateRandomString(20), inputAccount1, inputAccount2, amount, title );
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
        }}}

    //Method to perfom a transaction on a specific account
    public void performTransactionApp (int accountNumber) {

        boolean runApp = true;
        boolean runApp1 = true;
        boolean runApp2 = true;
        boolean runApp3 = true;


        Double currentBalanceAccount1 = 0d;
        int inputAccount2 = 0;
        double currentBalanceAccount2 = 0d;


        double update1 = 0d;
        double update2 = 0d;

        Double amount = 0d;




            System.out.println("Z konta: " + accountNumber);

            currentBalanceAccount1 = getBalance(accountNumber);


        while (runApp1) {

            System.out.println();
            System.out.println("Podaj numer konta na który wykonać przelew");
            Scanner scan1 = new Scanner(System.in);
            inputAccount2 = scan1.nextInt();


            currentBalanceAccount2 = getBalance(inputAccount2);

            if (currentBalanceAccount2 > 0 && accountNumber !=inputAccount2) {
                runApp1 = false;

            } else {
                System.out.println("Nie prawidłowe konto - wprowadź konto ponownie");
                System.out.println();
            }
        }
        while (runApp2) {

            System.out.println("Podaj kwotę przelewu");
            Scanner scan2 = new Scanner(System.in);
            amount = scan2.nextDouble();

            update1 = currentBalanceAccount1 - amount;


            if (amount <= 0) {
                System.out.println("Kwota przelewu musi byc większa od zera");




            } else if (update1 < 0) {
                System.out.println("Nie masz wystarczających środków na dokonanie przelwu");
                System.out.println("Podaj kwotę ponownie");
                System.out.println("");
            } else if (update1 > 0)
                runApp2 = false;
            update2 = currentBalanceAccount2 + amount;

        }
        String title = " ";
        while(runApp3) {
            System.out.println("Podaj tytuł przelewu");

            Scanner scan = new Scanner(System.in);
            title = scan.nextLine();


            if (title.isEmpty()) {
                System.out.println("Tytuł przelewu nie może być pusty");
            } else {
                runApp3 = false;
            }

        }

        PreparedStatement pst = null;
        PreparedStatement pst1 = null;

        try {


            con.setAutoCommit(false);



            String query1 = "UPDATE mbankcustomers SET balance =? WHERE account_no=?";
            String query2 = "UPDATE mbankcustomers SET balance =? WHERE account_no=?";

            //     main1.checkAccount(inputAccount1);
            //    main1.checkAccount(inputAccount2);

            pst = con.prepareStatement(query1);
            pst1= con.prepareStatement(query2);

            pst.setDouble(1, update1);
            pst.setInt(2, accountNumber);


            pst1.setDouble(1,update2);
            pst1.setInt(2,inputAccount2 );





            pst.executeUpdate();

            pst1.executeUpdate();

            con.commit();
            connect();
            saveTransaction(Transaction.generateRandomString(20), accountNumber, inputAccount2, amount, title );
            System.out.println("Transakcja przebiegła pomyślnie");
            System.out.println("Wykonano przelew z konta: " + accountNumber + " na konto: " + inputAccount2+ " w wysokości: " + amount);
            System.out.println("Tytuł przelewu: " + title);
            //System.out.println("Numer transakcji: " + Transaction.generateRandomString(20));

        } catch(SQLException e){
            System.out.println(e);

            if(con!= null){
                System.out.println("Transakcja jest anulowana");
                try {
                    con.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }}}}










