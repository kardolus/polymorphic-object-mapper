package us.kardol.objectmapper;

import com.google.gson.Gson;
import org.reflections.Reflections;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PolymorphicObjectMapper {
  public <T> T fromJson(String json, List<T> candidates) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
    Boolean isMatch;

    for(T clazz : candidates){
      T result = (T) new Gson().fromJson(json, clazz.getClass());
      isMatch = true;

      for(PropertyDescriptor propertyDescriptor :
          Introspector.getBeanInfo(result.getClass()).getPropertyDescriptors()){
        if(propertyDescriptor.getReadMethod().invoke(result) == null){
          isMatch = false;
        }
      }

      if(isMatch){
        return result;
      }
    }

    throw new MappingException("Unable to map object");
  }

  public <T> T fromJson(String json, Class<T> interfaze) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    List<T> candidates = new ArrayList<>();

    for (Annotation annotation : interfaze.getAnnotations()) {
      Class<? extends Annotation> type = annotation.annotationType();

      for (Method method : type.getDeclaredMethods()) {
        Object value = method.invoke(annotation, (Object[])null);
        Class[] classes = (Class[]) value;

        if(classes.length == 1 && classes[0].equals(PolymorphicDeserialize.DEFAULT_CLASS)){
          return fromJsonUsingAllImplementations(json, interfaze);
        }

        for(Class clazz : classes){
          Constructor<?> constructor = clazz.getConstructor();
          T object = (T) constructor.newInstance();
          candidates.add(object);
        }
      }
    }

    return this.fromJson(json, candidates);
  }

  private <T> T fromJsonUsingAllImplementations(String json, Class<T> interfaze) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    List<T> candidates = new ArrayList<>();
    Package pack = interfaze.getPackage();
    Reflections reflections = new Reflections(pack.getName());
    Set<Class<? extends T>> implementors = reflections.getSubTypesOf(interfaze);

    for(Class clazz : implementors){
      Constructor<?> constructor = clazz.getConstructor();
      T object = (T) constructor.newInstance();
      candidates.add(object);
    }

    return this.fromJson(json, candidates);
  }


}
