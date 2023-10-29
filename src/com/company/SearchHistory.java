package com.company;

import java.sql.*;
import java.util.Scanner;

public class SearchHistory {

    static Connection con = null;

    public static void  createConnection() {

        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/test";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        static boolean run = true;
    static boolean runSearch = true;




    public static void searchagain () {
        System.out.println();

        System.out.println("Czy wyszukać ponownie (Tak/Nie)");
        Scanner scanner56 = new Scanner(System.in);
        String inputSearchAgain = scanner56.nextLine();
        if (inputSearchAgain.equals("Nie") || inputSearchAgain.equals("nie")) {
            System.out.println("Czy powrócić do menu? (Tak/Nie)");
            Scanner scanner3 = new Scanner(System.in);
            String inputSearchAgain1 = scanner3.nextLine();
            if (inputSearchAgain1.equals("Nie") || inputSearchAgain1.equals("nie")) {
                runSearch = true;
            } else if (inputSearchAgain1.equals("Tak") || inputSearchAgain1.equals("tak")) {
                runSearch = false;
            } else {
                System.out.println("Błąd podano niepoprawną odpowiedź - Podaj odpowiedź Tak lub Nie");
                searchagain();
            }

        } else if (inputSearchAgain.equals("Tak") || inputSearchAgain.equals("tak")) {
            runSearch = true;
        } else {
            System.out.println("Błąd podano niepoprawną odpowiedź - Podaj odpowiedź Tak lub Nie");
            searchagain();
        }
    }


    public static void searchTransactionsBy(int accountNumber) {
        createConnection();

        System.out.println();
        while(run) {

            System.out.println("Jeśli chces szukać określonej transakcji wybierz odpowiedni filter przez wpisane cyfry od 1 - 6");
            System.out.println("Jeśli chcesz wyjść z opcji wyszukiwania wybierz 7");
            System.out.println();
            System.out.println("1.Numer Transakcji");
            System.out.println("2.Z jakiego rachunku");
            System.out.println("3.Na jaki rachunek");
            System.out.println("4.Kwota");
            System.out.println("5.Tytuł");
            System.out.println("6.Data");
            System.out.println("7.Wyjście");
            System.out.println();
            System.out.println("Podaj wybrany filter");

            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            runSearch = true;

            switch (input) {
                case 1 -> {


                    while (runSearch) {
                        System.out.println("Podaj ID transakcji");

                        Scanner scanner1 = new Scanner(System.in);
                        String inputTransactionId = scanner1.nextLine();
                        String query = "SELECT * FROM  mbanktransactions WHERE numerTransakcji= '" + inputTransactionId + "'";
                        try {
                            Statement st = con.createStatement();
                            ResultSet resultSet = st.executeQuery(query);
                            System.out.println();

                            while (resultSet.next()) {
                                System.out.println();
                                System.out.println("Informacje dotyczące transakcji: " + inputTransactionId);
                                System.out.println("Transakcja wykonana z rachunku: " + resultSet.getInt(2));
                                System.out.println("Transakcja wykonana na  rachunek: " + resultSet.getInt(3));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        searchagain();
//

                    }
                }


                case 2 -> {

                    while (runSearch) {

                        System.out.println("Podaj numer rachunku z którego wykonano transakcję");

                        Scanner scanner1 = new Scanner(System.in);
                        int inputAccountFrom = scanner1.nextInt();
                        String query = "SELECT * FROM  mbanktransactions WHERE z_Rachunku= '" + inputAccountFrom + "'";

                        try {
                            Statement st = con.createStatement();
                            ResultSet resultSet = st.executeQuery(query);
                            while (resultSet.next()) {
                                System.out.println("Informacje dotyczące transakcji wykonanych z rachunku: " + inputAccountFrom);
                                System.out.println("Numer ID transakcji: " + resultSet.getString(1));
                                System.out.println("Transakcja wykonana na  rachunek: " + resultSet.getInt(3));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                                System.out.println();
                            }


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        searchagain();

                    }


                }
                case 3 -> {
                    while (runSearch) {

                        System.out.println("Podaj numer rachunku na który wykonano przelew");

                        Scanner scanner1 = new Scanner(System.in);
                        int inputAccountTo = scanner1.nextInt();
                        String queryAccountTo = "SELECT * FROM  mbanktransactions WHERE na_Rachunek= '" + inputAccountTo + "'";

                        try {
                            Statement st = con.createStatement();
                            ResultSet resultSet = st.executeQuery(queryAccountTo);
                            System.out.println();

                            while (resultSet.next()) {
                                System.out.println("Informacje dotyczące transakcji wykonanych na rachunek: " + resultSet.getInt(3));

                                System.out.println("Transakcja wykonana z rachunku: " + resultSet.getInt(2));
                                System.out.println("Numer ID transakcji: " + resultSet.getString(1));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                                System.out.println();


                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        searchagain();

                    }
                }

                case 4 -> {

                    while (runSearch) {
                        System.out.println("Podaj przedział w jakim szukasz kwoty przelewu");

                        System.out.print("Kwota od: ");
                        Scanner scanner1 = new Scanner(System.in);
                        double inputAmount1 = scanner1.nextDouble();
                        System.out.print("Kwota do: ");
                        Scanner scanner2 = new Scanner(System.in);
                        double inputAmount2 = scanner2.nextDouble();

                        String queryAmountSearch = "SELECT * FROM  mbanktransactions WHERE kwota BETWEEN ? AND ?";

                        try {
                            PreparedStatement pst = con.prepareStatement(queryAmountSearch);
                            pst.setDouble(1, inputAmount1);
                            pst.setDouble(2, inputAmount2);

                            ResultSet resultSet = pst.executeQuery();
                            System.out.println();

                            while (resultSet.next()) {
                                System.out.println("Informacje dotyczące transakcji wykonanych z rachunku: " + resultSet.getInt(2));
                                System.out.println("Numer ID transakcji: " + resultSet.getString(1));
                                System.out.println("Transakcja wykonana na  rachunek: " + resultSet.getInt(3));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                                System.out.println();


                            }


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        searchagain();
                    }
                }

                case 5 -> {

                    while (runSearch) {
                        System.out.println("Podaj tytuł przelewu, którego szukasz");

                        Scanner scanner1 = new Scanner(System.in);
                        String inputTitle = scanner1.nextLine();


                        String queryTitle = "SELECT * FROM  mbanktransactions WHERE tytuł LIKE ?";


                        try {
                            PreparedStatement pst = con.prepareStatement(queryTitle);
                            pst.setString(1, inputTitle + "%");

                            ResultSet resultSet = pst.executeQuery();
                            System.out.println();
                            while (resultSet.next()) {
                                System.out.println("Informacje dotyczące transakcji wykonanych z rachunku: " + resultSet.getInt(2));
                                System.out.println("Numer ID transakcji: " + resultSet.getString(1));
                                System.out.println("Transakcja wykonana na rachunek: " + resultSet.getInt(3));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                                System.out.println();
                            }

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        searchagain();
                    }
                }

                case 6 -> {
                    while (runSearch) {
                        System.out.println("Podaj przedział czasu w którym wykonano przelew, którego szukasz");
                        System.out.println("Data od: ");
                        Scanner scanner1 = new Scanner(System.in);
                        String inputData1 = scanner1.nextLine();
                        System.out.println("Podaj date do której mam szukać przelewu");
                        Scanner scanner2 = new Scanner(System.in);
                        String inputData2 = scanner2.nextLine();


                        String queryDates = "SELECT * FROM  mbanktransactions WHERE Date BETWEEN ? AND ?";
                        try {
                            PreparedStatement pst = con.prepareStatement(queryDates);
                            pst.setString(1, inputData1);
                            pst.setString(2, inputData2);

                            ResultSet resultSet = pst.executeQuery();

                            while (resultSet.next()) {
                                System.out.println("Informacje dotyczące transakcji wykonanych z rachunku: " + resultSet.getInt(2));
                                System.out.println("Numer ID transakcji: " + resultSet.getString(1));
                                System.out.println("Transakcja wykonana na  rachunek: " + resultSet.getInt(3));
                                System.out.println("Transakcja na kwotę: " + resultSet.getDouble(4));
                                System.out.println("Tytuł transakcji: " + resultSet.getString(5));
                                System.out.println("Data transakcji: " + resultSet.getString(6));
                                System.out.println();


                            }


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        searchagain();
                    }
                }

                case 7 -> run = false;

                default -> System.out.println("Błąd - nie podano filtra z listy. Podaj filtr wyszukiwania ponownie.");


            }
            System.out.println();
            System.out.println("---------------------------");
            System.out.println();


        }}}








