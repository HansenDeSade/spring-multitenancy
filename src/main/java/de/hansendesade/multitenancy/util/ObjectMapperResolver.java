package de.hansendesade.multitenancy.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.MediaType;

import java.util.List;

public class ObjectMapperResolver {

  private ObjectMapperResolver() {
  }

  public static ObjectMapper getObjectMapper(String contentType) {
    ObjectMapper objectMapper = null;
    if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      objectMapper = new XmlMapper();
    } else {
      objectMapper = new ObjectMapper();
    }
    return objectMapper;
  }

  /**
   * Prefers APPLICATION_JSON
   */
  public static MediaType getMediaType(String header) {
    List<MediaType> parseMediaTypes = MediaType.parseMediaTypes(header);
    if (parseMediaTypes.contains(MediaType.APPLICATION_XML)
        && !(parseMediaTypes.contains(MediaType.APPLICATION_JSON) || parseMediaTypes.contains(MediaType.APPLICATION_JSON_UTF8) || parseMediaTypes.contains(MediaType.ALL))) {
      return MediaType.APPLICATION_XML;
    }
    return MediaType.APPLICATION_JSON;
  }
}
