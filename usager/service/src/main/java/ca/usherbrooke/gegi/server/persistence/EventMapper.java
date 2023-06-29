package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Event;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes")
    List<Event> getAllEventsProg();

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes WHERE Asso_EtudianteID = 'AGEG'")
    List<Event> getEventsGenie();

}

