package com.dnsite.security.user.service;

import com.dnsite.history.service.HistoryService;
import com.dnsite.security.user.model.Role;
import com.dnsite.security.user.model.User;
import com.dnsite.security.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HistoryService historyService;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRegistrationDate(new Date());
        user.setLastLoginDate(new Date());
        userRepository.save(user);
        historyService.save("USER", "INSERT");
    }

    @Override
    public void updateUserRole(Long id, Role role){
        User user = userRepository.getOne(id);
        user.setRole(role.getAuthority());
        userRepository.save(user);
        historyService.save("USER", "UPDATE");
    }

    @Override
    public void deleteUserById(Long id){
        User user = userRepository.getOne(id);
        userRepository.delete(user);
        historyService.save("USER", "DELETE");
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public String generateTemporaryPassword(String username){
        User user = findByUsername(username);
        String tempPassword = alphaNumericString();
        user.setPassword(bCryptPasswordEncoder.encode(tempPassword));
        user.setRegistrationDate(new Date());
        user.setLastLoginDate(new Date());
        userRepository.save(user);

        return tempPassword;
    }


    private static String alphaNumericString() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuwxyz";
        Random rnd = new Random();
        int len = rnd.nextInt(25);
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}