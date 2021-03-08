# Java-endterm
This is my first Java project where i have tried to create a banking system.

My project consists of for classes: Account, Database, Transaction, Application.

1)Account class which allows to create and account of user with signup() method where user can create his account and receive card number with pincode. 
  Also there are two method which create random numbers for card number and pincode

2)Database class allows to make connection between java code and postgresql. 
  Here I set connection by providing link to localhost throw jdbc driver and entered user name and password to connect my database.

3)Transaction class is a class where all main methods with money are made. Here are three variables cardNumber, pincode and balance. 
  showBalance() is used to output current balance of account.
  Deposit() is used for inserting money into account.
  Transfer() is used to send money to another card.
  Withdrawal() is used to take money from account.

4)In Application class whole menu is made. Menu can be used by typing number of option like [1,2,3,4,5]. 
  In this class methods from Account and Transaction classes are called. The program run starts in this class. 
 
