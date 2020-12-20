package com.example.demo.helpers;

import com.example.demo.models.Article;
import com.example.demo.models.Authority;
import com.example.demo.models.Region;
import com.example.demo.repositories.ArticleRepository;
import com.example.demo.repositories.AuthorityRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.util.LinkedList;
import java.util.List;

@TestComponent
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorityMocker {

    private static Authority getMockedAuthority(Region testRegion) {
        Authority newAuthority = new Authority();
        newAuthority.setAddress("test");
        newAuthority.setCanVerifyCases(false);
        newAuthority.setEmail("test1@test1.com");
        newAuthority.setPassword("test1");
        newAuthority.setName("test");
        newAuthority.setRegion(testRegion);
        return newAuthority;
    }

    public static void addMockedAuthorities(AuthorityRepository repository, Region testRegion) {
        List<Authority> authorities = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            authorities.add(getMockedAuthority(testRegion));
        }
        repository.saveAll(authorities);
    }
}
