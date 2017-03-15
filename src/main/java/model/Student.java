package model;

/**
 * Created by SteveQ on 2017-03-15.
 */
public class Student {
    private Integer Id;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String parentEmail;
    private String street;
    private String city;
    private String postalCode;
    private Integer houseNumber;

    public Student(String firstName, String lastName, String dateOfBirth, String parentEmail, String street, String city, String postalCode, Integer houseNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.parentEmail = parentEmail;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
    }

    public Student(Integer id, String firstName, String lastName, String dateOfBirth, String parentEmail, String street, String city, String postalCode, Integer houseNumber) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.parentEmail = parentEmail;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.houseNumber = houseNumber;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
}
