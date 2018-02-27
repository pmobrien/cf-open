package com.pmobrien.cfopen.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Athlete extends NeoEntity {

  @Index(unique = true)
  private String competitorId;
  private String competitorName;
  
  @Relationship(type = "SUBMITTED", direction = Relationship.OUTGOING)
  private List<Score> scores;
  
  @Relationship(type = "MEMBER_OF", direction = Relationship.OUTGOING)
  private Team team;

  public String getCompetitorId() {
    return competitorId;
  }

  public Athlete setCompetitorId(String competitorId) {
    this.competitorId = competitorId;
    return this;
  }

  public String getCompetitorName() {
    return competitorName;
  }

  public Athlete setCompetitorName(String competitorName) {
    this.competitorName = competitorName;
    return this;
  }

  public List<Score> getScores() {
    return scores;
  }

  public Athlete setScores(List<Score> scores) {
    this.scores = scores;
    return this;
  }

  public Team getTeam() {
    return team;
  }

  public Athlete setTeam(Team team) {
    this.team = team;
    return this;
  }
}
