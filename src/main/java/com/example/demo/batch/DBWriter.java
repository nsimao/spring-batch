package com.example.demo.batch;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<User> {
// ------------------------------ FIELDS ------------------------------

    private UserRepository userRepository;

// --------------------------- CONSTRUCTORS ---------------------------

    public DBWriter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void write(List<? extends User> users) throws Exception {
        System.out.println("Data Saved for Users: " + users);
        userRepository.saveAll(users);
    }
}
