package com.mbw.commons.util.validate;

import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.Result;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * TODO
 *
 * @author Mabowen
 * @date 2020-04-08 22:05
 */
public class ValidateUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 参数校验
     * @param t
     * @return
     */
    public static <T> Result validate(T t) {
        if (validator == null) {
            validator = Validation.buildDefaultValidatorFactory().getValidator();
        }
        return FluentValidator
                .checkAll()
                .on(t, new HibernateSupportedValidator<T>().setHiberanteValidator(validator))
                .doValidate()
                .result(toSimple());
    }
}
