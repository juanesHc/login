package com.example.login.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterPersonRequestDto {

    @NotBlank(message = "Given name is required")
    @Size(min = 2, max = 20)
    private String givenName;

    @NotBlank(message = "Family name is required")
    @Size(min = 2, max = 20)
    private String familyName;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long and include uppercase, lowercase, number and special character"
    )
    private String password;

    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|googlemail\\.com)$",
            message = "Only Google email accounts are allowed"
    )
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^3\\d{9}$",
            message = "Phone must be a valid Colombian mobile number (3XXXXXXXXX)"
    )
    private String phone;
}
