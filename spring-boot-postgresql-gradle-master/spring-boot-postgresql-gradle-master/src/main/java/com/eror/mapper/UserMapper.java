package com.eror.mapper;

import com.eror.dto.UserDTO;
import com.eror.entity.User;
import org.springframework.stereotype.Component;

@Component
public final class UserMapper {
    public User DTOtoUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public UserDTO userToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }
}
