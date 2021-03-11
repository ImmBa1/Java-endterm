package bankingservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Transaction {//transcation class with 3 integers: number of card, pincode, balance
    private Integer cardNumber;

    private Integer pincode;

    private Integer balance;

    Transaction(Integer cardNumber, Integer pincode) {//constructor for Transaction class with parameters cardNumber and pincode
        this.cardNumber = cardNumber;
        this.pincode = pincode;
    }

    public double showBalance(Integer cardNumber) {//method showBalance
        try {
            Connection connection = Database.connection();//connects to database

            Statement statement5 = connection.createStatement();//new statement to make query

            String sql5 = "SELECT * FROM Balance WHERE card_number = '" + cardNumber + "'";//selects number of card from Balance table
            ResultSet resultset2 = statement5.executeQuery(sql5);//database information is represented in resultset

            while (resultset2.next()) {
                this.balance = resultset2.getInt(2);//assigns value of cardNumber in the second column called balance
            }
        } catch (Exception e) {//prints exception if error occurs
            System.out.println(e);
        }
        return this.balance;//returns value of balance
    }

    public void deposit(Integer amount, Integer cardNumber) {//deposit class which makes it possible to insert money into account
        try {
            Connection connection = Database.connection();//connection

            Statement statement6 = connection.createStatement();//new statement to make a query

            String sql6 = "UPDATE Balance SET balance = balance + '" + amount + "' WHERE card_number = '" + cardNumber + "'";
            //Update balance in Balanca table and sets value balance = (current)balance + amount where card number = number of the user
            statement6.executeUpdate(sql6);//executeUpdate executes the query
        } catch (Exception e) {
            System.out.println(e);//if error occurs, exception's name appears
        }
    }

    public void transferMoney(Integer amount_other, Integer number_other, Integer cardNumber){//method which sends money to other card
        try {
            Connection connection = Database.connection();

            Statement statement7 = connection.createStatement();//new statement to make a query
            String sql7 = "UPDATE Balance SET balance = balance + '" + amount_other + "' WHERE card_number = '" + number_other + "'";
            //updating balance column in Balance table for specific card number. Owner of another card receives money
            statement7.executeUpdate(sql7);//query is executed


            Statement statement8 = connection.createStatement();
            String sql8 = "UPDATE Balance SET balance = balance - '" + amount_other + "' WHERE card_number = '" + cardNumber + "'";
            //balance of sender decreases by amount of transfered money
            statement8.executeUpdate(sql8);


        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Withdrawal(Integer amount, Integer cardNumber){//method to withdraw money
        try {
            Connection connection = Database.connection();//setting connection

            Statement statement9 = connection.createStatement();
            String sql9 = "UPDATE Balance SET balance = balance - '" + amount + "' WHERE card_number = '" + cardNumber + "'";
            //balance decreases by amount of money of withdraw operation. balance decreases for specific card number
            statement9.executeUpdate(sql9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
