package com.excilys.dto.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * The Interface Date.
 */
@Constraint(validatedBy = DateConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {

  /**
   * Message.
   *
   * @return the string
   */
  String message() default "{Wrong date format}";

  /**
   * Groups.
   *
   * @return the class[]
   */
  Class<?>[] groups() default {};

  /**
   * Payload.
   *
   * @return the class<? extends payload>[]
   */
  Class<? extends Payload>[] payload() default {};

}

