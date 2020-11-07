package com.example.demo.services;


import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        UserDto givenUserDto = getOtherUserDto(givenMail, givenName, givenPassword);
        User expectedUser = getOtherUser(givenMail, givenName, givenPassword);

        User actualUser = userService.userDtoToUser(givenUserDto);
        assertThat(expectedUser).isEqualToComparingOnlyGivenFields(actualUser, "name", "email", "password");

    }

    private User getOtherUser(String email, String name, String password) {
        User user = new User();
        user.setPassword(password);
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    private UserDto getOtherUserDto(String email, String name, String password) {
        return UserDto.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
