package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.entity.User;
import com.project.DynamicFormBuilderSystem.repository.UserRepository;
import com.project.DynamicFormBuilderSystem.request.PasswordUpdateRequest;
import com.project.DynamicFormBuilderSystem.util.FindAuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleteUser() {

        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(isTheLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin cannot delete itself");
        }

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();

        if(!isPasswordCorrect(user.getPassword(), passwordUpdateRequest.getOldPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST," Current password is incorrect");
        }

        if(!isNewPasswordConfirmed(passwordUpdateRequest.getNewPassword(),
                passwordUpdateRequest.getNewPassword2()     )){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match");
        }

        if(!isNewPasswordDifferent(passwordUpdateRequest.getOldPassword(), passwordUpdateRequest.getNewPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Old and new passwords must be different");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));

        userRepository.save(user);
    }

    public boolean isPasswordCorrect(String currentPassword, String oldPassword){
        return passwordEncoder.matches(oldPassword,currentPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String newPasswordConfirmation){
        return newPassword.equals(newPasswordConfirmation);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword){

        return !oldPassword.equals(newPassword);
    }

    private boolean isTheLastAdmin(User user){
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority ->  "ROLE_ADMIN".equals(authority.getAuthority()));

        if(isAdmin){
            long adminCount = userRepository.countAdminUsers();
            return adminCount <= 1;
        }

        return false;
    }
}
