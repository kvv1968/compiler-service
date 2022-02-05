package ru.platform.learning.compilerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.platform.learning.compilerservice.entity.Role;
import ru.platform.learning.compilerservice.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;



}
