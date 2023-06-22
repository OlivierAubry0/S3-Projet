package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.AssoEtudiante;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssoMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.ASSO_ETUDIANTE (Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID) VALUES(#{Asso_EtudianteID}, #{Asso_Etudiante_Nom}, #{FaculteID})")
    void insertAsso(AssoEtudiante asso);

    @Select("SELECT * FROM BASE_DE_DONNE.les_associations")
    List<AssoEtudiante> getAllAsso();
}
