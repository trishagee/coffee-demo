import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

import static java.lang.String.valueOf;

@Path("/example")
@Produces(MediaType.APPLICATION_JSON)
public class ExampleWebResource {
    private Datastore datastore;

    public ExampleWebResource(final MongoClient mongoClient) {
        datastore = new Morphia().createDatastore(mongoClient, "TheDatabaseName");
    }

    @Path("save")
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSomething(SomePojo yourObject) {
        datastore.save(yourObject);

        return Response.created(URI.create(valueOf(yourObject.getId())))
                       .entity(yourObject)
                       .build();
    }

}
