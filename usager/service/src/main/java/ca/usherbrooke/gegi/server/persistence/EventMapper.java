package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.CheckIfUserReserved;
import ca.usherbrooke.gegi.server.admin.CheckMyEvents;
import ca.usherbrooke.gegi.server.admin.Event;
import ca.usherbrooke.gegi.server.admin.EventShowedToStudents;
import org.apache.ibatis.annotations.*;

import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import java.util.List;

@Mapper
public interface EventMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.EVENEMENT (EvenementID, Evenement_Nom, Evenement_Date, Evenement_Debut, Evenement_Fin, Asso_EtudianteID, Nombre_Places, Allow_Guests, Description, filename) VALUES(#{evenementID}, #{evenementNom}, #{evenementDate}, #{evenementDebut}, #{evenementFin}, #{assoEtudianteID}, #{nombrePlaces}, #{allowGuests}, #{description}, #{filename})")
    void insertEvent(Event event);

    @Select("SELECT * FROM BASE_DE_DONNE.EVENEMENT")
    @Results({
            @Result(property = "evenementID", column = "EvenementID"),
            @Result(property = "evenementNom", column = "Evenement_Nom"),
            @Result(property = "evenementDate", column = "Evenement_Date"),
            @Result(property = "evenementDebut", column = "Evenement_Debut"),
            @Result(property = "evenementFin", column = "Evenement_Fin"),
            @Result(property = "assoEtudianteID", column = "Asso_EtudianteID"),
            @Result(property = "nombrePlaces", column = "Nombre_Places"),
            @Result(property = "allowGuests", column = "Allow_Guests"),
            @Result(property = "description", column = "Description"),
            @Result(property = "filename", column = "filename")
    })
    List<Event> getAllEvents();

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes_per_fac WHERE FaculteID = #{FaculteID}")
    List<EventShowedToStudents> getEvents(@Param("FaculteID") String FaculteID);
    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes_per_fac WHERE EvenementID = #{EvenementID}")
    List<EventShowedToStudents> getEventUrReserving4(@Param("EvenementID") String EvenementID);
    @Select("SELECT EvenementID, Evenement_Nom, Evenement_Date, Nom_Invite, Enregistration_Invite FROM BASE_DE_DONNE.MyEvents WHERE UsagerID = #{UsagerID}")
    List<CheckMyEvents> CheckMyEvents(@Param("UsagerID") String UsagerID);
    @Update("UPDATE BASE_DE_DONNE.EVENEMENT SET " +
            "Evenement_Nom = #{Evenement_Nom}, Evenement_Date = #{Evenement_Date}, Evenement_Debut = #{Evenement_Debut}," +
            " Evenement_Fin = #{Evenement_Fin}, Nombre_Places = #{Nombre_Places}, Allow_Guests = #{Allow_Guests}," +
            "Description = #{Description}, filename = #{filename} " +
            " WHERE EvenementID = #{EvenementID}")
    void update_the_Event(@Param("EvenementID") String EvenementID, @Param("Evenement_Nom") String Evenement_Nom,
                         @Param("Evenement_Date") String Evenement_Date, @Param("Evenement_Debut") String Evenement_Debut,
                         @Param("Evenement_Fin") String Evenement_Fin, @Param("Nombre_Places") int Nombre_Places,
                         @Param("Allow_Guests") boolean Allow_Guests, @Param("Description") String Description,
                         @Param("filename") String filename);
    @Delete("DELETE FROM BASE_DE_DONNE.EVENEMENT WHERE EvenementID = #{EvenementID}")
    void deleteEvent(@Param("EvenementID") String EvenementID);

}

