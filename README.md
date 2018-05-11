# PolymorphicObjectMapper
This library was originally created to limit the number of nulls floating around in your Java program. 

This library enables you to deserialize a Json string using interfaces rather than classes.

You can either use a list of objects (with a common ancestor) to get its concrete implementor using `fromJson(String json, List objects)` or you can annotate your interface with `@PolymorphicDeserialize` and use the method `fromJson(String json, Class<T> interface)` instead. 

The annotation `@PolymorphicDeserialize` takes an optional argument `classes` that can be used to provide a list of candidates rather then using all of the implementors.

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
