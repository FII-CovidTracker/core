package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
 public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userDtoToUserTest() {
        String givenMail = "mail";
        String givenName = "name";
        String givenPassword = "password";
        UserDto givenUserDto = getUserDto(givenMail, givenName, givenPassword);
        User expectedUser = getUser(givenMail, givenName, givenPassword);

        User actualUser = userService.userDtoToUser(givenUserDto);
        assertThat(expectedUser).isEqualToComparingOnlyGivenFields(actualUser, "name", "email", "password");

    }

    @Test
    public void userToUserDtoTest() {
        String givenMail = "mail";
        String givenName = "name";
        String givenPassword = "password";
        UserDto expectedUserDto = getUserDto(givenMail, givenName, givenPassword);
        User givenClinicUser = getUser(givenMail, givenName, givenPassword);

        UserDto actualUserDto = userService.userToUserDto(givenClinicUser);
        assertThat(expectedUserDto).isEqualToComparingOnlyGivenFields(actualUserDto, "name", "email", "password");

    }

    private User getUser(String email, String name, String password) {
        User user = new User();
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private UserDto getUserDto(String email, String name, String password) {
        return UserDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
