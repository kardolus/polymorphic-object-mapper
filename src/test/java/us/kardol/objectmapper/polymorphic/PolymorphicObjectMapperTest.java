package us.kardol.objectmapper.polymorphic;

import org.junit.Before;
import org.junit.Test;
import us.kardol.objectmapper.polymorphic.entity.Animal;
import us.kardol.objectmapper.polymorphic.entity.Cat;
import us.kardol.objectmapper.polymorphic.entity.Dog;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PolymorphicObjectMapperTest {
  private PolymorphicObjectMapper subject;
  private String jsonInString;
  private Animal result;
  private List<Animal> animals;

  @Before
  public void setUp(){
    subject = new PolymorphicObjectMapper();
    animals = new ArrayList<>();
    animals.add(new Dog());
    animals.add(new Cat());
  }

  @Test
  public void mapperShouldParseACatIfAllFieldsAreSet() throws Exception{
    jsonInString = "{'color' : 'gray'}";
    result = subject.fromJson(jsonInString, animals);
    Cat cat = (Cat) result;
    assertThat(cat.getColor(), is(equalTo("gray")));
  }

  @Test
  public void mapperShouldParseADogIfAllFieldsAreSet() throws Exception{
    jsonInString = "{'name' : 'Persol', 'age' : 14}";
    result = subject.fromJson(jsonInString, animals);
    Dog dog = (Dog) result;
    assertThat(dog.getName(), is(equalTo("Persol")));
    assertThat(dog.getAge(), is(14));
  }

  @Test(expected = MappingException.class)
  public void mapperShouldThrowAnExceptionIfFieldsAreMissing() throws Exception{
    jsonInString = "{'name' : 'Persol'}";
    result = subject.fromJson(jsonInString, animals);
  }

  @Test
  public void mappingAnnotatedClassesShouldParseListOfCandidates() throws Exception{
    jsonInString = "{'name' : 'Persol', 'age' : 14}";
    result = subject.fromJson(jsonInString, Animal.class);
    Dog dog = (Dog) result;
    assertThat(dog.getName(), is(equalTo("Persol")));
    assertThat(dog.getAge(), is(14));
  }
}
