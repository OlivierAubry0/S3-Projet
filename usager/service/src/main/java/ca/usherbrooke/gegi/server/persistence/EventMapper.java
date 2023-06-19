package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.EVENEMENT (EvenementID, Evenement_Nom, Evenement_Date, Evenement_Debut, Evenement_Fin, Asso_EtudianteID, Nombre_Places, Allow_Guests, Description) VALUES(#{evenementID}, #{evenementNom}, #{evenementDate}, #{evenementDebut}, #{evenementFin}, #{assoEtudianteID}, #{nombrePlaces}, #{allowGuests}, #{description})")
    void insertEvent(Event event);

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes")
    List<Event> getAllEvents();

    @Select("SELECT * FROM BASE_DE_DONNE.evenement_programmes WHERE Asso_EtudianteID = 'AGEG'")
    List<Event> getEventsGenie();
}

