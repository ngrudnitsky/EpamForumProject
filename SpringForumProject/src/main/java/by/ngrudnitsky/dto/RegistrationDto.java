package by.ngrudnitsky.dto;

import by.ngrudnitsky.entity.User;
import lombok.Data;

@Data
public class RegistrationDto {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User toUser(){
        User user = new User();
        user.setUsername(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public static RegistrationDto fromUser(User user) {
        RegistrationDto regUserDto = new RegistrationDto();

        regUserDto.setUserName(user.getUsername());
        regUserDto.setFirstName(user.getFirstName());
        regUserDto.setLastName(user.getLastName());
        regUserDto.setEmail(user.getEmail());
        regUserDto.setPassword(user.getPassword());

        return regUserDto;
    }

}
