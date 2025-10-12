package com.example.usersservice.service;

import com.example.usersservice.DTOs.UserCreateDTO;
import com.example.usersservice.DTOs.UserDTO;
import com.example.usersservice.model.User;
import com.example.usersservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .orElse(null);
    }

    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        User user = modelMapper.map(userCreateDTO, User.class);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDTO.class); // ovde se vraca modelMapper.map jer ce sada savedUser da ima sacuvan id, dok to userCreateDTO nije imao?
    }

    public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(userCreateDTO.getName());
                    existingUser.setEmail(userCreateDTO.getEmail());

                    User updatedUser = userRepository.save(existingUser);
                    return modelMapper.map(updatedUser, UserDTO.class);
                }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);

                    return true;
                }).orElse(false);
    }
}
