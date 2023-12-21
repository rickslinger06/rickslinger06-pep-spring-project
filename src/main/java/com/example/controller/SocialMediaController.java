package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;


    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    /**
     * @param account
     * @return account object and status code 200 if register is successfull status code 409 otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        if (accountService.usernameAlreadyExists(account)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        //register the account if all validation is satisfied
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.status(HttpStatus.OK)
                .body(registeredAccount);
    }

     /**
     * @param account
     * @return authenticatedAccount if log in is successfull
     */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){

        //check if username and password exists on database
        if(!accountService.existsByUsernameAndPassword(account.getUsername(), account.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(account);
        }

        //find account by username and return the account onject if found
        Account authenticatedAccount = accountService.findByUsername(account.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(authenticatedAccount);
    }

    /**
     * 
     * @param message
     * @return status code 400 if message_text is blank ,characters over 255 and if user is not in database.
     * otherwise return status code 200 and message object
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message validation not satisfied");
        }
    
        if (!messageService.IsMessageInDB(message.getPosted_by())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist in the database");
        }
        
        Message validatedMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(validatedMessage);
    }

    /**
     * 
     * @return status code 200 and list of all messages
     */

    @GetMapping("/messages")
    public ResponseEntity <List<Message>> getAllMessages(){

        List<Message> list = messageService.gellAllMessages();

        return ResponseEntity.status(HttpStatus.OK).body(list);
        
    }

    /**
     * 
     * @param id
     * @return Satus code 200 and message object if found and return empty otherwise
     */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);
    
            return ResponseEntity.status(HttpStatus.OK).body(message);
       
    }

    /**
     * @param account_id
     * @return Status code 200 and list of messages by account_id
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getListOfMessageByUser(@PathVariable int account_id){

        List<Message> messageList = messageService.getMessagerByUserId(account_id);

        return ResponseEntity.status(HttpStatus.OK).body(messageList);
       
    }

    /**
     * 
     * @param message_id
     * @return status code 200 if message is successfully deleted and empty body if message doent exist
     */
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable int message_id){
    
        int rowsAffected = messageService.deleteMessageById(message_id);
    
        if(rowsAffected > 0){
            return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
        }
    
         // Resource not found, 200 with empty body
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * 
     * @param message_id
     * @param message
     * @return satus code 200 if row affected is greater than 0
     */
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<?> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        String newMessageText = message.getMessage_text();
        
        if (newMessageText != null && !newMessageText.isBlank() && newMessageText.length() <= 255) {
            int rowsAffected = messageService.updateMessage(message_id, newMessageText);
    
            // 200 OK with rowsAffected in the response body
            if (rowsAffected > 0) {
                return ResponseEntity.ok(rowsAffected); 
            }
        }
        // 400 Bad Request
        return ResponseEntity.badRequest().build(); 
    }
    
    
    

 
    
}
