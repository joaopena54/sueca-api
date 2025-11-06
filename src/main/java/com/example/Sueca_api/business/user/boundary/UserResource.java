package com.example.Sueca_api.business.user.boundary;

import com.example.Sueca_api.business.user.control.GetUserProfileInfoUseCase;
import com.example.Sueca_api.business.user.dto.UserProfileDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserResource {

    private final GetUserProfileInfoUseCase getUserProfileInfoUseCase;

    @RolesAllowed("User")
    @GetMapping("/profile")
    public UserProfileDTO getUserProfile(Jwt jwt){

        return  getUserProfileInfoUseCase.execute(jwt);

    }
}

