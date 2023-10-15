package net.unesita.verifyserver.teamspeakbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import lombok.Getter;
import net.unesita.verifyserver.teamspeakbot.entity.ChannelEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TeamSpeakBot {

    @Getter
    private TS3Api ts3Api;
    @Getter
    private TS3Query ts3Query;

    @Getter
    public static final Map<Client, ChannelEntity> channelByClient = new ConcurrentHashMap<>();
    @Getter
    public static List<Client> clients;

    public void startTeamSpeakBot() {
        TS3Config ts3Config = new TS3Config();
        ts3Config.setHost("91.218.67.114");
        //ts3Config.setHost(UnestiaBot.getHostName());
        ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        ts3Query = new TS3Query(ts3Config);
        ts3Query.connect();

        ts3Api = ts3Query.getApi();
        //ts3Api.login(UnestiaBot.getUsername(), UnestiaBot.getPassword());
        ts3Api.login("serveradmin", "W-mmUvXhmPcCdUHL-SjUQf8EGHn-#ScF");

        ts3Api.selectVirtualServerById(1);
        //ts3Api.setNickname(UnestiaBot.getName());
        ts3Api.setNickname("Unestia Network");
        ts3Api.registerAllEvents();
        this.addListener();
    }

    public void addListener() {
        ts3Api.addTS3Listeners(new TS3Listener() {
            @Override
            public void onTextMessage(TextMessageEvent textMessageEvent) {
            }

            @Override
            public void onClientJoin(ClientJoinEvent clientJoinEvent) {
                Client client = ts3Api.getClientInfo(clientJoinEvent.getClientId());
                ts3Api.sendPrivateMessage(clientJoinEvent.getClientId(), "Herzlich willkommen, auf dem unestia.net TeamSpeak!");

                if (ts3Api.getChannelInfo(33).getName().equals("┏ Support » Closed")) {
                    int supporterOnline = (int) ts3Api.getClients().stream().filter(clients -> client.isInServerGroup(31)).count();
                    if (supporterOnline != 0) {
                        Map<ChannelProperty, String> channelProperties = new HashMap<>();
                        channelProperties.put(ChannelProperty.CHANNEL_NAME, "┏ Support » Open");
                        channelProperties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "5");

                        ts3Api.editChannel(33, channelProperties);
                    }
                }
            }

            @Override
            public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {
                if (ts3Api.getChannelInfo(33).getName().equals("┏ Support » Open")) {
                    int supporterOnline = (int) ts3Api.getClients().stream().filter(clients -> clients.isInServerGroup(31)).count();
                    if (supporterOnline == 0) {
                        Map<ChannelProperty, String> channelProperties = new HashMap<>();
                        channelProperties.put(ChannelProperty.CHANNEL_NAME, "┏ Support » Closed");
                        channelProperties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "0");

                        ts3Api.editChannel(33, channelProperties);
                    }
                }
            }

            @Override
            public void onServerEdit(ServerEditedEvent serverEditedEvent) {

            }

            @Override
            public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {
            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {
            }

            @Override
            public void onClientMoved(ClientMovedEvent clientMovedEvent) {
                Client client = ts3Api.getClientInfo(clientMovedEvent.getClientId());
                if (clientMovedEvent.getTargetChannelId() == 33) {
                    boolean hasSupportNotifications = false;
                    for (Client notificationClients : ts3Api.getClients())
                        if (notificationClients.isInServerGroup(31)) {
                            hasSupportNotifications = true;
                            ts3Api.sendPrivateMessage(notificationClients.getId(), "Es befindet sich ein User (" + client.getNickname() + ") im Support, welcher Hilfe benötigt!");
                        }
                    if (hasSupportNotifications) {
                        ts3Api.sendPrivateMessage(client.getId(), "Es wurden Supporter informiert, welche sich schnellstmöglich um dein Anliegen kümmern werden!");
                        Map<ChannelProperty, String> properties = new HashMap<>();
                        properties.put(ChannelProperty.CHANNEL_FLAG_TEMPORARY, "1");
                        properties.put(ChannelProperty.CHANNEL_DELETE_DELAY, "3");
                        properties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "1");

                        Integer supportChannel = 128;
                        properties.put(ChannelProperty.CPID, String.valueOf(supportChannel));

                        //ORDNER:
                        //properties.put(ChannelProperty.CHANNEL_ORDER, String.valueOf(supportChannel));

                        String channelName = "● Support » " + client.getNickname();

                        ts3Api.createChannel(channelName, properties);
                        ts3Query.getApi().moveQuery(50);

                        if (!TeamSpeakBot.channelByClient.containsKey(client))
                            TeamSpeakBot.channelByClient.put(client, new ChannelEntity(client, channelName, supportChannel));
                        ts3Api.moveClient(client.getId(), ts3Api.getChannelByNameExact(channelName, true).getId());

                    } else {
                        ts3Api.sendPrivateMessage(client.getId(), "Aktuell befindet sich kein Supporter auf dem TeamSpeak, welcher sich um dein Anliegen kümmern könnte!");
                    }
                }

            }

            @Override
            public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {
            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

            }

            @Override
            public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {
            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {
            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

            }
        });
    }

}
