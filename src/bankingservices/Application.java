package bankingservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static java.lang.System.exit;

public class Application {//class application where program runs
    public static boolean isLogged = false;//isLogged is false by default and static means that this variable is the same for whole class. Its value is global

    public static void main(String[] args) throws Exception {
        int option = 0;//option is a variable of chosen number

        Scanner scanner = new Scanner(System.in);//user types number of option and Scanner inputs it

        while (option == 0) {
            System.out.println("~~~~ SELECT AN OPTION ~~~~\n");
            System.out.println("1. Create new account");//if 1 then create account
            System.out.println("2. Sign in\n");//if 2 then log in

            while (option < 1 || option > 2) {//if user types inappropriate number (less than 1 and more than 2) then he has to input option one more time
                System.out.print("Type your choice: ");

                option = scanner.nextInt();
            }
        }

        switch (option) {//switch which includes 2 main cases
            case 1:
                System.out.println("\n\n~~~~ SIGN UP ~~~~\n");//if user decided to create an account
                System.out.print("Enter first name: ");//he types first name


                boolean temp = false;
                while(temp == false) {
                    String firstName = scanner.next().trim();//first name assigns to var firstName. trim() method is used to removes whitespace from both sides of text
                if ((((!firstName.equals("")) && (firstName != null) && (firstName.matches("^[a-zA-Z]*$"))))) {

                    temp = true;

                } else {
                    temp = false;
                    System.out.println("INCORRECT");
                    continue;
                }

                    System.out.print("Enter last name: ");//last name
                    String lastName = scanner.next().trim();//scanner
                    if ((((!lastName.equals("")) && (lastName != null) && (lastName.matches("^[a-zA-Z]*$"))))) {
                        temp = true;
                        Account account = new Account(firstName, lastName);//new instance of Account class is created with these variables firstName and lastName which we assigned earlier
                        account.signup();//signup method of Account class is called

                    } else {
                        temp = false;
                        System.out.println("INCORRECT");
                        continue;
                    }
            }




            case 2:
                while (isLogged == false) {//while user hasn't logged in yet he must enter his card id and pincode to log in
                    System.out.println("\n\nPlease, remember your card id and pincode\n");

                    System.out.println("\n\n~~~~ SIGN IN ~~~~\n");

                    System.out.print("Enter your card number: ");

                    Integer cardNumber = scanner.nextInt();//enter card number int

                    System.out.print("Enter your pincode: ");

                    Integer pincode = scanner.nextInt();//enter pincode int

                    Transaction transaction = new Transaction(cardNumber, pincode);//instance of class Transaction created with cardNumber and pincode assigned earlier

                    try {
                        Connection connection = Database.connection();

                        Statement stmt4 = connection.createStatement();
                        String sql4 = "SELECT * FROM Card WHERE card_number = '" + cardNumber + "' AND pincode = '" + pincode + "'";
                        ResultSet resultset3 = stmt4.executeQuery(sql4);
                        //selects all info from Card
                        if (resultset3.next()) {//if cardnumber and it pincode corresponds to database values then user has passed sign in operation successfully
                            isLogged = true;

                            System.out.println("\n\n~~~~ LOGIN SUCCESS ~~~~\n");

                            System.out.println("--- Enter an option ----\n");

                            System.out.println("1. Balance");
                            System.out.println("2. Deposit");
                            System.out.println("3. Transfer money");
                            System.out.println("4. Withdraw");
                            System.out.println("5. Exit");

                            int option_user = 0;

                            while (option_user < 1 || option_user > 5) {//is user enters number of option bigger than 5 or less than 1 he must repeat the operation until he writes right number
                                System.out.print("\nType your choice: ");
                                option_user = scanner.nextInt();
                                if(option_user <1 || option_user > 5) {//if he types wrong number, "Incorrect value" message appears and it will be on until user writes right number
                                    System.out.println("\n\nIncorrect value, type the correct value");
                                    option_user = scanner.nextInt();
                                }
                            }

                            double balance = 0;

                            while(option_user>0 || option_user<=5) {//while option number bigger than 0 and less or equal to 5
                                switch (option_user) {
                                    case 1:
                                        System.out.println("\n\n~~~~ SHOW BALANCE ~~~~\n");//case 1 show balance

                                        balance = transaction.showBalance(cardNumber);//showbalance method is called and its value assigned to balance var

                                        System.out.println(balance + "$");//sout of balance
                                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~");


                                        System.out.println("\n\n--- Enter an option ----\n");//after that menu selection appears

                                        System.out.println("1. Balance");
                                        System.out.println("2. Deposit");
                                        System.out.println("3. Transfer money");
                                        System.out.println("4. Withdraw");
                                        System.out.println("5. Exit");
                                        System.out.print("\nType your choice: ");
                                        option_user = scanner.nextInt();//user types option

                                        break;
                                    case 2:
                                        System.out.println("\n\n~~~~ MAKE DEPOSIT ~~~~\n");
                                        int amount = 0;
                                        amount = scanner.nextInt();//user types amount of money to insert(only integer, because all money is always integer)

                                        while (amount <= 0) {//if amount less than 0, he must insert again, cause u can not deposit 0 or less money
                                            System.out.print("\nIncorrect. Type amount again: ");

                                            amount = scanner.nextInt();
                                        }

                                        transaction.deposit(amount, cardNumber);//deposit method called

                                        balance = transaction.showBalance(cardNumber);//current showBalance is asigned to balanca ver


                                        System.out.println("\nCurrent balance is " + balance + "$");
                                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~");


                                        System.out.println("\n\n--- Enter an option ----\n");

                                        System.out.println("1. Balance");
                                        System.out.println("2. Deposit");
                                        System.out.println("3. Transfer money");
                                        System.out.println("4. Withdraw");
                                        System.out.println("5. Exit");
                                        System.out.print("\nType your choice: ");
                                        option_user = scanner.nextInt();

                                        break;

                                    case 3:
                                        System.out.println("\n\n~~~~ TRANSFER MONEY ~~~~\n");//transfer money case

                                        System.out.print("Enter number of other client(Please, be careful. If you type in wrong card number, you will have to deal with our support ");

                                        Integer number_other = scanner.nextInt();//number_other is number of other person's card

                                        Statement statement10 = connection.createStatement();
                                        String sql10 = "SELECT card_number from Card where card_number = " + number_other;
                                        //sql query which select card_number from Card table
                                        int amount_other = 0;//money to send

                                        while (amount_other <= 0) {//amount of money to transfer must be positive
                                            System.out.print("Enter amount for other client");

                                            amount_other = scanner.nextInt();
                                        }
                                        transaction.transferMoney(amount_other, number_other, cardNumber);//transfermoney method called

                                        System.out.println("\nYou sent " + amount_other + "$ to " + number_other);
                                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~");


                                        System.out.println("\n\n--- Enter an option ----\n");

                                        System.out.println("1. Balance");
                                        System.out.println("2. Deposit");
                                        System.out.println("3. Transfer money");
                                        System.out.println("4. Withdraw");
                                        System.out.println("5. Exit");
                                        System.out.print("\nType your choice: ");
                                        option_user = scanner.nextInt();
                                        break;

                                    case 4:
                                        System.out.println("\n\n~~~~ WITHDRAW MONEY ~~~~\n");//withdraw money case
                                        amount = scanner.nextInt();

                                        while (amount > balance) {//you can not withdraw money bigger than your balance
                                            System.out.print("\nIncorrect. The amount exceeds your balance\n");

                                            amount = scanner.nextInt();//repeat until you do right
                                        }

                                        transaction.Withdrawal(amount, cardNumber);//withdrawal method called

                                        balance = transaction.showBalance(cardNumber);//showBalacne method called and assigned to balance


                                        System.out.println("\nCurrent balance is " + balance + "$");//current balance output
                                        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~");


                                        System.out.println("\n\n--- Enter an option ----\n");

                                        System.out.println("1. Balance");
                                        System.out.println("2. Deposit");
                                        System.out.println("3. Transfer money");
                                        System.out.println("4. Withdraw");
                                        System.out.println("5. Exit");
                                        System.out.print("\nType your choice: ");
                                        option_user = scanner.nextInt();

                                        break;

                                    case 5:
                                        System.out.println("\n\n~~~~ Application is terminated ~~~~");
                                        exit(0);//method exit which terminates program. In order to call it i had to import from java.lang.System.exit library

                                    default:
                                        break;
                                }
                            }
                        } else {
                            System.out.println("\nLogin error");//if user fails to log in, then error message is printed and he has to rerun the app
                        }
                    } catch (Exception e) {
                        System.out.println(e);//if something goes wrong - exception occurs
                    }
                    break;
                }
            default:
                break;
        }
    }
}
