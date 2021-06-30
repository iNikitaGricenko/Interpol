package bin.data.Models;

import java.sql.Date;

public class Crime extends Model{
    private String Lastname = "";
    private String Firstname = "";
    private String Nationality = "";
    private String Alias = "";
    private String About = "";
    private Date Birthday;
    private String Country = "";
    private int Height;
    private String Sex = "";
    private String Eye = "";
    private String Hair = "";
    private String WantedBy = "";
    private String Grouping = "";
    private byte [] Photo = null;

    public String getGrouping() {
        return Grouping;
    }

    public Crime setGrouping(String grouping) {
        Grouping = grouping;
        return this;
    }


    public Crime() {
        super();
    }

    public Crime(long id) {
        this.setId(id);
    }

    public String getFirstname() {
        return Firstname;
    }

    public Crime setFirstname(String firstname) {
        Firstname = firstname;
        return this;
    }

    public String getLastname() {
        return Lastname;
    }

    public Crime setLastname(String lastname) {
        Lastname = lastname;
        return this;
    }

    public String getCountry() {
        return Country;
    }

    public Crime setCountry(String country) {
        Country = country;
        return this;
    }

    public Date getBirthday() {
        return Birthday;
    }

    public Crime setBirthday(Date birthday) {
        this.Birthday = birthday;
        return this;
    }

    public int getHeight() {
        return Height;
    }

    public Crime setHeight(int height) {
        Height = height;
        return this;
    }

    public String getNationality() {
        return Nationality;
    }

    public Crime setNationality(String nationality) {
        Nationality = nationality;
        return this;
    }

    public String getAlias() {
        return Alias;
    }

    public Crime setAlias(String alias) {
        Alias = alias;
        return this;
    }

    public String getAbout() {
        return About;
    }

    public Crime setAbout(String about) {
        About = about;
        return this;
    }

    public Crime setHeight(Integer height) {
        Height = height;
        return this;
    }

    public String getSex() {
        return Sex;
    }

    public Crime setSex(String sex) {
        Sex = sex;
        return this;
    }

    public String getEye() {
        return Eye;
    }

    public Crime setEye(String eye) {
        Eye = eye;
        return this;
    }

    public String getHair() {
        return Hair;
    }

    public Crime setHair(String hair) {
        Hair = hair;
        return this;
    }

    public String getWantedBy() {
        return WantedBy;
    }

    public Crime setWantedBy(String wantedBy) {
        WantedBy = wantedBy;
        return this;
    }

    public byte [] getPhoto() {return Photo;}

    public Crime setPhoto(byte [] bytes) {
        Photo = bytes;
        return this;
    }
}
