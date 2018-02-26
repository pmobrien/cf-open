package com.pmobrien.cfopen.neo.accessors;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.pmobrien.cfopen.neo.Sessions;
import com.pmobrien.cfopen.neo.pojo.Team;
import java.util.List;

public class TeamAccessor {
  
  public List<Team> getAllTeams() {
    return Sessions.returningSessionOperation(session -> Lists.newArrayList(session.loadAll(Team.class)));
  }
  
  public Team getTeam(Long id) {
    return Sessions.returningSessionOperation(session -> session.load(Team.class, id));
  }

  public Team getTeamByName(String name) {
    return Sessions.returningSessionOperation(session -> {
      return session.queryForObject(
          Team.class,
          "MATCH (team:Team { name: {name} }) RETURN team",
          ImmutableMap.<String, String>builder()
              .put("name", name)
              .build()
      );
    });
  }
  
  public Team createOrUpdateTeam(Team team) {
    Sessions.sessionOperation(session -> session.save(team));
    
    return getTeamByName(team.getName());
  }
}
