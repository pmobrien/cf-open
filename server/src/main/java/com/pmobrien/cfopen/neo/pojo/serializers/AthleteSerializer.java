package com.pmobrien.cfopen.neo.pojo.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pmobrien.cfopen.neo.pojo.Athlete;
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
    
    generator.writeEndObject();
  }
}
