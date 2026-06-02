package fudo_backend.service;


import fudo_backend.model.Message;
import fudo_backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Message saveMessage(Message message) {
        message.setIsRead(false);
        return messageRepository.save(message);
    }


    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }


    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }


    public Message markAsRead(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found!"));

        message.setIsRead(true);
        return messageRepository.save(message);
    }


    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}