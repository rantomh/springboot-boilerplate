package com.rantomah.boilerplate.infrastructure.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
        PasswordValidator validator =
                new PasswordValidator(
                        Arrays.asList(
                                // at least 8 characters
                                new LengthRule(8, 30),
                                // at least one upper-case character
                                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                                // at least one lower-case character
                                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                                // at least one digit character
                                new CharacterRule(EnglishCharacterData.Digit, 1),
                                // at least one symbol (special character)
                                new CharacterRule(EnglishCharacterData.Special, 1),
                                // no whitespace
                                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            List<String> messages = validator.getMessages(result);
            String messageTemplate = String.join(",", messages);
            context.buildConstraintViolationWithTemplate(messageTemplate)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }
}
