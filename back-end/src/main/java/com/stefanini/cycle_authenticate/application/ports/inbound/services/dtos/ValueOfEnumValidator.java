package com.stefanini.cycle_authenticate.application.ports.inbound.services.dtos;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

    private List<String> acceptedValues;


    @Override
    public void initialize(ValueOfEnum constraintAnnotation) {
        this.acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Deixa o @NotBlank/@NotNull cuidar do nulo
        }

        // VERIFICA SE A STRING DE ENTRADA É VÁLIDA
        return acceptedValues.contains(value.toString());
    }
}
