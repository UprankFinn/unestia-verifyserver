package net.unesita.verifyserver.teamspeakbot.entity;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChannelEntity {

    private Client client;
    private String channelName;
    private Integer channelId;

}
