package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.University;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UniversityMapper {
    @Select("SELECT * FROM BASE_DE_DONNE.UNIVERSITE")
    List<University> getAllUniversitiesNames();
}
