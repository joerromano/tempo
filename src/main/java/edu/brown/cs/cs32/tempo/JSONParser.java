package edu.brown.cs.cs32.tempo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
  public static Map<String, String> toMap(String t) throws JSONException {
    HashMap<String, String> map = new HashMap<String, String>();
    JSONObject jObject = new JSONObject(t);
    Iterator<?> keys = jObject.keys();

    while (keys.hasNext()) {
      String key = (String) keys.next();
      String value = jObject.getString(key);
      map.put(key, value);
    }

    return map;
  }
}
