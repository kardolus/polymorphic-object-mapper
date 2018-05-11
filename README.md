# Polymorphic ObjectMapper
This library was originally created to limit the number of nulls that usually float around in a Java program. It enables you to deserialize a Json string using interfaces rather than classes.

You can either use a list of objects (with a common ancestor) to get its concrete implementor using `fromJson(String json, List objects)` or you can annotate your interface with `@PolymorphicDeserialize` and use the method `fromJson(String json, Class<T> interface)` instead. 

The annotation `@PolymorphicDeserialize` takes an optional argument `classes` that can be used to provide a list of candidates. This allows you to limit the implementors that will be considered for deserialization; like a filter. Omitting the `classes` argument will cause a scan of all the classes that are in the same package as the interface itself, including sub packages. If the interface is not in the same package structure as the candidate classes you have to explicitly specify candidates. 

Roadmap:
- `@PolymorphicDeserialize` annotation should disable the standard Jackson deserialization used by the Spring Framework

Resources:
- Custom annotations: https://dzone.com/articles/creating-custom-annotations-in-java
- Extend StdSerializer - see http://www.baeldung.com/jackson-deserialization 
- Interface Implementations: https://stackoverflow.com/questions/347248/how-can-i-get-a-list-of-all-the-implementations-of-an-interface-programmatically
