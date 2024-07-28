package Service;

import Model.Message;
import Model.Account;
import DAO.MessageDao;

import java.util.List;

public class MessageService {
    
    MessageDao messageDao;

    public MessageService() {
        messageDao = new MessageDao();
    }

    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    /**
     * This method adds a message to the database given the account exists,
     * and the message is valid -- not empty and below 256 characters.
     * @param message contains information to add
     * @return the newly added message if successful, otherwise null
     */
    public Message addMessage(Message message) {
        AccountService accountService = new AccountService();
        Account account = accountService.getAccountById(message.getPosted_by());
        if (account != null &&
            message.getMessage_text().length() > 0 &&
            message.getMessage_text().length() <= 255) {
                return messageDao.insertMessage(message);
        }
        return null; 
    }

    /**
     * This method retrieves all messages from the database
     * @return a list of messages
     */
    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    /**
     * This method retrieves a message by id, if exists
     * @param id the message_id
     * @return Message if it exist, otherwise null
     */
    public Message getMessageById(int id) {
        return messageDao.getMessageById(id);
    }

    /**
     * This method deletes a message using the message_id
     * @param id the message_id
     * @return the deleted Message object, if it exist, otherwise null
     */
    public Message deleteMessageById(int id) {
        Message messageToDelete = getMessageById(id);
        if (messageToDelete != null) {
            messageDao.deleteMessage(id);
        }
        return messageToDelete;
    }

    /**
     * This method updates the content of the message given that message exists,
     * and the text is not empty and contains less than 256 characters.
     * @param id message to update
     * @param text new content to put in the message
     * @return the updated Message if successful, otherwise null
     */
    public Message updateMessageById(int id, String text) {
        if (getMessageById(id) != null &&
            !text.isEmpty() &&
            text.length() <= 255) {
                messageDao.updateMessage(id, text);
                return getMessageById(id);
        }
        return null; 
    }

    /**
     * This method retrieves a list of all messages by a particular user
     * @param userId contains the user id
     * @return a list of all messages if successful, otherwise null
     */
    public List<Message> getAllMessagesByUser(int userId) {
        return messageDao.getAllMessagesByUser(userId);
    }
}
