package com.example.moty.demo.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moty.demo.annotation.RequireAuth;
import com.example.moty.demo.annotation.RequireRole;
import com.example.moty.demo.model.dto.ApiResponse;

import com.example.moty.demo.model.po.Role;
import com.example.moty.demo.model.po.User;
import com.example.moty.demo.repo.UserRepository;
import com.example.moty.demo.util.ApiResponseUtil;
import com.example.moty.demo.util.JWTProviderUtil;

import lombok.Data;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JWTProviderUtil tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // Check if username already exists
        if (userRepository.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest()
                .body(ApiResponseUtil.badRequest("Username already exists"));
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Use BCrypt to encode password
        user.setEnabled(true);
        
        // Set default roles (can be adjusted as needed)
        Set<Role> roles = new HashSet<>();
        // Default role logic can be added here
        user.setRoles(roles);

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registration successful");
        response.put("username", user.getUsername());
        return ResponseEntity.ok(ApiResponseUtil.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Use raw password for authentication directly, Spring Security will automatically use BCryptPasswordEncoder for verification
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String token = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(ApiResponseUtil.success(response));
    }
    
    /**
     * Get current user information - login required
     */
    @GetMapping("/profile")
    @RequireAuth(message = "Please login first")
    public ApiResponse<Map<String, Object>> getUserProfile() {
        // Current user information can be retrieved and returned here
        Map<String, Object> profile = new HashMap<>();
        profile.put("message", "User information retrieved successfully");
        profile.put("timestamp", System.currentTimeMillis());
        return ApiResponseUtil.success(profile);
    }
    
    /**
     * Admin-only endpoint - get all user list
     */
    @GetMapping("/admin/users")
    @RequireRole(value = "ROLE_ADMIN", message = "Admin permission required to view user list")
    public ApiResponse<Map<String, Object>> getAllUsers() {
        long userCount = userRepository.count();
        Map<String, Object> data = new HashMap<>();
        data.put("message", "User list retrieved successfully");
        data.put("totalUsers", userCount);
        data.put("timestamp", System.currentTimeMillis());
        return ApiResponseUtil.success(data);
    }
}

@Data
class LoginRequest {
    private String username;
    private String password;
}

@Data
class RegisterRequest {
    private String username;
    private String password;
}
