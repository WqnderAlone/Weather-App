package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Weather {
  double lat, lon;
  String key;
  URL u;
  BufferedReader in;
  String id;
  String json;
  JsonObject tempObj;
  boolean imperial;
  
  Gson g; 
  JsonObject obj;

  public Weather(double[] coords, String appId) {
    g = new Gson();
    lat = coords[0];
    lon = coords[1];
    key = appId;
    try {
      String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + key;
      u = new URL(url);
      in = new BufferedReader(new InputStreamReader(u.openStream()));
      json = in.readLine();

      obj = new JsonParser().parse(json).getAsJsonObject();
      tempObj = obj;
    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public Weather(double[] coords, boolean metric, String appId) {
    g = new Gson();
    imperial = !metric;
    lat = coords[0];
    lon = coords[1];
    key = appId;
    try {
      String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + key;
      u = new URL(url);
      in = new BufferedReader(new InputStreamReader(u.openStream()));
      json = in.readLine();

      obj = new JsonParser().parse(json).getAsJsonObject();
      tempObj = obj;
    }

    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getWeatherJson() {
    String temp = obj.get("weather").toString();

    temp = temp.replace("[", "");
    temp = temp.replace("]", "");

    return temp;
  }

  public String getWeatherDescription() {
    String temp = getWeatherJson();
    tempObj = new JsonParser().parse(temp).getAsJsonObject();

    temp = tempObj.get("description").toString();

    return temp;
  }

  public String getMainJson() {
    String temp = obj.get("main").toString();
    
    temp = temp.replace("[", "");
    temp = temp.replace("]", "");

    return temp;
  }

  public String getTemperature() {
    double temperature = 0.0;
    String json = getMainJson();
    tempObj = new JsonParser().parse(json).getAsJsonObject();
    temperature = Double.parseDouble(tempObj.get("temp").getAsString());

    temperature -= 273;
    
    if (imperial) {
      temperature = temperature * 9 / 5;
      temperature += 32;

      return String.valueOf(temperature).substring(0, 5) + " degrees Fahrenheit";
    }

    return String.valueOf(temperature).substring(0, 5) + " degrees Celsius";
  }

  public String getFeelsLike() {
    double temperature = 0.0;
    String json = getMainJson();
    tempObj = new JsonParser().parse(json).getAsJsonObject();
    temperature = Double.parseDouble(tempObj.get("feels_like").getAsString());

    temperature -= 273;

    if (imperial) {
      temperature = temperature * 9 / 5;
      temperature += 32;

      return String.valueOf(temperature).substring(0, 5) + " degrees Fahrenheit";
    }

    return String.valueOf(temperature).substring(0, 5) + " degrees Celsius";
  }

  public String getWindJson() {
    String temp = obj.get("wind").toString();

    temp = temp.replace("[", "");
    temp = temp.replace("]", "");

    return temp;
  }

  public String getWindSpeed() {
    double speed = 0;
    String json = getWindJson();
    tempObj = new JsonParser().parse(json).getAsJsonObject();

    speed = Double.parseDouble(tempObj.get("speed").toString());

    if (imperial) {
      speed *= 2.237;

      return String.valueOf(speed).substring(0, 5) + " miles / hour";
    }
    
    return String.valueOf(speed).substring(0, 5) + " meters / second";
  }

  public String getWindDeg() {
    double deg = 0;
    String json = getWindJson();
    tempObj = new JsonParser().parse(json).getAsJsonObject();

    deg = Double.parseDouble(tempObj.get("deg").toString());

    return String.valueOf(deg) + " degrees";
  }
}
