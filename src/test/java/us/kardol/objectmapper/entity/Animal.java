package us.kardol.objectmapper.entity;

import us.kardol.objectmapper.PolymorphicDeserialize;
import us.kardol.objectmapper.entity.subentity.HiggsBoson;

@PolymorphicDeserialize(classes = {Dog.class, Cat.class})
public interface Animal extends HiggsBoson {

}
