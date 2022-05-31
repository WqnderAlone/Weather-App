package com.example;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;



//import java.util.List;
//import java.util.ArrayList;

public class GeoCoords {
  URL u;
  BufferedReader in;
  String id;
  String json;

  public GeoCoords(String city, String appId) {
    id = appId;
    try {
      String url = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&appid=" + id;
      u = new URL(url);
      in = new BufferedReader(new InputStreamReader(u.openStream()));
      json = in.readLine();
      
      json = json.replace("[", "");
      json = json.replace("]", "");
    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public double[] getCoords() throws Exception {
    double[] coords = new double[2];
    
    Gson g = new Gson();

    JsonObject obj = new JsonParser().parse(json).getAsJsonObject();

    String lat = obj.get("lat").toString();
    String lon = obj.get("lon").toString();

    coords[0] = Double.parseDouble(lat);
    coords[1] = Double.parseDouble(lon);

    return coords;
  }
}
