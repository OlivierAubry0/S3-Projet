package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.AssoEtudiante;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssoMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.ASSO_ETUDIANTE (Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID, UsagerID) VALUES(#{Asso_EtudianteID}, #{Asso_Etudiante_Nom}, #{FaculteID}, #{UsagerID})")
    void insertAsso(AssoEtudiante asso);

    @Delete("DELETE FROM BASE_DE_DONNE.ASSO_ETUDIANTE WHERE Asso_EtudianteID = #{Asso_EtudianteID}")
    void deleteAsso(@Param("Asso_EtudianteID") String Asso_EtudianteID);

    @Select("SELECT * FROM BASE_DE_DONNE.les_associations")
    List<AssoEtudiante> getAllAsso();

    @Update("UPDATE BASE_DE_DONNE.ASSO_ETUDIANTE SET Asso_Etudiante_Nom = #{asso.Asso_Etudiante_Nom}, FaculteID = #{asso.FaculteID}, UsagerID = #{asso.UsagerID} WHERE Asso_EtudianteID = #{asso.Asso_EtudianteID}")
    void update_the_Asso(@Param("asso") AssoEtudiante asso);

    @Select("SELECT * FROM BASE_DE_DONNE.ASSO_ETUDIANTE WHERE Asso_EtudianteID = #{Asso_EtudianteID}")
    AssoEtudiante getAssoById(@Param("Asso_EtudianteID") String Asso_EtudianteID);

}

