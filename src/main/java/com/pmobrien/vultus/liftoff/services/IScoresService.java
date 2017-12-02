package com.pmobrien.vultus.liftoff.services;

import com.pmobrien.vultus.liftoff.neo.pojo.ScoreNode;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/scores")
public interface IScoresService {

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response addScore(ScoreNode score);
}
