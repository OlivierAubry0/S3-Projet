package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.AssoEtudiante;
import ca.usherbrooke.gegi.server.admin.FaculteData;
import org.apache.ibatis.annotations.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@Mapper
public interface AssoMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.ASSO_ETUDIANTE (Asso_EtudianteID, Asso_Etudiante_Nom, FaculteID, UsagerID) VALUES(#{Asso_EtudianteID}, #{Asso_Etudiante_Nom}, #{FaculteID}, #{UsagerID})")
    void insertAsso(AssoEtudiante asso);

    @Delete("DELETE FROM BASE_DE_DONNE.ASSO_ETUDIANTE WHERE Asso_Etudiante_Nom = #{Asso_Etudiante_Nom}")
    void deleteAsso(@Param("Asso_Etudiante_Nom") String Asso_Etudiante_Nom);

    @Select("SELECT * FROM BASE_DE_DONNE.les_associations")
    List<AssoEtudiante> getAllAsso();

    @Update("UPDATE BASE_DE_DONNE.ASSO_ETUDIANTE SET Asso_Etudiante_Nom = #{Asso_Etudiante_Nom}, FaculteID = #{FaculteID}, UsagerID = #{UsagerID} WHERE Asso_EtudianteID = #{Asso_EtudianteID}")
    void update_the_Asso(@Param("Asso_EtudianteID") String Asso_EtudianteID, @Param("FaculteID") String FaculteID, @Param("Asso_Etudiante_Nom") String Asso_Etudiante_Nom, @Param("UsagerID") String UsagerID);

    @Select("SELECT UsagerID FROM BASE_DE_DONNE.USAGER")
    List<String> getAllUsagerID();

    @Select("SELECT Faculte_Nom as name, FaculteID as id FROM BASE_DE_DONNE.FACULTE")
    List<FaculteData> getAllFaculteData();

}

