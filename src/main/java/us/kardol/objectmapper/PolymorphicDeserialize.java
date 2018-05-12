package us.kardol.objectmapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PolymorphicDeserialize {
   Class DEFAULT_CLASS = Object.class;
   String DEFAULT_BASEPACKAGE = "";
   String BASEPACKAGE_METHOD = "basePackage";

   Class<? extends Object>[] classes() default Object.class;
   String basePackage() default DEFAULT_BASEPACKAGE;
}
