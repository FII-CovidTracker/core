package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.auth.TokenAuthorization;
import com.example.demo.dto.AuthorityDto;
import com.example.demo.models.Authority;
import com.example.demo.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public AuthorityDto authorityToAuthorityDto(Authority authority) {
        return AuthorityDto.builder()
                .address(authority.getAddress())
                .canVerifyCases(authority.getCanVerifyCases())
                .email(authority.getEmail())
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
        return null;
    }
}
