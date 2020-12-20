package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.dto.UserDto;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        userRepository.delete(user);
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User", id));
        return userToUserDto(user);

    }

    public UserDto userToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
