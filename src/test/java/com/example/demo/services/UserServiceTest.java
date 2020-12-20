package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.models.Region;
import com.example.demo.models.User;
import com.example.demo.models.UserType;
import com.example.demo.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

 public class UserServiceTest {
    private static final String NAME = "Name";
    private static final String PASSWORD = "12345678990";
    private static final String EMAIL = "email@adresa.com";

    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserServiceTest() {

        userRepository = Mockito.mock(UserRepository.class, this::getError);
        userService = new UserService(userRepository);
    }

    @Test
    public void findAllShouldReturnOneElement() {
        User expectedUser = getUser();

        expectedUser.setId(1L);

        doAnswer(iom -> Arrays.asList(expectedUser, expectedUser))
                .when(userRepository)
                .findAll();

        assertThat(userService.findAll().size()).isEqualTo(2);
    }

    @Test
    public void saveTestShouldCallSaveFromRepository() {
        UserDto givenUserDto = getUserDto();

        doAnswer(iom -> new User())
                .when(userRepository)
                .save(any());

        userService.saveUser(givenUserDto);

        verify(userRepository, atLeastOnce()).save(any());
    }

    @Test
    public void deleteTestShouldCallDeleteFromRepository() {
        User user = getUser();

        doAnswer(iom -> Optional.of(user))
                .when(userRepository)
                .findById(any());

        doAnswer(iom -> null)
                .when(userRepository)
                .delete(any());

        userService.deleteById(user.getId());

        verify(userRepository, atLeastOnce()).delete(any());
    }

    @Test
    public void findByIdTestShouldCallFindByIdFromRepository() {
        User user = getUser();

        doAnswer(iom -> Optional.of(user))
                .when(userRepository)
                .findById(any());

        userService.findById(user.getId());

        verify(userRepository, atLeastOnce()).findById(any());
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail(EMAIL);
        user.setName(NAME);
        user.setPassword(PASSWORD);
        user.setUserType(new UserType());
        return user;
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .name(NAME)
                .id(1L)
                .build();
    }

    private Object getError(InvocationOnMock invocationOnMock) {
        throw new RuntimeException(String.format("Should not reach %s::%s", invocationOnMock.getMock().getClass().getName(),
                invocationOnMock.getMethod().getName()));
    }
}
