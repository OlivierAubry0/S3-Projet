package ca.usherbrooke.gegi.server.persistence;

import ca.usherbrooke.gegi.server.admin.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.images (filename) VALUES (#{filename})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertImage(Image image);

    @Select("SELECT filename FROM BASE_DE_DONNE.images")
    List<String> getAllImageNames();
}
