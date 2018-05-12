package us.kardol.objectmapper;

import org.junit.Before;
import org.junit.Test;
import us.kardol.objectmapper.entity.Animal;
import us.kardol.objectmapper.entity.Cat;
import us.kardol.objectmapper.entity.Creature;
import us.kardol.objectmapper.entity.Dog;
import us.kardol.objectmapper.entity.subentity.HiggsBoson;
import us.kardol.objectmapper.entity.subentity.Pet;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PolymorphicObjectMapperTest {
  private PolymorphicObjectMapper subject;
  private String jsonInString;
  private Animal result;
  private Set<Animal> animals;

  @Before
  public void setUp(){
    subject = new PolymorphicObjectMapper();
    animals = new HashSet<>();
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

  @Test
  public void mappingAnnotatedClassesShouldParseListOfCandidatesWhenClassesAreOmitted() throws Exception{
    jsonInString = "{'name' : 'Persol', 'age' : 14}";
    Creature creature = subject.fromJson(jsonInString, Creature.class);
    Dog dog = (Dog) creature;
    assertThat(dog.getName(), is(equalTo("Persol")));
    assertThat(dog.getAge(), is(14));
  }

  @Test(expected = MappingException.class)
  public void componentScanShouldFailWhenClassesNotInSameBasePackageAsInterface() throws Exception{
    jsonInString = "{'name' : 'Persol', 'age' : 14}";
    HiggsBoson boson = subject.fromJson(jsonInString, HiggsBoson.class);
  }

  @Test
  public void componentScanShouldSucceedWhenBasePackageIsSet() throws Exception{
    jsonInString = "{'name' : 'Persol', 'age' : 14}";
    Pet pet = subject.fromJson(jsonInString, Pet.class);
    Dog dog = (Dog) pet;
    assertThat(dog.getName(), is(equalTo("Persol")));
    assertThat(dog.getAge(), is(14));
  }
}
