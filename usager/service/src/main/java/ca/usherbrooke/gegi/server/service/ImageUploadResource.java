package ca.usherbrooke.gegi.server.service;

import ca.usherbrooke.gegi.server.admin.Image;
import ca.usherbrooke.gegi.server.persistence.ImageMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@javax.ws.rs.Path("/api/images")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImageUploadResource {

    @ConfigProperty(name = "upload.directory") // Specify the directory to store the images
    String uploadDirectory;

    private final ImageMapper imageMapper;

    @Inject
    public ImageUploadResource(ImageMapper imageMapper) {
        this.imageMapper = imageMapper;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formDataMap = input.getFormDataMap();

        try {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            Path filePath = Path.of(uploadDirectory, fileName);

            // Get the input part for the image
            List<InputPart> imageParts = formDataMap.get("image");
            if (imageParts != null && !imageParts.isEmpty()) {
                InputPart imagePart = imageParts.get(0);
                // Save the image file
                imagePart.getBody(InputStream.class, null).transferTo(Files.newOutputStream(filePath));

                // Extract the filename from the filePath
                Path filenamePath = filePath.getFileName();
                String filename = filenamePath.toString();
                Image image = new Image();
                image.setFilename(filename);
                imageMapper.insertImage(image);

                return Response.ok("Image uploaded successfully!").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("No image found in the request").build();
            }
        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error uploading image").build();
        }
    }

    @javax.ws.rs.Path("/GetNames")
    @GET
    public List<String> getAllImageNames() {
        return imageMapper.getAllImageNames();
    }

    @GET
    public List<Image> getAllImages() {
        List<String> imageNames = imageMapper.getAllImageNames();
        List<Image> images = new ArrayList<>();

        for (String imageName : imageNames) {
            String imageUrl = "http://localhost:8080/" + imageName;

            // Create a new Image object with the filename and path
            Image image = new Image();
            image.setFilename(imageName);
            image.setPath(imageUrl);

            // Add the image to the list
            images.add(image);
        }

        return images;
    }

}
