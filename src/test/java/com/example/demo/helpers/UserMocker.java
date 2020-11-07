package com.example.demo.helpers;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.LinkedList;
import java.util.List;

@TestComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMocker {

    private static User getMockedUser() {
        User user = new User();
        user.setEmail("email@mail.com");
        user.setName("Mihnea Rezmerita");
        user.setPassword("Not a good password");
        return user;
    }

    public static void addMockedUsers(UserRepository repository) {
        List<User> users = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            users.add(getMockedUser());
        }
        repository.saveAll(users);
    }

}
