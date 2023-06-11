package ca.usherbrooke.gegi.server.persistence;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.InputStream;
import java.sql.Blob;
import java.util.UUID;

@Mapper
public interface ImageMapper {

    @Insert("INSERT INTO BASE_DE_DONNE.images (id, data) VALUES (#{imageId}, #{imageData, jdbcType=BLOB})")
    void insertImage(@Param("imageId") String imageId, @Param("imageData") Blob imageData);

}
