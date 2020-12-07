package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.auth.TokenAuthorization;
import com.example.demo.dto.AuthorityDto;
import com.example.demo.models.Authority;
import com.example.demo.repositories.AuthorityRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    public List<AuthorityDto> findAll() {
        return authorityRepository.findAll().stream()
                .map(authority -> authorityToAuthorityDto(authority))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        Authority region = authorityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Authority", id));
        authorityRepository.delete(region);
    }

    public AuthorityDto findById(Long id) {
        Authority authority = authorityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Authority", id));
        return authorityToAuthorityDto(authority);

    }

    public AuthorityDto findByEmail(String email) {
        Authority authority = authorityRepository.findAuthorityByEmail(email);
        if (authority == null)
            return null;
        return authorityToAuthorityDto(authority);
    }

    public AuthorityDto authorityToAuthorityDto(Authority authority) {
        return AuthorityDto.builder()
                .address(authority.getAddress())
                .canVerifyCases(authority.getCanVerifyCases())
                .email(authority.getEmail())
                .hashedPassword(authority.getHashedPassword())
                .name(authority.getName())
                .phoneNumber(authority.getPhoneNumber())
                .photoURL(authority.getUploadedFiles().stream()
                        .map(uploadedFiles -> uploadedFiles.getUrl())
                        .collect(Collectors.toList()))
                .region(authority.getRegion().getName())

                .build();
    }

    public AuthorityDto loginAuthority() {
        String accessToken = TokenAuthorization.getAccessToken();
        System.out.println(accessToken);
        System.out.println(TokenAuthorization.isRequestAuthorized(accessToken));
        String testEmail = "test@test.com";
        String testPassword = "test";
        String hashedPass = Hashing.sha256()
                .hashString(testPassword, StandardCharsets.UTF_8)
                .toString();
        AuthorityDto attemptLoginAuthority = findByEmail(testEmail);
        if (attemptLoginAuthority == null || !attemptLoginAuthority.getHashedPassword().equals(hashedPass)) {
            System.out.println("Email does not exist or password is not correct!");
            return null;
        }
        System.out.println("Login Successful");
        return attemptLoginAuthority;
    }
}
