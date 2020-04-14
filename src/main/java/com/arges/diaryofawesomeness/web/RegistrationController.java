package com.arges.diaryofawesomeness.web;

import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.validation.RegistrationFormValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationFormValidator registrationFormValidator;


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
                    .map(e -> e.toString())
                    .forEach(log::info);
            return "registration";
        }

        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
