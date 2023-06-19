package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Faculty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface FacultyMapper {
    @Select("SELECT Faculte_Nom FROM BASE_DE_DONNE.FACULTE WHERE UniversiteID = #{universiteID}")
    List<String> getFacultiesByUniversity(int universiteID);
}
