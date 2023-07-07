package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Reservation;
import org.apache.ibatis.annotations.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import java.util.List;

@Mapper
public interface ReservationMapper {


    @Insert("INSERT INTO BASE_DE_DONNE.RESERVATION (EvenementID,UsagerID,Telephone_Invite,Nom_Invite,Enregistration_Invite) VALUES(#{EvenementID}, #{UsagerID}, #{Telephone_Invite}, #{Nom_Invite}, #{Enregistration_Invite})")
    void insertReservation(Reservation reservation);

    @Delete("DELETE FROM BASE_DE_DONNE.RESERVATION WHERE EvenementID = #{EvenementID} AND UsagerID = #{UsagerID}")
    void deleteReservation(@Param("EvenementID") String EvenementID, @Param("UsagerID") String UsagerID);

    @Update("UPDATE BASE_DE_DONNE.RESERVATION SET UsagerID = #{NewUsagerID} WHERE EvenementID = #{EvenementID} AND UsagerID = #{UsagerID}")
    void updateReservation(@Param("NewUsagerID") String NewUsagerID, @Param("EvenementID") String EvenementID, @Param("UsagerID") String UsagerID);

}
