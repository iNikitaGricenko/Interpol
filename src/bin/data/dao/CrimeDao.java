package bin.data.dao;

import bin.data.Models.Crime;

import java.sql.Date;

public interface CrimeDao extends ItemDao<Crime>{
    CrimeDao addPrimaryInfo(String LastName, String ForeName, String Nationality);
    CrimeDao getByPrimaryInfo(Crime model);
    CrimeDao addSecondaryInfo(String Alias, String AboutPerson, Date BirthDay, String CityOfBirth, Integer Height, String Sex, String Eye, String Hair, String WantedBy);

    Crime build();
}
