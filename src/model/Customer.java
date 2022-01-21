package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    String firstName;
    String lastName;
    String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Customer(String firstName, String lastName, String email) {

        String emailRegex = "^(.+)@(.+)\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) throw new IllegalArgumentException("Error, invalid Email!\n");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\tEmail: " + email;
    }

}
