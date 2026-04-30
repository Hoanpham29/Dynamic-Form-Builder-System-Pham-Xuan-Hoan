package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.PasswordUpdateRequest;

public interface UserService {
    void deleteUser();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
