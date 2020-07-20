package by.ngrudnitsky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String status;
}