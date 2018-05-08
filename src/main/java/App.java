import entity.Animal;
import entity.Cat;
import entity.Dog;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class App {
  public static void main(String... args){
    String jsonInString = "{'color' : 'gray'}";
    PolymorphicObjectMapper mapper = new PolymorphicObjectMapper();
    List<Animal> animals = new ArrayList<>();
    animals.add(new Dog());
    animals.add(new Cat());

    Animal result = null;

    try {
      result = mapper.fromJson(jsonInString, animals);
      System.out.println(result.getClass() + " " + result);
    } catch (IntrospectionException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
