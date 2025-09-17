package com.example.ufo_fi.v2.notification.send.application;

import com.example.ufo_fi.v2.notification.send.infrastructure.firebase.dto.request.PushMassageCommand;

public interface WebPushClient {
    void sendMulticast(PushMassageCommand massageCommand);

    void sendUnicast(PushMassageCommand massageCommand);
}
