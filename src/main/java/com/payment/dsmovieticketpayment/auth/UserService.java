package com.payment.dsmovieticketpayment.auth;

import com.payment.dsmovieticketpayment.auth.exceptions.FieldValidationFailed;
import com.payment.dsmovieticketpayment.auth.jwt.JWTUtility;
import com.payment.dsmovieticketpayment.auth.payload.AuthRequestDto;
import com.payment.dsmovieticketpayment.auth.payload.AuthRespondDto;
import com.payment.dsmovieticketpayment.auth.payload.RegisterRequestDto;
import com.payment.dsmovieticketpayment.common.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JWTUtility jwtUtility;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("user name not found"));
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
        return new User(userEntity.getUsername() , userEntity.getPassword() , new ArrayList<>(Arrays.asList(authority)));
    }

    public UserEntity registerUser(RegisterRequestDto request){
        if(userRepository.existsByUsernameIgnoreCase(request.getUserName())){
            throw new FieldValidationFailed("Username Already Exist in the System");
        }

        if(userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new FieldValidationFailed("Email Already Exist in the System");
        }

        UserEntity saveToBe = UserEntity.builder()
                                        .username(request.getUserName())
                                        .password(new BCryptPasswordEncoder().encode(request.getPassword()))
                                        .firstName(request.getFirstName())
                                        .email(request.getEmail())
                                        .phone((request.getPhone()))
                                        .address(request.getAddress())
                                        .role(Role.PAYMENTBOOKER)
                                        .build();

        return userRepository.save(saveToBe);
    }

    public AuthRespondDto loginUser(AuthRequestDto authRequestDto){
        final UserDetails userDetails = loadUserByUsername(authRequestDto.getUserName());
        final String token = jwtUtility.generateToken(userDetails);
        return new AuthRespondDto(token , authRequestDto.getUserName() , userDetails.getAuthorities().stream().findFirst().get().getAuthority());
    }

    public long getAuthUserId(String username){
        UserEntity userEntity = userRepository.findByUsername(username).get();
        return userEntity.getId();
    }

    public String getUserAddressByName(String username){
        UserEntity userEntity = userRepository.findByUsername(username).get();
        return userEntity.getAddress();
    }

    public String getUserPhoneByName(String username){
        UserEntity userEntity = userRepository.findByUsername(username).get();
        return userEntity.getPhone();
    }

}