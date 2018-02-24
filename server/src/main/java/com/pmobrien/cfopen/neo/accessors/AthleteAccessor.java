package com.pmobrien.cfopen.neo.accessors;

import com.google.common.collect.ImmutableMap;
import com.pmobrien.cfopen.neo.Sessions;
import com.pmobrien.cfopen.neo.pojo.Athlete;

public class AthleteAccessor {

  public Athlete getAthleteByCompetitorId(String competitorId) {
    return Sessions.returningSessionOperation(session -> {
      return session.queryForObject(
          Athlete.class,
          "MATCH (athlete:Athlete { competitorId: {competitorId} }) RETURN athlete",
          ImmutableMap.<String, String>builder()
              .put("competitorId", competitorId)
              .build()
      );
    });
  }
  
  public Athlete createOrUpdateAthlete(Athlete athlete) {
    Athlete dbAthlete = new AthleteAccessor().getAthleteByCompetitorId(athlete.getCompetitorId());
      
    if(dbAthlete == null) {
      Sessions.sessionOperation(session -> session.save(athlete));
    } else {
      Sessions.sessionOperation(session -> session.save(dbAthlete.setScores(athlete.getScores())));
    }
    
    return getAthleteByCompetitorId(athlete.getCompetitorId());
  }
}
