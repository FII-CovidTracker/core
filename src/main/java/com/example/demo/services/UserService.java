package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.dto.ArticleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> userToUserDto(user))
                .collect(Collectors.toList());
    }

    public void saveUser(UserDto userDto) {
        User user = userDtoToUser(userDto);
        userRepository.save(user);
    }

    public User userDtoToUser(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        System.out.println(user);
        return user;
    }


    public void deleteById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        System.out.println(user);
        userRepository.delete(user);
    }

    public UserDto userToUserDto(User user) {
        /////SCUZE CINE TREBUIA SA IMPLEMENTEZE ASTA DAR AVEAM NEVOIE DE EA PENTRU GET
        //il comentezi tu si returnezi null ca sa poti testa ce aide facut
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
