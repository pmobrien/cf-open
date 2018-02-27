package com.pmobrien.cfopen.webservices.impl;

import com.pmobrien.cfopen.Application;
import com.pmobrien.cfopen.exceptions.ValidationException;
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
  public Response updateAthletes(String password, UpdateAthletes update) {
    if(!Application.getProperties().getData().getTeams().getUpdatePassword().equals(password)) {
      throw new ValidationException("Password is incorrect.");
    }
    
    for(Athlete athlete : update.getAthletes()) {
      new AthleteAccessor().updateTeam(athlete, athlete.getTeam());
    }
    
    return Response.ok().build();
  }
}
