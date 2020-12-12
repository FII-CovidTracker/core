package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.auth.TokenAuthorization;
import com.example.demo.dto.AuthorityDto;
import com.example.demo.models.Authority;
import com.example.demo.repositories.AuthorityRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RegionService regionService;

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

    public void saveAuthority(AuthorityDto authorityDto) {
        Authority authority = authorityDtoToAuthority(authorityDto);
        authorityRepository.save(authority);
    }

    public AuthorityDto authorityToAuthorityDto(Authority authority) {
        return AuthorityDto.builder()
                .address(authority.getAddress())
                .canVerifyCases(authority.getCanVerifyCases())
                .email(authority.getEmail())
                .password(authority.getPassword())
                .name(authority.getName())
                .phoneNumber(authority.getPhoneNumber())
                .photoURL(authority.getUploadedFiles().stream()
                        .map(uploadedFiles -> uploadedFiles.getUrl())
                        .collect(Collectors.toList()))
                .region_id(authority.getRegion().getId())
                .build();
    }

    public Authority authorityDtoToAuthority(AuthorityDto authorityDto) {
        Authority authority = new Authority();
        authority.setAddress(authorityDto.getAddress());
        authority.setCanVerifyCases(authorityDto.getCanVerifyCases());
        authority.setEmail(authorityDto.getEmail());
        authority.setPassword(Hashing.sha256()
                .hashString(authorityDto.getPassword(), StandardCharsets.UTF_8)
                .toString());
        authority.setName(authorityDto.getName());
        authority.setPhoneNumber(authorityDto.getPhoneNumber());
        authority.setRegion(regionService.findRegionById(authorityDto.getRegion_id()));
        return authority;
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
        if (attemptLoginAuthority == null || !attemptLoginAuthority.getPassword().equals(hashedPass)) {
            System.out.println("Email does not exist or password is not correct!");
            return null;
        }
        System.out.println("Login Successful");
        return attemptLoginAuthority;
    }
}
