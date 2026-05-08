package com.substring.irctc.config.Security;

import com.substring.irctc.Repository.UserRepo;
import com.substring.irctc.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepo userRepo;

    public CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

User user  = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

CustomUserDetail customUserDetail = new CustomUserDetail(user);
return customUserDetail;

//        if(username.equals("user")) {
//            return User.builder()
//                    .username("user")
//                    .password("user123")
//                    .roles("USER")
//                    .build();
//        }
//
//        throw new UsernameNotFoundException("user not found");
    }
}
