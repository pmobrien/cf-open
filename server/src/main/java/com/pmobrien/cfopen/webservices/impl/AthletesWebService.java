package com.pmobrien.cfopen.webservices.impl;

import com.pmobrien.cfopen.neo.accessors.AthleteAccessor;
import com.pmobrien.cfopen.neo.accessors.TeamAccessor;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Team;
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
      Athlete foundAthlete = new AthleteAccessor().getAthleteByCompetitorId(athlete.getCompetitorId());
      if(foundAthlete != null) {
        Team team = athlete.getTeam() == null
            ? null
            : new TeamAccessor().getTeam(athlete.getTeam().getId());
        
        new AthleteAccessor().createOrUpdateAthlete(foundAthlete.setTeam(team));
      }
    }
    
    return Response.ok().build();
  }
}
