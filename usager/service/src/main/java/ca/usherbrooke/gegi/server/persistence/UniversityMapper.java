package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.University;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UniversityMapper {
    @Select("SELECT Universite_Nom FROM BASE_DE_DONNE.UNIVERSITE")
    List<String> getAllUniversitiesNames();
}
