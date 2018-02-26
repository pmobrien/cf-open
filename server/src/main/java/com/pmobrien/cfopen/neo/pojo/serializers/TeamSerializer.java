package com.pmobrien.cfopen.neo.pojo.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Score;
import com.pmobrien.cfopen.neo.pojo.Team;
import java.io.IOException;

public class TeamSerializer extends StdSerializer<Team> {

  public TeamSerializer() {
    this(null);
  }
  
  public TeamSerializer(Class<Team> type) {
    super(type);
  }

  @Override
  public void serialize(Team team, JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeStartObject();
    
    generator.writeNumberField("ordinal", team.getOrdinal());
    generator.writeStringField("name", team.getName());
    
    if(team.getAthletes() != null) {
      generator.writeArrayFieldStart("athletes");
      
      for(Athlete athlete : team.getAthletes()) {
        generator.writeStartObject();
        
        generator.writeStringField("competitorId", athlete.getCompetitorId());
        generator.writeStringField("competitorName", athlete.getCompetitorName());

        if(athlete.getScores() != null) {
          generator.writeArrayFieldStart("scores");

          for(Score score : athlete.getScores()) {
            generator.writeStartObject();

            generator.writeNumberField("ordinal", score.getOrdinal());
            generator.writeNumberField("rank", score.getRank());
            generator.writeStringField("score", score.getScore());
            generator.writeStringField("scoreDisplay", score.getScoreDisplay());

            generator.writeEndObject();
          }

          generator.writeEndArray();
        }
        
        generator.writeEndObject();
      }
      
      generator.writeEndArray();
    }
    
    generator.writeEndObject();
  }
}
