package com.company;

import java.util.Scanner;

public class Menu {


        public void showCustomerDetails( Customer customer) {


            System.out.println("Numer Rachunku: " +     customer.getCustomerNumber());
            System.out.println("Numer Konta: " +     customer.getAccount_no());
            System.out.println("Stan Konta: " + customer.getBalance());
            System.out.println("Imię Klienta: " +     customer.getFirstName());
            System.out.println("Nazwisko Klienta: " +     customer.getLastName());
            System.out.println("Hasło Do Logowania: " +     customer.getPassword());
            System.out.println("Email: " +     customer.getEmail());
            System.out.println("Numer Telefonu: " +     customer.getPhoneNumber());



    }


    public void accessingMenu (int customeNumber) {

            boolean shouldContinue = true;
        AccessingPortal as = new AccessingPortal ();
        as.connect();

       String  nameOfHoldr =  as.getCustomerDetails(customeNumber).getFirstName();
        System.out.println("Witaj " + nameOfHoldr +   " na koncie klienta banku Mbank");



            while(shouldContinue) {
                System.out.println();
        System.out.println("Wybierz opcję:");
        System.out.println("1. Sprawdzenie historii rachunku");
        System.out.println("2. Wykonanie przelewu");
        System.out.println("3. Dodanie zdefiniowanego odbiorcy");
        System.out.println("4. Informacje o prowadzonym rachunku");
        System.out.println("5. Wylogowywanie");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        Transaction transaction = new Transaction();
        transaction.connect();

        BankHistory bh = new BankHistory();
        bh.connect();





        switch (choice) {
            case 1 -> {
                System.out.println("Historia rachunku");

                int accountNr = as.getCustomerDetails(customeNumber).getAccount_no();


                bh.showBankHistory(accountNr);

                System.out.println();
//                System.out.println("Jesli chcesz wrocic do menu - wcisnij x");
//                Scanner scanner1 = new Scanner(System.in);
//                String exit = scanner1.nextLine();
//
//
//                if (exit.equals("x") || exit.equals("X")) {
//                    break;
//                }
            }
            case 2 -> {
                System.out.println("Wykonanie przelewu");
                System.out.println();


             int accountNr = as.getCustomerDetails(customeNumber).getAccount_no();


                transaction.performTransactionApp(accountNr);
                System.out.println();
                System.out.println("Jeśli chcesz wrócić do menu - wciśnij x");
                Scanner scanner1 = new Scanner(System.in);
                String exit = scanner1.nextLine();


                if (exit.equals("x") || exit.equals("X")) {
                    break;
                }
            }

            case 3 -> { System.out.println("Dodanie zdefiniownaego odbiorcy");

                System.out.println();
                System.out.println("Jeśli chcesz wrócić do menu - wciśnij x");
                Scanner scanner1 = new Scanner(System.in);
                String exit = scanner1.nextLine();


                if (exit.equals("x") || exit.equals("X")) {
                    break;
                } }
            case 4 -> {
                System.out.println("Informacje o prowadzonym rachunku");



               showCustomerDetails(as.getCustomerDetails(customeNumber));

                System.out.println();
                System.out.println("Jeśli chcesz wrócić do menu - wciśnij x");
                Scanner scanner1 = new Scanner(System.in);
                String exit = scanner1.nextLine();


                if (exit.equals("x") || exit.equals("X")) {
                    break;
                }



            }

            case 5 -> {

                shouldContinue = false;
                System.out.println("Wylogowywanie...");

                System.out.println("Wylogowywanie przebiegło pomyślnie");}

            default -> System.out.println("Niewybrano żadnej opcji - wybierz jedną z powyższych opcji");
        }


    }
} }
