package com.pmobrien.cfopen.webservices.pojo;

import com.pmobrien.cfopen.neo.pojo.Athlete;
import java.util.List;

public class UpdateAthletes {

  private List<Athlete> athletes;

  public List<Athlete> getAthletes() {
    return athletes;
  }

  public void setAthletes(List<Athlete> athletes) {
    this.athletes = athletes;
  }
}
