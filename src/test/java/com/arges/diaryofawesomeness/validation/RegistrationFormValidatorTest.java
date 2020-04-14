package com.arges.diaryofawesomeness.validation;

import com.arges.diaryofawesomeness.data.UserRepository;
import com.arges.diaryofawesomeness.model.User;
import com.arges.diaryofawesomeness.web.RegistrationForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RegistrationFormValidatorTest {

    @InjectMocks
    private RegistrationFormValidator validator;

    @Mock
    private UserRepository userRepo;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

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
        //given
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
        //given
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
        //given
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
        //given
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
    public void testPasswordsDoNotMatch() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("password");
        form.setPasswordConfirm("pass");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of( "Diff.registrationForm.passwordConfirm");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testUsernameTooShort() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("us");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Size.registrationForm.username");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testUsernameTooLong() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("useruseruseruseruseruseru");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Size.registrationForm.username");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testPasswordTooShort() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("pass");
        form.setPasswordConfirm("pass");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Size.registrationForm.password");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testPasswordTooLong() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("passwordpasswordpasswordpasswordp");
        form.setPasswordConfirm("passwordpasswordpasswordpasswordp");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Size.registrationForm.password");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testEmailFormatNotValid() {
        //given
        RegistrationForm form = new RegistrationForm();
        form.setUsername("test");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("emejl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Format.registrationForm.email");

        //when
        validator.validate(form, errors);

        //then
        validateErrorCodes(errors, 1, expectedCodes);
    }

    @Test
    public void testUserWithGivenUsernameAlreadyExists() {
        //given
        when(userRepo.findByUsername("test_user1"))
            .thenReturn(new User("test_user1", "admin1234", "test_user1@jar.pl"));

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

    @Test
    public void testUserWithGivenEmailAlreadyExists() {
        //given
        when(userRepo.findByEmail("mar@jar.pl"))
                .thenReturn(new User("test", "admin1234", "mar@jar.pl"));

        RegistrationForm form = new RegistrationForm();
        form.setUsername("test_user1");
        form.setPassword("password");
        form.setPasswordConfirm("password");
        form.setEmail("mar@jar.pl");
        Errors errors = new BeanPropertyBindingResult(form, "validForm");
        List<String> expectedCodes = List.of("Diff.registrationForm.email");

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