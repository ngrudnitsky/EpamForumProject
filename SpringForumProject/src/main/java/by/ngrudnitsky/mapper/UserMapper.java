package by.ngrudnitsky.mapper;

import by.ngrudnitsky.dto.RegistrationDto;
import by.ngrudnitsky.dto.UserDto;
import by.ngrudnitsky.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updated", expression = "java(java.time.Instant.now())")
    @Mapping(target = "status", ignore = true)
    User mapFromRegistrationDto(RegistrationDto registrationDto);

    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updated", expression = "java(java.time.Instant.now())")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "userId", source = "id")
    User mapFromUserDto(UserDto userDto);

    @Mapping(target = "id", source = "userId")
    UserDto mapToDto(User user);
}
