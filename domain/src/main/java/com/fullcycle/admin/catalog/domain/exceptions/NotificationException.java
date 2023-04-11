package com.fullcycle.admin.catalog.domain.exceptions;

import com.fullcycle.admin.catalog.domain.validation.handler.Notification;

public class NotificationException extends DomainException{
    public NotificationException(String aMessage, final Notification notification) {
        super(aMessage,notification.getErrors());
    }
}
