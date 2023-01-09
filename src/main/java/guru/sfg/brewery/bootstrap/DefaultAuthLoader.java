/*
 *  Copyright 2020 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package guru.sfg.brewery.bootstrap;

import guru.sfg.brewery.domain.security.Authority;
import guru.sfg.brewery.domain.security.RoleEnum;
import guru.sfg.brewery.domain.security.User;
import guru.sfg.brewery.services.AuthorityService;
import guru.sfg.brewery.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * Created by jt on 2019-01-26.
 */
@RequiredArgsConstructor
@Component
@Log4j2
public class DefaultAuthLoader implements CommandLineRunner {
  private final UserService userService;
  private final AuthorityService authorityService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) {
    if (userService.count() == 0) {
      loadUserData();
    }
  }

  private void loadUserData() {
    Authority adminAuthority = Authority.builder().role(RoleEnum.ADMIN).build();
    Authority userAuthority = Authority.builder().role(RoleEnum.USER).build();
    Authority customerAuthority = Authority.builder().role(RoleEnum.CUSTOMER).build();

    authorityService.saveAuthorities(List.of(adminAuthority, userAuthority, customerAuthority));

    User spring = User.builder().username("spring").password(passwordEncoder.encode("test1")).authority(adminAuthority).build();
    User user = User.builder().username("user").password(passwordEncoder.encode("test2")).authority(userAuthority).build();
    User scott = User.builder().username("scott").password(passwordEncoder.encode("test3")).authority(customerAuthority).build();

    userService.saveUsers(List.of(spring, user, scott));

    log.debug("Users created: " + userService.count());
  }
}
