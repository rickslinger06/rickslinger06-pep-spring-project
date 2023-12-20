package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.MessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;



@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
   public MessageService(MessageRepository messageRepository,AccountRepository accountRepository) {
    this.messageRepository = messageRepository;
    this.accountRepository = accountRepository;
}

/**
 * 
 * @param message
 * @return message object that was persist to the database
 */
public Message createMessage(Message message) {
    return messageRepository.save(message);
}

/**
 * 
 * @param id
 * @return true if the user id exists on the database
 */
public boolean IsMessageInDB(long id){
   
    // finds the list of all acounts
  List<Account> allAccounts = accountRepository.findAll();
    
  //looping all acounts and check if account_id is equal to the parameter id return true if found and false otherwise
  for(Account i : allAccounts){
    if(id == i.getAccount_id()){
        return true;
    }
  }
  return false;
 }

 /**
  * 
  * @return list of messages
  */
public List<Message> gellAllMessages() {
    
    List<Message> findAllMessages = messageRepository.findAll();
    if(findAllMessages.isEmpty()){
        throw new MessageException("No message found");
    }
    return findAllMessages;
}

/**
 * 
 * @param id
 * @return Message object if found and return empty otherwise
 */
public Message getMessageById(int message_id) {
    
    return messageRepository.findMessageById(message_id);
   
}

public List<Message> getMessagerByUserId(int account_id) {

    return messageRepository.findAllMessagesByPostedBy(account_id);
}
 

}
