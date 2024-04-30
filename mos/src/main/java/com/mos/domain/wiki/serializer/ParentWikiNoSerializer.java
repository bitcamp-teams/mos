package com.mos.domain.wiki.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class ParentWikiNoSerializer extends JsonSerializer<Integer> {

  @Override
  public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value == 0 ? "#" : String.valueOf(value));
  }


}
