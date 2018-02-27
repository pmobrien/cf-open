package com.pmobrien.cfopen.neo.pojo;

import java.util.List;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Team extends NeoEntity {

  @Index(unique = true)
  private Integer ordinal;
  private String name;
  
  @Relationship(type = "MEMBER_OF", direction = Relationship.INCOMING)
  private List<Athlete> athletes;

  public Integer getOrdinal() {
    return ordinal;
  }

  public Team setOrdinal(Integer ordinal) {
    this.ordinal = ordinal;
    return this;
  }
  
  public String getName() {
    return name;
  }

  public Team setName(String name) {
    this.name = name;
    return this;
  }

  public List<Athlete> getAthletes() {
    return athletes;
  }

  public Team setAthletes(List<Athlete> athletes) {
    this.athletes = athletes;
    return this;
  }
}
