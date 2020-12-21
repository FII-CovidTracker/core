package com.example.demo.services;

import com.example.demo.Exceptions.InvalidLoginException;
import com.example.demo.auth.PasswordOperator;
import com.example.demo.dto.AuthorityDto;
import com.example.demo.dto.LoginCredidentials;
import com.example.demo.dto.RegionDto;
import com.example.demo.helpers.ServiceTestValuesWrapper;
import com.example.demo.models.Authority;
import com.example.demo.models.Region;
import com.example.demo.repositories.AuthorityRepository;
import com.google.common.hash.Hashing;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthorityServiceTest {

   private AuthorityRepository authorityRepository;
   private RegionService regionService;

   @Autowired
   private AuthorityService authorityService;

   public AuthorityServiceTest() {

       authorityRepository = Mockito.mock(AuthorityRepository.class, this::getError);
       regionService = Mockito.mock(RegionService.class, this::getError);
       authorityService = new AuthorityService(authorityRepository, regionService);
   }

   @Test
   public void findAllShouldReturnOneElement() {
       Authority expectedAuthority = getAuthority();

       expectedAuthority.setId(1L);

       doAnswer(iom -> Arrays.asList(expectedAuthority, expectedAuthority))
               .when(authorityRepository)
               .findAll();

       assertThat(authorityService.findAll().size()).isEqualTo(2);
   }

   @Test
   public void saveTestShouldCallSaveFromRepository() {
       AuthorityDto givenAuthorityDto = getAuthorityDto();

       doAnswer(iom -> new Authority())
               .when(authorityRepository)
               .save(any());

       doAnswer(iom -> getRegion())
               .when(regionService)
               .findRegionById(any());

       authorityService.saveAuthority(givenAuthorityDto);

       verify(authorityRepository, atLeastOnce()).save(any());
       verify(regionService, atLeastOnce()).findRegionById(any());
   }

   @Test
   public void deleteTestShouldCallDeleteFromRepository() {
       Authority authority = getAuthority();

       doAnswer(iom -> Optional.of(authority))
               .when(authorityRepository)
               .findById(any());

       doAnswer(iom -> null)
               .when(authorityRepository)
               .delete(any());

       authorityService.deleteById(authority.getId());

       verify(authorityRepository, atLeastOnce()).delete(any());
   }

   @Test
   public void findByIdTestShouldCallFindByIdFromRepository() {
       Authority authority = getAuthority();

       doAnswer(iom -> Optional.of(authority))
               .when(authorityRepository)
               .findById(any());

       authorityService.findById(authority.getId());

       verify(authorityRepository, atLeastOnce()).findById(any());
   }

    @Test
    public void findByEmailTestShouldCallFindAuthorityByEmailFromRepository() {
        Authority authority = getAuthority();

        doAnswer(iom -> authority)
                .when(authorityRepository)
                .findAuthorityByEmail(any());

        authorityService.findByEmail(authority.getEmail());

        verify(authorityRepository, atLeastOnce()).findAuthorityByEmail(any());
    }

    @Test
    public void nullCornerCaseForFindByEmailTestShouldCallFindAuthorityByEmailFromRepository() {
        doAnswer(iom -> null)
                .when(authorityRepository)
                .findAuthorityByEmail(any());

        authorityService.findByEmail(ServiceTestValuesWrapper.EMAIL);

        verify(authorityRepository, atLeastOnce()).findAuthorityByEmail(any());
    }

    @Test
    public void findAuthorityByIdTestShouldCallFindByIdFromRepository() {
        Authority authority = getAuthority();

        doAnswer(iom -> Optional.of(authority))
                .when(authorityRepository)
                .findById(any());

        authorityService.findAuthorityById(authority.getId());

        verify(authorityRepository, atLeastOnce()).findById(any());
    }

    @Test
    public void loginAuthorityTestPassesOnCorrectCredentials() {
        Authority authority = getAuthority();
        authority.setPassword(hashString(ServiceTestValuesWrapper.PASSWORD));
        doAnswer(iom -> authority)
                .when(authorityRepository)
                .findAuthorityByEmail(any());

        authorityService.loginAuthority(new LoginCredidentials(ServiceTestValuesWrapper.EMAIL, ServiceTestValuesWrapper.PASSWORD));

        verify(authorityRepository, atLeastOnce()).findAuthorityByEmail(any());
    }

    @Test(expected = InvalidLoginException.class)
    public void loginAuthorityTestThrowsOnIncorrectCredentials() {
        Authority authority = getAuthority();

        doAnswer(iom -> authority)
                .when(authorityRepository)
                .findAuthorityByEmail(any());

        authorityService.loginAuthority(new LoginCredidentials(ServiceTestValuesWrapper.EMAIL, "wrong"));
    }

   private Authority getAuthority() {
       Authority authority = new Authority();
       authority.setId(1L);
       authority.setName(ServiceTestValuesWrapper.AUTHORITY_NAME);
       authority.setAddress(ServiceTestValuesWrapper.ADDRESS);
       authority.setCanVerifyCases(true);
       authority.setEmail(ServiceTestValuesWrapper.EMAIL);
       authority.setPassword(ServiceTestValuesWrapper.PASSWORD);
       authority.setPhoneNumber(ServiceTestValuesWrapper.PHONE);
       authority.setRegion(getRegion());
       authority.setArticleSet(new LinkedHashSet<>());
       authority.setAuthorityUsers(new LinkedHashSet<>());
       authority.setUploadedFiles(new LinkedHashSet<>());
       return authority;
   }

   private AuthorityDto getAuthorityDto() {
       return AuthorityDto.builder()
               .email(ServiceTestValuesWrapper.EMAIL)
               .password(ServiceTestValuesWrapper.PASSWORD)
               .address(ServiceTestValuesWrapper.ADDRESS)
               .region_id(1L)
               .photoURL(new ArrayList<>())
               .phoneNumber(ServiceTestValuesWrapper.PHONE)
               .canVerifyCases(true)
               .name(ServiceTestValuesWrapper.AUTHORITY_NAME)
               .id(1L)
               .build();
   }

    private Region getRegion() {
        Region region = new Region();
        region.setId(1L);
        region.setName(ServiceTestValuesWrapper.AUTHORITY_NAME);
        region.setGlobal(true);
        region.setAuthoritySet(new LinkedHashSet<>());
        region.setClinicSet(new LinkedHashSet<>());
        return region;
    }
    private String hashString(String string){
       return PasswordOperator.getHashForPassword(string);
    }
   private Object getError(InvocationOnMock invocationOnMock) {
       throw new RuntimeException(String.format("Should not reach %s::%s", invocationOnMock.getMock().getClass().getName(),
               invocationOnMock.getMethod().getName()));
   }
}
