package com.iamneo.hotelhub.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Integer age;
    private String city;
    private String firstName;
    private String lastName;
    private String gender;
    private String country;
    private String phone;
    private String password;
}
