package vttp.day22.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.day22.models.User;
import vttp.day22.repositories.UserRepository;

//Service for Controller
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public boolean authenticate(User user) {
        boolean result = userRepo.authenticate(user);
        return result;
    }

    public boolean createUser(final User user) throws Exception {
        int count = userRepo.createUser(user);
        System.out.printf("Insert count: %d\n", count);
        return count > 0;
        //For the sql query, if user is created, it will return a integer
        //If count is more than 1, createUser will return true
    }
    
}