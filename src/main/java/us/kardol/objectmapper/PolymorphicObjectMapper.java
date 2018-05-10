package us.kardol.objectmapper;

import com.google.gson.Gson;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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

    if(result != null){
      return result;
    }
    
    throw new MappingException("Unable to map object");
  }

  public <T> T fromJson(String json, Class<T> clazz) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    List<T> classes = new ArrayList<>();

    for (Annotation annotation : clazz.getAnnotations()) {
      Class<? extends Annotation> type = annotation.annotationType();
      System.out.println("Values of " + type.getName());

      for (Method method : type.getDeclaredMethods()) {
        Object value = method.invoke(annotation, (Object[])null);
        System.out.println(" " + method.getName() + ":");

        for(Class klass : (Class[]) value){
          System.out.println("  " + klass.getName());
          Constructor<?> constructor = klass.getConstructor();
          T object = (T) constructor.newInstance();
          classes.add(object);
        }
      }
    }

    return this.fromJson(json, classes);
  }
}
