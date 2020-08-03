package banking;

import java.util.Objects;

public class User {

    private String number;
    private String pin;
    private long balance;

    public User(String number, String pin, int balance) {
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public String getCardNumber() {
        return number;
    }

    public boolean checkPin(String pin) {
        return this.pin.equals(pin);
    }

    public String getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }
}
