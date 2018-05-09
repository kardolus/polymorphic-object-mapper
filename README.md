# PolymorphicObjectMapper
Main method: fromJson(String json, List<? Extends Object> objects)

Pass a List of objects that share the same parent and a json string to the PolymorphicObjectMapper and it will determine which concrete type to deserialize the Json to.  

Spring Integration Options/Ideas:
- (Custom) Annotate the interface with `@JsonDeserialize(using = Cat.class, Dog.class, Canary.class)` - see https://dzone.com/articles/creating-custom-annotations-in-java
- Extend StdSerializer - see http://www.baeldung.com/jackson-deserialization 
