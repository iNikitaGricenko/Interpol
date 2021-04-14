package bin.data.Models;

import java.sql.Date;
import java.util.List;

public class Grouping extends Model{
    private String Name = "";
    private String Specification = "";
    private String Location = "";
    private String AboutGrouping = "";
    private Date CreationDate;
    private String Country = "";
    private String DangerLvl = "";
    private int Member;
    private String WantedBy = "";

    private List<Crime> crimes;

    public List<Crime> getCrimes() {
        return crimes;
    }

    public void setCrimes(List<Crime> crimes) {
        this.crimes = crimes;
    }

    public String getWantedBy() {
        return WantedBy;
    }

    public Grouping setWantedBy(String wantedBy) {
        WantedBy = wantedBy;
        return this;
    }

    public int getMember() {
        return Member;
    }

    public Grouping setMember(int member) {
        Member = member;
        return this;
    }


    public Grouping() {
        super();
    }

    public Grouping(Long id) {
        super(id);
    }

    public String getName() {
        return Name;
    }

    public Grouping setName(String name) {
        Name = name;
        return this;
    }

    public String getSpecification() {
        return Specification;
    }

    public Grouping setSpecification(String specification) {
        Specification = specification;
        return this;
    }

    public String getCountry() {
        return Country;
    }

    public Grouping setCountry(String country) {
        Country = country;
        return this;
    }

    public Date getCreationDate() {
        return CreationDate;
    }

    public Grouping setCreationDate(Date creationDate) {
        CreationDate = creationDate;
        return this;
    }

    public String getLocation() {
        return Location;
    }

    public Grouping setLocation(String location) {
        Location = location;
        return this;
    }

    public String getAboutGrouping() {
        return AboutGrouping;
    }

    public Grouping setAboutGrouping(String aboutGrouping) {
        AboutGrouping = aboutGrouping;
        return this;
    }

    public String getDangerLvl() {
        return DangerLvl;
    }

    public Grouping setDangerLvl(String dangerLvl) {
        DangerLvl = dangerLvl;
        return this;
    }
}
