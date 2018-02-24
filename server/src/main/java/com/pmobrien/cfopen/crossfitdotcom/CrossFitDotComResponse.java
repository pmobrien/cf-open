package com.pmobrien.cfopen.crossfitdotcom;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CrossFitDotComResponse {

  private List<Row> leaderboardRows;

  public List<Row> getLeaderboardRows() {
    return leaderboardRows;
  }

  public void setLeaderboardRows(List<Row> leaderboardRows) {
    this.leaderboardRows = leaderboardRows;
  }
  
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Row {
    
    private Entrant entrant;
    private List<Score> scores;

    public Entrant getEntrant() {
      return entrant;
    }

    public void setEntrant(Entrant entrant) {
      this.entrant = entrant;
    }

    public List<Score> getScores() {
      return scores;
    }

    public void setScores(List<Score> scores) {
      this.scores = scores;
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Entrant {
      
      private String competitorId;
      private String competitorName;

      public String getCompetitorId() {
        return competitorId;
      }

      public void setCompetitorId(String competitorId) {
        this.competitorId = competitorId;
      }

      public String getCompetitorName() {
        return competitorName;
      }

      public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
      }
    }
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Score {
      
      private int ordinal;
      private String rank;
      private String score;
      private String scoreDisplay;
      private String scaled;

      public int getOrdinal() {
        return ordinal;
      }

      public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
      }

      public String getRank() {
        return rank;
      }

      public void setRank(String rank) {
        this.rank = rank;
      }

      public String getScore() {
        return score;
      }

      public void setScore(String score) {
        this.score = score;
      }

      public String getScoreDisplay() {
        return scoreDisplay;
      }

      public void setScoreDisplay(String scoreDisplay) {
        this.scoreDisplay = scoreDisplay;
      }

      public String getScaled() {
        return scaled;
      }

      public void setScaled(String scaled) {
        this.scaled = scaled;
      }
    }
  }
  
  public List<Athlete> toAthleteList() {
    List<Athlete> athletes = Lists.newArrayListWithExpectedSize(leaderboardRows.size());
    
    for(Row row : leaderboardRows) {
      List<com.pmobrien.cfopen.neo.pojo.Score> scores = Lists.newArrayListWithExpectedSize(row.scores.size());
      
      for(Row.Score score : row.scores) {
        scores.add(
            new com.pmobrien.cfopen.neo.pojo.Score()
                .setOrdinal(score.ordinal)
                .setRank(Integer.parseInt(score.rank))
                .setScore(score.score)
                .setScoreDisplay(score.scoreDisplay)
        );
      }
      
      athletes.add(
          new Athlete()
              .setCompetitorId(row.entrant.competitorId)
              .setCompetitorName(row.entrant.competitorName)
              .setScores(scores)
      );
    }
    
    return athletes;
  }
}
