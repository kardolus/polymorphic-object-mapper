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

  public <T> T fromJson(String json, Class<T> interfaze) throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
    List<T> classes = new ArrayList<>();

    for (Annotation annotation : interfaze.getAnnotations()) {
      Class<? extends Annotation> type = annotation.annotationType();

      for (Method method : type.getDeclaredMethods()) {
        Object value = method.invoke(annotation, (Object[])null);
        Class[] clazzes = (Class[]) value;

        if(clazzes.length == 1 && clazzes[0].getName().equals("java.lang.Object")){
          return fromJsonUsingAllImplementations(json, interfaze);
        }

        for(Class clazz : clazzes){
          Constructor<?> constructor = clazz.getConstructor();
          T object = (T) constructor.newInstance();
          classes.add(object);
        }
      }
    }

    return this.fromJson(json, classes);
  }

  private <T> T fromJsonUsingAllImplementations(String json, Class<T> interfaze) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    List<T> classes = new ArrayList<>();
    Reflections reflections = new Reflections("us.kardol");
    Set<Class<? extends T>> implementors = reflections.getSubTypesOf(interfaze);

    for(Class clazz : implementors){
      Constructor<?> constructor = clazz.getConstructor();
      T object = (T) constructor.newInstance();
      classes.add(object);
    }

    return this.fromJson(json, classes);
  }


}