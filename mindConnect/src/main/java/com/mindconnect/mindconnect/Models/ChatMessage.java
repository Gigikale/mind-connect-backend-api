package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "chat_messages")
@Table(name = "chat_messages")
@Getter
@Setter
public class ChatMessage extends BaseEntity {

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(
            name = "sender_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "chat_sender_fkey")
    )
    private User sender;

    @ManyToOne
    @JoinColumn(
            name = "receiver_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "chat_receiver_fkey")
    )
    private User receiver;

}
