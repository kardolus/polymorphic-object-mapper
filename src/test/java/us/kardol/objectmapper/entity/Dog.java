package us.kardol.objectmapper.entity;

import com.google.gson.Gson;

public class Dog implements Animal{
  private String name;
  private Integer age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String toString(){
    return new Gson().toJson(this);
  }
}
