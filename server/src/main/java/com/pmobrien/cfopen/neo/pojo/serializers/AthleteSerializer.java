package com.pmobrien.cfopen.neo.pojo.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Score;
import java.io.IOException;

public class AthleteSerializer extends StdSerializer<Athlete> {

  public AthleteSerializer() {
    this(null);
  }
  
  public AthleteSerializer(Class<Athlete> type) {
    super(type);
  }
  
  @Override
  public void serialize(Athlete athlete, JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeStartObject();
    
    generator.writeStringField("competitorId", athlete.getCompetitorId());
    generator.writeStringField("competitorName", athlete.getCompetitorName());
    
    if(athlete.getTeam() != null) {
      generator.writeObjectFieldStart("team");
      
      generator.writeNumberField("ordinal", athlete.getTeam().getOrdinal());
      generator.writeStringField("name", athlete.getTeam().getName());
    
      generator.writeEndObject();
    }
    
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
    
    generator.writeEndObject();
  }
}
