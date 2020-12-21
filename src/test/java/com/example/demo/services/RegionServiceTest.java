package com.example.demo.services;

import com.example.demo.dto.RegionDto;
import com.example.demo.helpers.ServiceTestValuesWrapper;
import com.example.demo.models.Region;
import com.example.demo.repositories.RegionRepository;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegionServiceTest {

   private RegionRepository regionRepository;

   @Autowired
   private RegionService regionService;

   public RegionServiceTest() {

       regionRepository = Mockito.mock(RegionRepository.class, this::getError);
       regionService = new RegionService(regionRepository);
   }

   @Test
   public void findAllShouldReturnOneElement() {
       Region expectedRegion = getRegion();

       expectedRegion.setId(1L);

       doAnswer(iom -> Arrays.asList(expectedRegion, expectedRegion))
               .when(regionRepository)
               .findAll();

       assertThat(regionService.findAll().size()).isEqualTo(2);
   }

   @Test
   public void saveTestShouldCallSaveFromRepository() {
       RegionDto givenRegionDto = getRegionDto();

       doAnswer(iom -> new Region())
               .when(regionRepository)
               .save(any());

       regionService.saveRegion(givenRegionDto);

       verify(regionRepository, atLeastOnce()).save(any());
   }

   @Test
   public void deleteTestShouldCallDeleteFromRepository() {
       Region region = getRegion();

       doAnswer(iom -> Optional.of(region))
               .when(regionRepository)
               .findById(any());

       doAnswer(iom -> null)
               .when(regionRepository)
               .delete(any());

       regionService.deleteById(region.getId());

       verify(regionRepository, atLeastOnce()).delete(any());
   }

   @Test
   public void findByIdTestShouldCallFindByIdFromRepository() {
       Region region = getRegion();

       doAnswer(iom -> Optional.of(region))
               .when(regionRepository)
               .findById(any());

       regionService.findById(region.getId());

       verify(regionRepository, atLeastOnce()).findById(any());
   }

   private Region getRegion() {
       Region region = new Region();
       region.setId(1L);
       region.setName(ServiceTestValuesWrapper.REGION_NAME);
       region.setGlobal(true);
       region.setAuthoritySet(new LinkedHashSet<>());
       region.setClinicSet(new LinkedHashSet<>());
       return region;
   }

   private RegionDto getRegionDto() {
       return RegionDto.builder()
               .isGlobal(true)
               .name(ServiceTestValuesWrapper.REGION_NAME)
               .id(1L)
               .build();
   }

   private Object getError(InvocationOnMock invocationOnMock) {
       throw new RuntimeException(String.format("Should not reach %s::%s", invocationOnMock.getMock().getClass().getName(),
               invocationOnMock.getMethod().getName()));
   }
}
