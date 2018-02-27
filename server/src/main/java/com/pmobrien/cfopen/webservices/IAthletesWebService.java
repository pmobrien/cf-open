package com.pmobrien.cfopen.webservices;

import com.pmobrien.cfopen.webservices.pojo.UpdateAthletes;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/athletes")
public interface IAthletesWebService {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAthletes();
  
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateAthletes(@QueryParam("password") String password, UpdateAthletes update);
}
