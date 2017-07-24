package life.dev.hari.alertChallenge.utilities;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by plank-hari.s on 7/24/2017.
 * Generic Json Deserializer providing:
 * getObject(): Conversion of json string to custom object
 * getObjectList(): Conversion of json string to an array of custom objects
 */
public class JsonDeserializer<T> {
    private final JsonFactory jsonFactory;
    private final ObjectMapper objectMapper;
    private Class<T> typeT;

    public JsonDeserializer(JsonFactory factory, ObjectMapper mapper, Class<T> clazz) {
        jsonFactory = factory;
        objectMapper = mapper;
        typeT = clazz;
    }


    public List<T> getObjectList(String jsonData) throws IOException {

        List<T> objects = new ArrayList<T>();
        ArrayList<T> allEventsSentViaRest = new ArrayList<T>();
        JsonParser jsonParser = jsonFactory.createParser(jsonData);

        jsonParser.nextToken();

        while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
            T t = objectMapper.readValue(jsonParser, typeT);
            objects.add(t);
        }
        return objects;
    }


    public T getObject(String jsonData) throws IOException {

        T t;
        JsonParser jsonParser = jsonFactory.createParser(jsonData);

        if (jsonParser.nextToken() == JsonToken.START_OBJECT) {
            t = objectMapper.readValue(jsonParser, typeT);
            return t;
        }
        return null;
    }
}
