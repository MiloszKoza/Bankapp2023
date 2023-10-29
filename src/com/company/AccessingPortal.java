package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    boolean setEmail = false;


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

            if (inputCustomerNumber == myList.get(i) && setEmail == true) {
                 System.out.println("Numer klienta: " + inputCustomerNumber + " jest prawidłowy");

                corectCustomerNumber = true;
                break;
            }  else if (inputCustomerNumber == myList.get(i)) {
                    //  System.out.println("Account Found :" + inputAccount);
                    corectCustomerNumber = true;
                    break;
            } else if (myList.get(i) != inputCustomerNumber && i == myList.size() - 1 &&  setEmail == true) {
                System.out.println("Numer klienta: " + inputCustomerNumber + " nie jest prawidłowy - Spróbuj ponownie");
                corectCustomerNumber = false;

                System.out.println("Podaj numer klienta ponownie");
                Scanner scanner = new Scanner(System.in);
                int cNumber = scanner.nextInt();
                setEmail  = true;
                checkAccount(cNumber);


                } else if (myList.get(i) != inputCustomerNumber && i == myList.size() - 1) {
                    corectCustomerNumber = false;

                }

            }
            return corectCustomerNumber;
        }


    public   Customer getCustomerDetails (int customerNumber ){

        String query = " SELECT *  FROM mbankcustomers WHERE customerNumber = " +customerNumber ;


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

    public String  setEmail() {

        String query = "SELECT email FROM mbankcustomers";

        boolean runMethod = true;
        String inputEmail = "";

        ArrayList<String> listEmails = new ArrayList<>();


        try {
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                listEmails.add(rs.getString(1));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        while (runMethod) {


            System.out.println("Podaj swój adres email");
            Scanner scanner2 = new Scanner(System.in);
             inputEmail = scanner2.nextLine();


            for (int i = 0; i < listEmails.size(); i++) {
                if (!inputEmail.contains("@")  && !inputEmail.contains(".")) {
                    System.out.println("Nie prawidłowy format adresu email");
                    break;
            } else if (inputEmail.equals(listEmails.get(i))) {
                    System.out.println("Adres email jest już wykorzystany - podaj inny adres email");
                    break;
                } else if (!inputEmail.equals(listEmails.get(i)) && i == listEmails.size() - 1) {
                    runMethod = false;
                    break;

                }
            }


        }
        return inputEmail;
    }

    public int  setPhoneNumber() {
        boolean run = true;
        String  inputPhoneNumber = "";
        int convrtedinputPhoneNumber =0;
        while(run) {
        System.out.println("Podaj numer telefonu");
        Scanner scanner = new Scanner(System.in);
         inputPhoneNumber = scanner.nextLine();

        if ( inputPhoneNumber.length() < 9 ) {
            System.out.println("Nieprawidłowy numer telefonu - numer jest za krótki");
        } else {
             convrtedinputPhoneNumber = Integer.parseInt(inputPhoneNumber);
            run  = false;
        }
        }
        return convrtedinputPhoneNumber;
    }

    public String  setNewpassword () {

        boolean run = true;
        String inputNewPassword ="";
        while (run) {
            System.out.println("Wprowadź nowe hasło do logowania");

            Scanner scanner = new Scanner(System.in);
             inputNewPassword = scanner.nextLine();

            boolean have12characters = false;
            boolean hasSpecialcharacter = false;
            boolean hasDigit = false;
            boolean hasUppercaseCharacter = false;


            if (inputNewPassword.length() >= 12) {
                have12characters = true;
            }

            for (int i = 0; i < inputNewPassword.length(); i++) {

                if (!Character.isDigit(inputNewPassword.charAt(i)) && !Character.isLetter(inputNewPassword.charAt(i)) && !Character.isWhitespace(inputNewPassword.charAt(i)) ) {

                    hasSpecialcharacter = true;


                }
            }

            for (int i = 0; i < inputNewPassword.length(); i++) {
                if (Character.isDigit(inputNewPassword.charAt(i))) {
                    hasDigit = true;

                }
            }
            for (int i = 0; i < inputNewPassword.length(); i++) {
                if (Character.isUpperCase(inputNewPassword.charAt(i))) {

                    hasUppercaseCharacter = true;


                }
            }

            if (have12characters && hasSpecialcharacter && hasDigit && hasUppercaseCharacter) {
                System.out.println("Hasło poprawne");


            } else {
                System.out.println("Hasło nie spełnia wymagań - spróbuj podać inne hasło");
                setNewpassword();
            }


            System.out.println("Powtórz nowe hasło");
            Scanner scanner1 = new Scanner(System.in);
            String inputConfirmedPassword = scanner1.nextLine();

            if( inputConfirmedPassword.equals(inputNewPassword)){
                System.out.println("Nowe hasło zostało poprawnie ustawione");
                run = false;
                break;
            } else {
                System.out.println("Hasła nie sa takie same - spróbuj ponownie");

            }
        }
  return inputNewPassword;

    }

    public void insertingDataRegistration (String password, String email, int phoneNumber, int customerNumber ) {

        String query = "UPDATE  mbankcustomers SET password1 =? , email =? , phoneNumber=?  WHERE customerNumber= " + customerNumber;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
             preparedStatement.setString(1,password);
             preparedStatement.setString(2,email);
             preparedStatement.setInt(3,phoneNumber);

             preparedStatement.executeUpdate();

            System.out.println("Rejestracja przebiegła pomyślnie");


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void launchingPortal ( ) {
        System.out.println("Witaj w portalu bankowości elektronicznej");
        System.out.println("W celu rejestracji wybierz 1, w przypadku logowania do serwisu 2");

        Scanner scanner = new Scanner(System.in);

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println("Rejestracja do portalu");

                boolean runRegistration = true;
                while (runRegistration) {

                    System.out.println("W celu rejestracji podaj swój numer klienta");

                    Scanner scanner1 = new Scanner(System.in);
                    int inputCustomerNumber = scanner1.nextInt();
                    setEmail = true;
                    checkAccount(inputCustomerNumber);

                 int enteredPhonenumber =  setPhoneNumber();
                  String  enteredEmail = setEmail();
                   String enteredPassword = setNewpassword();

                    insertingDataRegistration(enteredPassword,enteredEmail,enteredPhonenumber, inputCustomerNumber );
                    runRegistration = false;

                }
                System.out.println();
                launchingPortal();
            }
            case 2 -> {
                for (int i =0; i < 3; i ++) {

                System.out.println("W celu logowania do portalu podaj numer klienta");

            Scanner scanner1 = new Scanner(System.in);
             try {
                 int inputCustomerNumber = scanner1.nextInt();


                   String checkedPassword =  getCustomerDetails(inputCustomerNumber).getPassword();

                    System.out.println("Podaj hasło do logowania");

                    Scanner scanner2 = new Scanner(System.in);
                    String password = scanner2.nextLine();

             if (password.equals(checkedPassword ) && checkAccount(inputCustomerNumber) ) {
                 System.out.println("Logowanie przebiegło  poprawnie");
                 System.out.println();
                 Menu menu = new Menu();
                 menu.accessingMenu(inputCustomerNumber);
                 break;

             }  else  if ( i == 2) {
                 System.out.println("Numer klienta albo hasło nieprawidłowe");
                     System.out.println(" 3 próby nieprawdiłowego logowania - konto zablokowane");
             } else   {
                 System.out.println("Numer klienta albo hasło nieprawidłowe - spróbuj ponownie");
                 System.out.println();
             }

             }catch (InputMismatchException ex) {
                 System.out.println("Nieprawidłowy format numeru klienta");
                 System.out.println();
             }


        }

    }
}}}
