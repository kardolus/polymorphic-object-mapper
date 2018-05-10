package us.kardol.objectmapper.entity;

import com.google.gson.Gson;

public class Cat implements Animal, Creature {
  private String color;

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  @Override
  public String toString(){
    return new Gson().toJson(this);
  }

}
