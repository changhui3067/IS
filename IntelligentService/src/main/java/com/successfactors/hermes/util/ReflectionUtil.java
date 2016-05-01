package com.successfactors.hermes.util;

import java.lang.annotation.Annotation;

/**
 * Reflection util
 * @author yyang
 *
 */
public class ReflectionUtil {

  /**
   * get specified annotation from certain 
   * annotated class
   * @param clazz clazz
   * @param annotationClass annotationClass
   * @return
   */
  public static <A extends Annotation> A getSpecifiedAnnotation(Class<?> clazz,
      Class<A> annotationClass) {
    if (clazz == null) {
      throw new IllegalArgumentException("Entity class cannot be NULL.");
    }
    if (annotationClass == null) {
      throw new IllegalArgumentException(
          "Annotation class needs to be specified.");
    }
    Class<?> searchType = clazz;
    while (!Object.class.equals(searchType) && searchType != null) {
      A annotation = searchType.getAnnotation(annotationClass);
      if (annotation != null) {
        return annotation;
      }
      searchType = searchType.getSuperclass();
    }
    return null;
  }

  /**
   * constructor
   */
  private ReflectionUtil() {
  }
}
