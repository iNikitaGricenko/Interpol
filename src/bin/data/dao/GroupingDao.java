package bin.data.dao;

import bin.data.Models.Crime;
import bin.data.Models.Grouping;

import java.sql.Date;
import java.util.List;

public interface GroupingDao extends ItemDao<Grouping>{
    List<Crime> getCrimes(Grouping model);

    GroupingDao addPrimaryInfo(String Name, String Specification, String Location);
    GroupingDao getByPrimaryInfo(Grouping model);
    GroupingDao addSecondaryInfo(String AboutGrouping, Date CreationDate, String Country, String DangerLvl);

    Grouping build();
}
