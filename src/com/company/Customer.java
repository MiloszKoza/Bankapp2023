package com.company;

public class Customer {
    private int customerNumber;
    private int account_no;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int phoneNumber;
    private double balance;


    public Customer(int customerNumber, int account_no, String firstName, String lastName, String password, String email, int phoneNumber, double balance) {
        this.customerNumber = customerNumber;
        this.account_no = account_no;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
    }

    public void setCustomerNumber (int customerNumber){

    this.customerNumber = customerNumber;
 }

 public int getCustomerNumber () {
     return customerNumber;
 }

 public void setFirstName ( String firstName) {

     this.firstName = firstName;
 }

 public  String getFirstName () {
     return firstName;
 }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getAccount_no() {
        return account_no;
    }

    public void setAccount_no(int account_no) {
        this.account_no = account_no;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerNumber=" + customerNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", balance=" + balance +
                '}';
    }
}