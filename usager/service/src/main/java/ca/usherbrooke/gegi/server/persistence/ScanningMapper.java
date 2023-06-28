package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Scanning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScanningMapper {
    @Select("SELECT COUNT(*) FROM BASE_DE_DONNE.evenement_programmes WHERE EvenementID = #{evenementID}")
    List<Scanning> checkEventExistence(@Param("evenementID") String evenementID);
}