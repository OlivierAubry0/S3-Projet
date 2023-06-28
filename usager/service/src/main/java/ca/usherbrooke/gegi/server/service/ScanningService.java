@Path("/api/scanning")
public class ScanningService {
    @Inject
    JsonWebToken jwt;

    @Inject
    ScanningMapper scanningMapper;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkEventExistence(EnterEventID enterEventID) {
        String evenementID = Scanning.getEvenementID();
        List<Scanning> events = scanningMapper.checkEventExistence(evenementID);
    }
}