package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.PasswordUpdateRequest;
import com.project.DynamicFormBuilderSystem.response.UserResponse;

public interface UserService {
    void deleteUser();
    UserResponse getUserInfo();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
