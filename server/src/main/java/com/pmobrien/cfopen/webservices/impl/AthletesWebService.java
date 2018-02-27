package com.pmobrien.cfopen.webservices.impl;

import com.pmobrien.cfopen.neo.accessors.AthleteAccessor;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.webservices.IAthletesWebService;
import com.pmobrien.cfopen.webservices.pojo.UpdateAthletes;
import javax.ws.rs.core.Response;

public class AthletesWebService implements IAthletesWebService {

  @Override
  public Response getAthletes() {
    return Response.ok(new AthleteAccessor().getAllAthletes()).build();
  }

  @Override
  public Response updateAthletes(UpdateAthletes update) {
    for(Athlete athlete : update.getAthletes()) {
      new AthleteAccessor().updateTeam(athlete, athlete.getTeam());
    }
    
    return Response.ok().build();
  }
}
