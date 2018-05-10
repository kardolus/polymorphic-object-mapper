# PolymorphicObjectMapper
Main method: fromJson(String json, List objects)

This library was created to limit the number of null-objects floating around in your Spring container. 

Pass a List of objects that implement the same interface and a json string to the PolymorphicObjectMapper and it will determine which concrete type to deserialize the Json to. 

Roadmap:
- Annotate an interface with `@JsonDeserialize(using = Cat.class, Dog.class, Canary.class)`
- This annotation will disable the standard Jackson deserialization
- The list of classes will then be passed to a new class that extends StdSerializer
- The StdSerializer then calls the PolyMorphicObjectMapper

Resources:
- Custom annotations: https://dzone.com/articles/creating-custom-annotations-in-java
- Extend StdSerializer - see http://www.baeldung.com/jackson-deserialization 
