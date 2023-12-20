package com.example.service;

import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.UserAlreadyExistException;
import com.example.exception.UserIsBlankException;
import com.example.exception.UserPasswordException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) {
    
        if(account.getUsername().isBlank()){
            throw new UserIsBlankException("Username cannot be blank");
        }
        if(account.getPassword().length() < 4){
            throw new UserPasswordException("Password must be more than 4 characters long");
        }

        return accountRepository.save(account);


}

public boolean usernameAlreadyExists(Account account){
   
    return accountRepository.existsByUsername(account.getUsername());
}

public boolean existsByUsernameAndPassword(String username, String password) {
    
    return accountRepository.existsByUsernameAndPassword(username, password);
}

public Account findByUsername(String username) {
    return accountRepository.findByUsername(username);
}



}
