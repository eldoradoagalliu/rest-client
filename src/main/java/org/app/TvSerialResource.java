package org.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.app.model.Episode;
import org.app.model.TvSerial;
import org.app.proxy.EpisodeProxy;
import org.app.proxy.TvSerialProxy;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@Path("/tv/serials")
public class TvSerialResource {

    @RestClient
    TvSerialProxy tvSerialProxy;

    @RestClient
    EpisodeProxy episodeProxy;

    private final List<TvSerial> tvSerials = new ArrayList<>();

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTvSerialInformation(@QueryParam("title") String title) {
        TvSerial tvSerial = getTvSerial(title);
        tvSerials.add(tvSerial);
        return Response.ok(tvSerials).build();
    }

    /**
     * A method that will handle the api failure of TvSerialProxy
     */
    @Fallback(fallbackMethod = "fallbackGetTvSerialInformation")
    public TvSerial getTvSerial(String title) {
        return tvSerialProxy.get(title);
    }

    private TvSerial fallbackGetTvSerialInformation(String title) {
        TvSerial tvSerial = new TvSerial();
        tvSerial.setId(1L);
        return tvSerial;
    }


    @GET
    @Path("/episodes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEpisodes(@QueryParam("title") String title) {
        TvSerial tvSerial = tvSerialProxy.get(title);
        List<Episode> episodes = getAllEpisodes(tvSerial.getId());
        return Response.ok(episodes).build();
    }

    /**
     * A method that will handle the api failure of EpisodeProxy
     */
    @Fallback(fallbackMethod = "fallbackGetEpisodes")
    public List<Episode> getAllEpisodes(Long id) {
        return episodeProxy.get(id);
    }

    public List<Episode> fallbackGetEpisodes(Long id) {
        return new ArrayList<>();
    }
}
