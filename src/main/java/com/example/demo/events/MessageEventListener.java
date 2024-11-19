package com.example.demo.events;

import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class MessageEventListener extends AbstractMongoEventListener<Message> {

    private final MessageService messageService;

    @Autowired
    public MessageEventListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Message> event) {
        Message message = event.getSource();

        message.setDelivered(true);
        message.setStatus("SENT");

        messageService.updateMessage(message);
        super.onAfterSave(event);
    }
}
