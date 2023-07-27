package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Reservation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReservationMapper {


    @Insert("INSERT INTO BASE_DE_DONNE.RESERVATION (EvenementID,UsagerID,Telephone_Invite,Nom_Invite,Enregistration_Invite) VALUES(#{EvenementID}, #{UsagerID}, #{Telephone_Invite}, #{Nom_Invite}, #{Enregistration_Invite})")
    void insertReservation(Reservation reservation);

    @Delete("DELETE FROM BASE_DE_DONNE.RESERVATION WHERE EvenementID = #{EvenementID} AND UsagerID = #{UsagerID}")
    void deleteReservation(@Param("EvenementID") String EvenementID, @Param("UsagerID") String UsagerID);

    @Update("UPDATE BASE_DE_DONNE.RESERVATION SET UsagerID = #{NewUsagerID} WHERE EvenementID = #{EvenementID} AND UsagerID = #{UsagerID}")
    void updateReservation(@Param("NewUsagerID") String NewUsagerID, @Param("EvenementID") String EvenementID, @Param("UsagerID") String UsagerID);

    @Select("SELECT * FROM BASE_DE_DONNE.RESERVATION WHERE EvenementID = #{EvenementID}")
    List<Reservation> getReservationsByEventID(@Param("EvenementID") String EvenementID);

    @Delete("DELETE FROM BASE_DE_DONNE.RESERVATION WHERE EvenementID = #{EvenementID}")
    void deleteReservationV2(@Param("EvenementID") String ReservationID);

    @Select("SELECT * FROM BASE_DE_DONNE.RESERVATION WHERE EvenementID = #{EvenementID}")
    Reservation getReservationByID(@Param("EvenementID") String ReservationID);

}
