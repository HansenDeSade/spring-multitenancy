package de.hansendesade.multitenancy.auth;

import de.hansendesade.multitenancy.model.ApplicationRole;
import de.hansendesade.multitenancy.model.ApplicationUser;
import de.hansendesade.multitenancy.model.UserOfTenant;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  /**
   * At the moment just dummy users userA and userB
   *
   * @param username
   * @return DummyUser
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (username.equals("userA")) {
      ApplicationRole roleA = new ApplicationRole();
      roleA.setRole("admin");
      ApplicationUser userA = new ApplicationUser();
      userA.setUsername("userA");
      userA.setPassword("$2a$10$dnjVDXlfbkh4AsuhtediOOd/rvoL7ILEwG8sjf5zPtviK8PXT4Wj6");
      userA.setRoles(roleA);
      return new UserOfTenant(userA.getUsername(), userA.getPassword(), getAuthorities(userA), "tenant1");
    } else if (username.equals("userB")){
      ApplicationRole roleB = new ApplicationRole();
      roleB.setRole("admin");
      ApplicationUser userB = new ApplicationUser();
      userB.setUsername("userB");
      userB.setPassword("$2a$10$dnjVDXlfbkh4AsuhtediOOd/rvoL7ILEwG8sjf5zPtviK8PXT4Wj6");
      userB.setRoles(roleB);
      return new UserOfTenant(userB.getUsername(), userB.getPassword(), getAuthorities(userB), "tenant2");
    } else if (username.equals("userC")){
        ApplicationRole roleC = new ApplicationRole();
        roleC.setRole("admin");
        ApplicationUser userC = new ApplicationUser();
        userC.setUsername("userC");
        userC.setPassword("$2a$10$dnjVDXlfbkh4AsuhtediOOd/rvoL7ILEwG8sjf5zPtviK8PXT4Wj6");
        userC.setRoles(roleC);
        return new UserOfTenant(userC.getUsername(), userC.getPassword(), getAuthorities(userC), "tenant3");
    } else {
      throw new UsernameNotFoundException("TODO");
    }
  }

  public Set<GrantedAuthority> getAuthorities(ApplicationUser user) {
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(user.getRoles().getRole()));
    return authorities;
  }
}