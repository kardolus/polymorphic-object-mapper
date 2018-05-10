# PolymorphicObjectMapper
Main method: fromJson(String json, List objects)

This library was created to limit the number of null-objects floating around in your Spring container. 

Pass a List of objects that implement the same interface and a json string to the PolymorphicObjectMapper and it will determine which concrete type to deserialize the Json to. 

Roadmap:
- Annotate an interface with `@PolymorphicDeserialize(classes = {Cat.class, Dog.class, Canary.class})`
- When classes are omitted, use the JVM to find all implementations of an Interface
- This annotation will disable the standard Jackson deserialization
- The list of classes will then be passed to a new class that extends StdSerializer
- The StdSerializer then calls the PolyMorphicObjectMapper

Resources:
- Custom annotations: https://dzone.com/articles/creating-custom-annotations-in-java
- Extend StdSerializer - see http://www.baeldung.com/jackson-deserialization 
- Interface Implementations: https://stackoverflow.com/questions/347248/how-can-i-get-a-list-of-all-the-implementations-of-an-interface-programmatically
