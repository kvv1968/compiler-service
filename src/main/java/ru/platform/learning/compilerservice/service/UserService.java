package ru.platform.learning.compilerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.platform.learning.compilerservice.entity.User;
import ru.platform.learning.compilerservice.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }


}
