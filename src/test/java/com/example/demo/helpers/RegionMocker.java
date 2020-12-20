package com.example.demo.helpers;

import com.example.demo.models.Region;
import com.example.demo.models.User;
import com.example.demo.repositories.RegionRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.test.context.TestComponent;

import java.util.LinkedList;
import java.util.List;

@TestComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionMocker {
    private static Region getMockedRegion() {
        Region region = new Region();
        region.setName("MC Region");
        region.setGlobal(false);
        return region;
    }

    public static void addMockedRegions(RegionRepository repository) {
        List<Region> regions = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            regions.add(getMockedRegion());
        }
        repository.saveAll(regions);
    }
}
