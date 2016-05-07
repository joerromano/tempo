package edu.brown.cs.cs32.tempo;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
  private Gson gson = new Gson();

  @Override
  public String render(Object model) {
    return gson.toJson(model);
  }
}
