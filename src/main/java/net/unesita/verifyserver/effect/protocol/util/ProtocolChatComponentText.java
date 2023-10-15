package net.unesita.verifyserver.effect.protocol.util;

import lombok.Getter;
import net.unesita.verifyserver.effect.protocol.ProtocolInfo;
import net.unesita.verifyserver.effect.protocol.ProtocolManager;

import java.lang.reflect.InvocationTargetException;

public class ProtocolChatComponentText
        implements ProtocolInfo {

    @Getter
    private final String message;
    private Object component;

    public ProtocolChatComponentText(String message) {
        this.message = message;
        try {
            this.component = ProtocolManager.getInstance().getMinecraftServerInstance("ChatComponentText").getConstructor(String.class).newInstance(message);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getInstance() {
        return component;
    }

    @Override
    public String getName() {
        return "ChatComponentText";
    }

}
