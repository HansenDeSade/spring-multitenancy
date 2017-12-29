package de.evelopment.multitenancy.auth;

import de.evelopment.multitenancy.model.ApplicationRole;
import de.evelopment.multitenancy.model.ApplicationUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
      return new User(userA.getUsername(), userA.getPassword(), getAuthorities(userA));
    } else if (username.equals("userB")){
      ApplicationRole roleB = new ApplicationRole();
      roleB.setRole("admin");
      ApplicationUser userB = new ApplicationUser();
      userB.setUsername("userA");
      userB.setPassword("123");
      userB.setRoles(roleB);
      return new User(userB.getUsername(), userB.getPassword(), getAuthorities(userB));
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