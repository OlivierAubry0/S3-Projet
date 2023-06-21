package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM BASE_DE_DONNE.USAGER WHERE UsagerID = #{usagerID}")
    Person selectUserByUsagerID(String usagerID);

    @Insert("INSERT INTO BASE_DE_DONNE.USAGER (UsagerID, Usager_Nom, Usager_Prenom, Usager_Role, FaculteID) VALUES (#{cip}, #{last_name}, #{first_name}, #{roles},  #{faculteID})")
    void insertUser(Person user);
}
