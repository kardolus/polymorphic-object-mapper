# PolymorphicObjectMapper
Main method: fromJson(String json, List<? Extends Object> objects)

Pass a List of objects that share the same parent and a json string to the PolymorphicObjectMapper and it will determine which concrete type to deserialize the Json to.  
