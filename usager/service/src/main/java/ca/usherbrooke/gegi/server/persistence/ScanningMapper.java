public interface ScanningMapper {
    @Select("SELECT COUNT(*) FROM BASE_DE_DONNE.evenement_programmes WHERE EvenementID = #{evenementID}")
    List<Scanning> checkEventExistence(@Param("evenementID") String evenementID);
}