package com.example.demo.services;

import com.example.demo.Exceptions.EntityNotFoundException;
import com.example.demo.Exceptions.InvalidLoginException;
import com.example.demo.auth.PasswordOperator;
import com.example.demo.auth.TokenAuthorization;
import com.example.demo.dto.AuthorityDto;
import com.example.demo.dto.LoginCredidentials;
import com.example.demo.dto.LoginResult;
import com.example.demo.models.Authority;
import com.example.demo.repositories.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RegionService regionService;

    public AuthorityService(AuthorityRepository authorityRepository, RegionService regionService) {
        this.authorityRepository = authorityRepository;
        this.regionService = regionService;
    }

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
        return authorityToAuthorityDto(findAuthorityById(id));
    }

    public Authority findAuthorityById(Long id) {
        return authorityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Authority", id));
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
                .id(authority.getId())
                .address(authority.getAddress())
                .canVerifyCases(authority.getCanVerifyCases())
                .email(authority.getEmail())
                .password(authority.getPassword())
                .name(authority.getName())
                .phoneNumber(authority.getPhoneNumber())
                .photoURL(authority.getUploadedFiles().stream()
                        .map(uploadedFiles -> uploadedFiles.getUrl())
                        .collect(Collectors.toList()))
                .region_id(authority.getRegion() == null ? 0 : authority.getRegion().getId())
                .build();
    }

    public Authority authorityDtoToAuthority(AuthorityDto authorityDto) {
        Authority authority = new Authority();
        authority.setAddress(authorityDto.getAddress());
        authority.setCanVerifyCases(authorityDto.getCanVerifyCases());
        authority.setEmail(authorityDto.getEmail());
        authority.setPassword(PasswordOperator.getHashForPassword(authorityDto.getPassword()));
        authority.setName(authorityDto.getName());
        authority.setPhoneNumber(authorityDto.getPhoneNumber());
        authority.setRegion(regionService.findRegionById(authorityDto.getRegion_id()));
        return authority;
    }

    public LoginResult loginAuthority(LoginCredidentials loginCredidentials) {

        String accessToken = TokenAuthorization.getAccessToken();
        System.out.println(accessToken);
        System.out.println(TokenAuthorization.isRequestAuthorized(accessToken));
        String receivedPassword = PasswordOperator.getHashForPassword(loginCredidentials.getPassword());

        AuthorityDto attemptLoginAuthority = findByEmail(loginCredidentials.getEmail());

        if (attemptLoginAuthority == null ||
                !attemptLoginAuthority.getPassword().equals(receivedPassword) ||
                !attemptLoginAuthority.getEmail().equals(loginCredidentials.getEmail())) {
            throw new InvalidLoginException();
        }

        System.out.println("Login Successful");
        return LoginResult.builder()
                .accessToken(accessToken)
                .name(attemptLoginAuthority.getName())
                .id(attemptLoginAuthority.getId())
                .regionId(attemptLoginAuthority.getRegion_id())
                .build();
    }
}
