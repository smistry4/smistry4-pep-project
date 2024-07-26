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

    public List<Message> getAllMessages() {
        return messageDao.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDao.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        Message messageToDelete = getMessageById(id);
        if (messageToDelete != null) {
            messageDao.deleteMessage(id);
        }
        return messageToDelete;
    }

    public Message updateMessageById(int id, String text) {
        if (getMessageById(id) != null &&
            !text.isEmpty() &&
            text.length() <= 255) {
                messageDao.updateMessage(id, text);
                return getMessageById(id);
        }
        return null; 
    }

    public List<Message> getAllMessagesByUser(int userId) {
        return messageDao.getAllMessagesByUser(userId);
    }
}
