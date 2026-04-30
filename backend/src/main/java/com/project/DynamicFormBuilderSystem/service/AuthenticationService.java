package com.project.DynamicFormBuilderSystem.service;

import com.project.DynamicFormBuilderSystem.request.AuthenticationRequest;
import com.project.DynamicFormBuilderSystem.request.RegisterRequest;
import com.project.DynamicFormBuilderSystem.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
