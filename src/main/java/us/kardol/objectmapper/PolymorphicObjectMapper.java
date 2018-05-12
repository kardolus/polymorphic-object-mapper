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
import java.util.HashSet;
import java.util.Set;

public class PolymorphicObjectMapper {
  public <T> T fromJson(String json, Set<T> candidates) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
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
    Set<T> candidates = new HashSet<>();
    Boolean hasClassesAnnotation = true;
    Boolean hasBasePackageAnnotation = false;

    for (Annotation annotation : interfaze.getAnnotations()) {
      Class<? extends Annotation> type = annotation.annotationType();

      for (Method method : type.getDeclaredMethods()) {
        if(method.getName().equals(PolymorphicDeserialize.BASEPACKAGE_METHOD)){
          Object value = method.invoke(annotation, (Object[])null);
          String basePackage = (String) value;

          if(basePackage.equals(PolymorphicDeserialize.DEFAULT_BASEPACKAGE)){
            hasBasePackageAnnotation = false;
            continue;
          }
          hasBasePackageAnnotation = true;
          candidates.addAll(findImplementors(interfaze, basePackage));
          continue;
        }

        Object value = method.invoke(annotation, (Object[])null);
        Class[] classes = (Class[]) value;

        if(classes.length == 1 && classes[0].equals(PolymorphicDeserialize.DEFAULT_CLASS)){
          hasClassesAnnotation = false;
          continue;
        }

        for(Class clazz : classes){
          Constructor<?> constructor = clazz.getConstructor();
          T object = (T) constructor.newInstance();
          candidates.add(object);
        }
      }
    }

    if(candidates.size() == 0 && !hasClassesAnnotation && !hasBasePackageAnnotation){
      return fromJsonUsingAllImplementations(json, interfaze);
    }

    return this.fromJson(json, candidates);
  }

  private <T> T fromJsonUsingAllImplementations(String json, Class<T> interfaze) throws IllegalAccessException, IntrospectionException, InvocationTargetException, InstantiationException, NoSuchMethodException {
    Package pack = interfaze.getPackage();
    Set<T> candidates = this.findImplementors(interfaze, pack.getName());
    return this.fromJson(json, candidates);
  }

  private <T> Set<T> findImplementors(Class<T> interfaze, String basePackage) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    Set<T> candidates = new HashSet<>();
    Reflections reflections = new Reflections(basePackage);
    Set<Class<? extends T>> implementors = reflections.getSubTypesOf(interfaze);

    for(Class clazz : implementors){
      Constructor<?> constructor = clazz.getConstructor();
      T object = (T) constructor.newInstance();
      candidates.add(object);
    }
    return candidates;
  }
}
