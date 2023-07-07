package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.admin.CheckMyEvents;
import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.admin.Scanning;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import java.util.List;

@Mapper
public interface EventMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.EVENEMENT (EvenementID, Evenement_Nom, Evenement_Date, Evenement_Debut, Evenement_Fin, Asso_EtudianteID, Nombre_Places, Allow_Guests, Description) VALUES(#{evenementID}, #{evenementNom}, #{evenementDate}, #{evenementDebut}, #{evenementFin}, #{assoEtudianteID}, #{nombrePlaces}, #{allowGuests}, #{description})")
    void insertEvent(Event event);

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes")
    List<Event> getAllEvents();

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes WHERE Asso_EtudianteID = #{Asso_EtudianteID}")
    List<Event> getEvents(@QueryParam("Asso_EtudianteID") String Asso_EtudianteID);

    @Select("SELECT EvenementID, Evenement_Nom, Evenement_Date, Nom_Invite, Enregistration_Invite FROM BASE_DE_DONNE.MyEvents WHERE UsagerID = #{UsagerID}")
    List<CheckMyEvents> CheckMyEvents(@Param("UsagerID") String UsagerID);


}

