package org.app.proxy;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.app.model.TvSerial;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/singlesearch")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface TvSerialProxy {

    @GET
    @Path("/shows")
    TvSerial get(@QueryParam("q") String title);
}
