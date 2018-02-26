package com.pmobrien.cfopen.webservices.impl;

import com.pmobrien.cfopen.neo.accessors.TeamAccessor;
import com.pmobrien.cfopen.webservices.ITeamsWebService;
import javax.ws.rs.core.Response;

public class TeamsWebService implements ITeamsWebService {

  @Override
  public Response getTeams() {
    return Response.ok(new TeamAccessor().getAllTeams()).build();
  }
}
