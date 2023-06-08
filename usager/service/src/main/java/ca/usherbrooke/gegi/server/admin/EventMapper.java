package ca.usherbrooke.gegi.server.admin;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface EventMapper {
    @Insert("INSERT INTO EVENEMENT(Evenement_Nom, Evenement_Date, Evenement__Debut, Evenement_Fin, Asso_EtudianteID) VALUES(#{eventName}, #{eventDate}, #{eventStart}, #{eventEnd}, #{studentAssociationId})")
    void insertEvent(Event event);
}
