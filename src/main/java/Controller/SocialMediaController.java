package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::registerNewUserHandler);
        app.post("login", this::verifyUserLoginHandler);
        app.post("messages", this::postMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);

        return app;
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void registerNewUserHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountToCreate = mapper.readValue(ctx.body(), Account.class);
        Account createdAccount = accountService.addUserAccount(accountToCreate);
        if (createdAccount != null) {
            ctx.json(mapper.writeValueAsString(createdAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void verifyUserLoginHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account accountToVerify = mapper.readValue(ctx.body(), Account.class);
        Account verifiedAccount = accountService.processUserLogin(accountToVerify);
        if (verifiedAccount != null) {
            ctx.json(mapper.writeValueAsString(verifiedAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void postMessageHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message messageToAdd = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(messageToAdd);
        if (addedMessage != null) {
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {
        List<Message> allMessages = messageService.getAllMessages();
        ctx.json(allMessages);
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(id);
        if (message != null) {
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.deleteMessageById(id);
        if (message != null) {
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message updatedMessage = messageService.updateMessageById(id, message.getMessage_text());
        if (updatedMessage != null) {
            ctx.json(mapper.writeValueAsString(updatedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByUserHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getAllMessagesByUser(id));
    }

}