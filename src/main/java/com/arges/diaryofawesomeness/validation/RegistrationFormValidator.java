package com.arges.diaryofawesomeness.validation;


import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.model.User;
import com.arges.diaryofawesomeness.security.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

    @Autowired
    private UserRepository userRepo;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm form = (RegistrationForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if(form.getUsername().length() < 4 || form.getUsername().length() > 24)
            errors.rejectValue("username", "size.registrationForm.username");
        if(userRepo.findByUsername(form.getUsername()) != null)
            errors.rejectValue("username", "diff.registrationForm.username");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if(form.getPassword().length() < 6 || form.getPassword().length() > 32)
            errors.rejectValue("password", "size.registrationForm.password");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
        if(!form.getPassword().equals(form.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm", "diff.registrationForm.passwordConfirm");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
    }
}
