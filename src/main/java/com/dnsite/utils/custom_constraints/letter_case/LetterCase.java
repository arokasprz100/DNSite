package com.dnsite.utils.custom_constraints.letter_case;

import javax.validation.Constraint;
        import javax.validation.Payload;
        import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LetterCaseValidator.class)
@Documented
public @interface LetterCase {

    String message() default "{com.dnsite.utils.custom_constraints." + "message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    LetterCaseMode value();

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        LetterCase[] value();
    }
}
