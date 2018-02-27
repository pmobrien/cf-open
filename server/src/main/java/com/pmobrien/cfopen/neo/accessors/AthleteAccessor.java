package com.pmobrien.cfopen.neo.accessors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pmobrien.cfopen.neo.Sessions;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Team;
import java.util.List;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;

public class AthleteAccessor {
  
  public List<Athlete> getAllAthletes() {
    return Sessions.returningSessionOperation(session -> Lists.newArrayList(session.loadAll(Athlete.class)));
  }

  public Athlete getAthleteByCompetitorId(String competitorId) {
    return Sessions.returningSessionOperation(session -> {
      return session.queryForObject(
          Athlete.class,
          "MATCH (athlete:Athlete { competitorId: {competitorId} }) " +
          "OPTIONAL MATCH (team:Team)<-[member:MEMBER_OF]-(athlete) " +
          "RETURN athlete, member, team",
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
      Sessions.sessionOperation(session -> {
        if(athlete.getScores() != null) {
          dbAthlete.setScores(athlete.getScores());
        }
        
        dbAthlete.setTeam(athlete.getTeam());
        
        session.save(dbAthlete);
      });
    }
    
    return getAthleteByCompetitorId(athlete.getCompetitorId());
  }
  
  public Athlete updateTeam(Athlete athlete, Team team) {
    Athlete dbAthlete = getAthleteByCompetitorId(athlete.getCompetitorId());
    
    if(dbAthlete != null) {
      if(dbAthlete.getTeam() != null) {
        if(team != null && dbAthlete.getTeam().getOrdinal().equals(team.getOrdinal())) {
          return dbAthlete;
        } else {
          Team oldTeam = new TeamAccessor().getTeamByOrdinal(dbAthlete.getTeam().getOrdinal());

          Sessions.sessionOperation(session -> {
            Team loadedTeam = session.load(Team.class, oldTeam.getId());
            loadedTeam.getAthletes().removeIf(a -> a.getCompetitorId().equals(athlete.getCompetitorId()));

            session.save(loadedTeam);
          });
        }
      }
    
      Sessions.sessionOperation(session -> {
        Team dbTeam = team == null
            ? null
            : session.loadAll(Team.class, new Filter("ordinal", ComparisonOperator.EQUALS, team.getOrdinal()))
                .stream()
                .findFirst()
                .orElse(null);
            
        if(dbTeam != null) {
          if(dbTeam.getAthletes() == null) {
            dbTeam.setAthletes(Lists.newArrayList());
          }

          dbTeam.getAthletes().add(dbAthlete);
          
          session.save(dbAthlete.setTeam(dbTeam));
        } else {
          session.save(dbAthlete.setTeam(null));
        }
      });
    }
    
    return dbAthlete;
  }
}
