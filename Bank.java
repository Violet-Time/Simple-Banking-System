package banking;

import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.util.*;

public class Bank {

    //private TreeMap<Long, User> userList = new TreeMap();
    private DBUtility dbUtility;
    private long bankNumber = 400000L;
    private int lastCardNumber = 0;
    Random random = new Random();

    public Bank(String path) {
        dbUtility = new DBUtility(path);
        User lastAddedUser = dbUtility.getLastAddedUser();
        if (lastAddedUser != null) {
            lastCardNumber = Integer.parseInt(lastAddedUser.getCardNumber().substring(6,15));
        }
    }

    public User getUser(String cardNumber, String pin) {
        if (!checkBankNumber(cardNumber)) {
            return null;
        }
        User user = dbUtility.getUser(cardNumber);
        if (user == null) {
            return null;
        }
        return user.checkPin(pin) ? user : null;
    }
    public User deleteUser(User user) {
        dbUtility.delete(user.getCardNumber());
        return null;
    }

    public boolean search(String cardNumber) {
        return dbUtility.getUser(cardNumber) != null;
    }

    public User addIncome(User user, int money) {
        dbUtility.updateBalance(user.getCardNumber(), money);
        return getUser(user.getCardNumber(), user.getPin());
    }

    public User transfer(User from, String to, int sum) {
        dbUtility.updateBalance(from.getCardNumber(), -sum);
        dbUtility.updateBalance(to, sum);
        return getUser(from.getCardNumber(),from.getPin());
    }

    public User createUser() {
        long generateBankNumber = generateBankNumber();
        while (dbUtility.getUser(String.valueOf(generateBankNumber)) != null) {
            generateBankNumber = generateBankNumber();
        }
        User user = new User(String.valueOf(generateBankNumber), String.format("%04d",random.nextInt(9999)), 0);
        dbUtility.addUser(user);
        return user;
    }

    public boolean checkBankNumber(String number) {
        if (number.length() < 16) {
            return false;
        }
        int sum = 0;
        int tmp = 0;
        for (int i = 0; i < number.length(); i++) {
            if (i % 2 == 0) {
                tmp = Character.getNumericValue(number.charAt(i)) * 2;
                sum += tmp > 9 ? tmp - 9 : tmp;
            } else {
                sum += Character.getNumericValue(number.charAt(i));
            }
        }
        if (sum % 10 == 0) {
            return true;
        } else {
            return false;
        }
    }

    public long generateBankNumber() {
        long generateBankNumber = bankNumber * ((long) Math.pow( 10, 10)) + lastCardNumber * 10;
        int sum = 0;
        int n;
        int j = 0;
        for (long i = 10; i <= (long) Math.pow( 10, 16); i *= 10) {
            j++;
            n = (int) ((generateBankNumber % i) * 10 / i);
            if (j % 2 == 0) {
                n = n * 2;
            }
            sum += n > 9 ? n - 9 : n;
        }
        lastCardNumber++;
        if (sum % 10 == 0) {
            return generateBankNumber;
        } else {
            return generateBankNumber + 10 - sum % 10;
        }

    }

}
