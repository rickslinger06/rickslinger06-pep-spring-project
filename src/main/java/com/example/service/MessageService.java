package com.example.service;

import java.util.ArrayList;
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

/**
 * 
 * @param account_id
 * @return list of messages by posted_id
 */
public List<Message> getMessagerByUserId(int account_id) {

    List<Message> allMessage = messageRepository.findAll();

    List<Message> messageByUser = new ArrayList<>();

    for(Message i : allMessage){
        if(i.getPosted_by() == account_id){
           messageByUser.add(i);
        }
    }
    return messageByUser;
}

/**
 * 
 * @param message_id
 * @return and int of number of rows affected if messages has been successfully deleted, return 0 id message is not found
 */
public int deleteMessageById(int message_id) {
    Message messageFound = messageRepository.findMessageById(message_id);
    if (messageFound != null) {
        messageRepository.delete(messageFound);
        // Assuming 1 row affected for successful deletion
        return 1; 
    }
    // No rows affected if the message was not found
    return 0; 
}

public void updateMessage(Message messageFound) {
}

public int updateMessage(int message_id, String newMessage) {

    Message messageFound = messageRepository.findMessageById(message_id);

    if(messageFound != null){
        messageFound.setMessage_text(newMessage);
        messageRepository.save(messageFound);
        return 1;

    }
    return 0;
}




}
