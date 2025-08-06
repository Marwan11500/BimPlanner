package com.marwan.bimplanner.DTO;

import com.marwan.bimplanner.Entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private User.Role role;
}
