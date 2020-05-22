package com.arges.diaryofawesomeness.validation;


import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.model.User;
import com.arges.diaryofawesomeness.web.RegistrationForm;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();
    private UserRepository userRepo;

    public RegistrationFormValidator(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm form = (RegistrationForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if(form.getUsername().length() < 4 || form.getUsername().length() > 24)
            errors.rejectValue("username", "Size.registrationForm.username");
        if(userRepo.findByUsername(form.getUsername()) != null)
            errors.rejectValue("username", "Diff.registrationForm.username");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if(form.getPassword().length() < 6 || form.getPassword().length() > 32)
            errors.rejectValue("password", "Size.registrationForm.password");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");
        if(!form.getPassword().equals(form.getPasswordConfirm()))
            errors.rejectValue("passwordConfirm", "Diff.registrationForm.passwordConfirm");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if(!emailValidator.isValid(form.getEmail()))
            errors.rejectValue("email", "Format.registrationForm.email");
        if(userRepo.findByEmail(form.getEmail()) != null)
            errors.rejectValue("email", "Diff.registrationForm.email");
    }
}
