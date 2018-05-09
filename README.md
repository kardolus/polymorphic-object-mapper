# PolymorphicObjectMapper
Main method: fromJson(String json, List<? Extends Object>)

Pass a List of objects that share the same parent and a json strong to the PolymorphicObjectMapper and it will determine which concrete type to deserialize the Json to.  
