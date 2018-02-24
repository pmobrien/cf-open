package com.pmobrien.cfopen.webservices.impl;

import com.pmobrien.cfopen.neo.accessors.AthleteAccessor;
import com.pmobrien.cfopen.webservices.IAthletesWebService;
import javax.ws.rs.core.Response;

public class AthletesWebService implements IAthletesWebService {

  @Override
  public Response getAthletes() {
    return Response.ok(new AthleteAccessor().getAllAthletes()).build();
  }
}
