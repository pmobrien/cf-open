package com.pmobrien.cfopen.mappers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pmobrien.cfopen.neo.pojo.Athlete;
import com.pmobrien.cfopen.neo.pojo.Team;
import com.pmobrien.cfopen.neo.pojo.serializers.AthleteSerializer;
import com.pmobrien.cfopen.neo.pojo.serializers.TeamSerializer;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class DefaultObjectMapper implements ContextResolver<ObjectMapper> {
  
  @Override
  public ObjectMapper getContext(Class<?> type) {
    return new ObjectMapper()
        .configure(SerializationFeature.INDENT_OUTPUT, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(
            new SimpleModule()
                .addSerializer(Athlete.class, new AthleteSerializer())
                .addSerializer(Team.class, new TeamSerializer())
        );
  }
}
