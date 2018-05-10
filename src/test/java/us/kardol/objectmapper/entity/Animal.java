package us.kardol.objectmapper.entity;

import us.kardol.objectmapper.PolymorphicDeserialize;

@PolymorphicDeserialize(classes = {Dog.class, Cat.class})
public interface Animal {

}
