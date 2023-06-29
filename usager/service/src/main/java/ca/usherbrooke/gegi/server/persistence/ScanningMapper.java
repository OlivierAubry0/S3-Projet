package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ScanningMapper {
    @Select("SELECT COUNT(*) FROM evenement_programmes WHERE EvenementID = #{EvenementID}")
    int checkEventExistence(@Param("EvenementID") String EvenementID);

    @Select("SELECT UsagerID, Telephone_Invite,Nom_Invite, Enregistration_Invite FROM places_reserves WHERE EvenementID = #{EvenementID} AND UsagerID = #{UsagerID}")
    List<CheckIfUserReserved> CheckUserReservation(@Param("EvenementID") String EvenementID, @Param("UsagerID") String UsagerID);
}