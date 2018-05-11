package us.kardol.objectmapper;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PolymorphicDeserialize {
   Class DEFAULT_CLASS = Object.class;

   Class<? extends Object>[] classes() default Object.class;
}
