package com.pmobrien.cfopen.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Athlete extends NeoEntity {

  private String competitorId;
  private String competitorName;
  
  @Relationship(type = "SUBMITTED", direction = Relationship.OUTGOING)
  private List<Score> scores;

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
}
