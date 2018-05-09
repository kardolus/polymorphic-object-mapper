package us.kardol.objectmapper.polymorphic;

import com.google.gson.Gson;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PolymorphicObjectMapper {

  public <T> T fromJson(String json, List<T> classes) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    T result = null;
    Boolean isMatch;

    for(T clazz : classes){
      T tmp = (T) new Gson().fromJson(json, clazz.getClass());
      isMatch = true;

      for(PropertyDescriptor propertyDescriptor :
          Introspector.getBeanInfo(tmp.getClass()).getPropertyDescriptors()){
        if(propertyDescriptor.getReadMethod().invoke(tmp) == null){
          isMatch = false;
        }
      }

      if(isMatch){
        result = tmp;
      }
    }

    if(result == null){
      throw new MappingException("Unable to map object");
    }

    return result;
  }
}
