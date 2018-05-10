package us.kardol.objectmapper.polymorphic.entity;

import us.kardol.objectmapper.polymorphic.PolymorphicDeserialize;

@PolymorphicDeserialize(classes = {Dog.class, Cat.class})
public interface Animal {

}
