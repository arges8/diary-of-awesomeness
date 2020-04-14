package com.arges.diaryofawesomeness.validation;

import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.model.User;
import com.arges.diaryofawesomeness.web.RegistrationForm;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RegistrationFormValidatorTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private RegistrationFormValidator validator;

    @Test
    public void testCorrectForm() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");

        //when
        validator.validate(form, errors);

        //then
        assertFalse(errors.hasErrors());
    }

    @Test
    public void testEmptyUsername() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("NotEmpty", "Size.registrationForm.username");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 2, expectedCodes);
    }

    @Test
    public void testEmptyPassword() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("NotEmpty", "Size.registrationForm.password", "Diff.registrationForm.passwordConfirm");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 3, expectedCodes);
    }

    @Test
    public void testEmptyPasswordConfirm() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("password");
        form.setPasswordConfirm("");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("NotEmpty", "Diff.registrationForm.passwordConfirm");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 2, expectedCodes);
    }

    @Test
    public void testEmptyEmail() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("NotEmpty", "Format.registrationForm.email");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 2, expectedCodes);
    }

    @Test
    public void testUserWithGivenUsernameAlreadyExists() {
        when(userRepo.findByUsername("test_user1"))
                .thenReturn(new User("test_user1", "admin1234", "test_user1@jar.pl"));

        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test_user1");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Diff.registrationForm.username");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);

    }

    private void validateErrorCodes(Errors errors, int expectedNumberOfErrors, List<String> expectedCodes) {
        List<ObjectError> errorList = errors.getAllErrors();
        assertEquals(expectedNumberOfErrors, errorList.size());
        errorList.forEach(e -> checkIfContainCodes(e, expectedCodes));
    }
    private void checkIfContainCodes(ObjectError error, List<String> codes) {
        String errorCode = error.getCode();
        if(errorCode == null)
            fail();

        long size = codes.stream()
                         .filter(errorCode::contains)
                         .count();

        assertEquals(1, size);
    }


}