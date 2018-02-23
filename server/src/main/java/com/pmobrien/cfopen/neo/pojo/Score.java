package com.pmobrien.cfopen.neo.pojo;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Score extends NeoEntity {

  @Relationship(type = "SUBMITTED", direction = Relationship.INCOMING)
  private Athlete athlete;
  
  private Integer ordinal;
  private Integer rank;
  private String score;
  private String scoreDisplay;

  public Athlete getAthlete() {
    return athlete;
  }

  public Score setAthlete(Athlete athlete) {
    this.athlete = athlete;
    return this;
  }

  public Integer getOrdinal() {
    return ordinal;
  }

  public Score setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
    return this;
  }

  public Integer getRank() {
    return rank;
  }

  public Score setRank(Integer rank) {
    this.rank = rank;
    return this;
  }

  public String getScore() {
    return score;
  }

  public Score setScore(String score) {
    this.score = score;
    return this;
  }

  public String getScoreDisplay() {
    return scoreDisplay;
  }

  public Score setScoreDisplay(String scoreDisplay) {
    this.scoreDisplay = scoreDisplay;
    return this;
  }
}
