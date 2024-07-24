package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
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
     */
    private void registerNewUserHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void verifyUserLoginHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getMessageByIdHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByIdHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void updateMessageByIdHandler(Context ctx) {

    }

    /**
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllMessagesByUserHandler(Context ctx) {

    }

}