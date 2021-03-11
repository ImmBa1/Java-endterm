package bankingservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Account {//creating a class Account with two strings of user's first and last name
    private String firstName;
    private String lastName;

    Account(String firstName, String lastName){//constructor for the class
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean signup(){//method which make it possible to create an account
        try{
            Connection connection = Database.connection();//connection to database throw the class Database.java

            Statement statement = connection.createStatement();//Creating statement which allows to make queries in database
            String sql = "INSERT INTO Account (first_name, last_name) VALUES ('" + this.firstName + "', '" + this.lastName + "')";
            statement.executeUpdate(sql);//we create sql string which inserts firstName + lastName and then we execute it throw the interface statement

            Statement statement2 = connection.createStatement();//same thing but here we select last element of id which auto-increments in postgres
            String sql2 = "SELECT last_value from account_account_id_seq";
            ResultSet resultset1 = statement2.executeQuery(sql2);//resultset object retrieves rows from database

            int last_account_id = 0;

            while (resultset1.next()){//if the row is created it assigns 1 to last_acount_id
                last_account_id = resultset1.getInt(1);
            }

            String cardNumber = this.generateCardNumber();//generates random card number in generateCardNumber() method

            String pincode = this.generatePincode();//generates random pincode number in generatePincode() method

            Statement statement3 = connection.createStatement();//new statement

            String sql3 = "INSERT INTO Card VALUES (" + last_account_id + ", " + cardNumber + ", " + pincode + ")";
            statement3.executeUpdate(sql3);//insert values of id, cardnumber, pincode by executeUpdate

            Statement statement4 = connection.createStatement();//net statement

            String sql4 = "INSERT INTO Balance VALUES (" + cardNumber + ", '0')";
            statement4.executeUpdate(sql4);//inserts cardnumber into Balance by executeUpdate

            connection.close();//closes connection to database

            System.out.println("\n\nAccount was successfully created");
            System.out.println("Card number: " + cardNumber);
            System.out.println("Pincode: " + pincode);

            return true;
        }catch (Exception e){//if statements in try are performed inproperly it prints exception and registration fails
            System.out.println(e);

            return false;
        }
    }

    public String generateCardNumber(){//method for creating random number for Card
        int length = 8;

        String randDigits = "1234567890";

        char[] cardNumber = new char[length];

        for(int i=0; i<length; i++){//creates diff digits in the loop of 8 digits
            int rand = (int) (Math.random() * randDigits.length());
            cardNumber[i] = randDigits.charAt(rand);
        }
        return new String(cardNumber);
    }

    public String generatePincode(){//method for creating random number for pincode
        int length = 4;

        String randDigits = "1234567890";

        char[] Pincode = new char[length];

        for(int i=0; i<length; i++){//creates diff digits i the loop of 4 digits
            int rand = (int) (Math.random() * randDigits.length());
            Pincode[i] = randDigits.charAt(rand);
        }
        return new String(Pincode);
    }
}
