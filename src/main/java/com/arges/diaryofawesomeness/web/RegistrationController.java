package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.validation.RegistrationFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/register")
public class RegistrationController {

    private UserRepository userRepo;

    private PasswordEncoder passwordEncoder;

    private RegistrationFormValidator registrationFormValidator;

    public RegistrationController(UserRepository userRepo, PasswordEncoder passwordEncoder,
                                  RegistrationFormValidator registrationFormValidator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.registrationFormValidator = registrationFormValidator;
    }

    @GetMapping
    public String getRegisterForm(RegistrationForm registrationForm) {
        return "registration";
    }

    @PostMapping
    public String registerUser(RegistrationForm form, BindingResult bindingResult) {
        registrationFormValidator.validate(form, bindingResult);
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::toString)
                    .forEach(log::info);
            return "registration";
        }

        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
