package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReservationMapper {


    @Insert("INSERT INTO BASE_DE_DONNE.RESERVATION (EvenementID,UsagerID,Telephone_Invite,Nom_Invite) VALUES(#{EvenementID}, #{UsagerID}, #{Telephone_Invite}, #{Nom_Invite})")
    void insertReservation(Reservation reservation);

}