package net.unesita.verifyserver;

import com.google.gson.Gson;
import lombok.Getter;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.Scoreboard;
import net.unesita.verifyserver.command.VerifyCommand;
import net.unesita.verifyserver.effect.particle.Particle;
import net.unesita.verifyserver.effect.particle.ParticleAPI;
import net.unesita.verifyserver.effect.particle.ParticleType;
import net.unesita.verifyserver.inventory.PlayerInventory;
import net.unesita.verifyserver.inventory.VerifyInventory;
import net.unesita.verifyserver.listener.block.BlockBreakListener;
import net.unesita.verifyserver.listener.block.BlockPlaceListener;
import net.unesita.verifyserver.listener.creature.CreatureSpawnListener;
import net.unesita.verifyserver.listener.entity.EntityDamageByEntityListener;
import net.unesita.verifyserver.listener.entity.EntityDamageListener;
import net.unesita.verifyserver.listener.food.FoodLevelChangeListener;
import net.unesita.verifyserver.listener.inventory.InventoryClickListener;
import net.unesita.verifyserver.listener.player.*;
import net.unesita.verifyserver.listener.server.ServerListPingListener;
import net.unesita.verifyserver.listener.weather.WeatherChangeListener;
import net.unesita.verifyserver.mongodb.entity.PlayerEntityHandler;
import net.unesita.verifyserver.scoreboard.ScoreboardManager;
import net.unesita.verifyserver.teamspeakbot.TeamSpeakBot;
import net.unestia.api.UnestiaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class VerifyServer extends JavaPlugin {

    @Getter
    private static VerifyServer instance;

    @Getter
    private final Scoreboard scoreboard;

    @Getter
    private final Gson gson;

    @Getter
    private TeamSpeakBot teamSpeakBot;

    @Getter
    private final ParticleAPI particleAPI;

    @Getter
    private final PlayerEntityHandler playerEntityHandler;

    public static final String PREFIX = "§8[§c§lDEFENCE§8] §r";

    @Getter
    private PlayerInventory playerInventory;
    @Getter
    private VerifyInventory verifyInventory;

    @Getter
    private final ScoreboardManager scoreboardManager;

    public VerifyServer() {
        instance = this;
        this.gson = new Gson();

        this.playerEntityHandler = new PlayerEntityHandler(this);

        this.scoreboardManager = new ScoreboardManager(this);

        this.scoreboard = new Scoreboard();

        this.particleAPI = new ParticleAPI();

        teamSpeakBot = new TeamSpeakBot();
        teamSpeakBot.startTeamSpeakBot();

    }

    @Override
    public void onLoad() {
        File[] files = new File("/home/dependency/").listFiles();
        for (File file : files) {
            try {
                Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                method.setAccessible(true);
                method.invoke(Thread.currentThread().getContextClassLoader(), file.toURI().toURL());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | MalformedURLException exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onEnable() {
        UnestiaAPI.getInstance().getRankManager().getRanks().forEach(ranks -> {
            this.scoreboard.createTeam(ranks.getSortId() + ranks.getName());
            if (ranks.getPrefix() != null) {
                this.scoreboard.getTeam(ranks.getSortId() + ranks.getName()).setPrefix(IChatBaseComponent.ChatSerializer.a(ranks.getColor() + ranks.getPrefix() + " §8| " + ranks.getColor()));
            } else {
                this.scoreboard.getTeam(ranks.getSortId() + ranks.getName()).setPrefix(IChatBaseComponent.ChatSerializer.a("§7" + ranks.getColor()));
            }
        });

        new BlockBreakListener(this);
        new BlockPlaceListener(this);
        new CreatureSpawnListener(this);
        new EntityDamageByEntityListener(this);
        new EntityDamageListener(this);
        new FoodLevelChangeListener(this);
        new InventoryClickListener(this);
        new AsyncPlayerChatListener(this);
        new PlayerChatTabCompleteListener(this);
        new PlayerCommandPreprocessListener(this);
        new PlayerDeathListener(this);
        new PlayerDropItemListener(this);
        new PlayerInteractAtEntityListener(this);
        new PlayerDropItemListener(this);
        new PlayerInteractListener(this);
        new PlayerJoinListener(this);
        new PlayerLoginListener(this);
        new PlayerMoveListener(this);
        new PlayerPickUpItemListener(this);
        new PlayerQuitListener(this);
        new PlayerRespawnListener(this);
        new PlayerSpawnLocationListener(this);
        new WeatherChangeListener(this);
        new ServerListPingListener(this);
        new VerifyCommand(this);

        this.playerInventory = new PlayerInventory(this);
        this.verifyInventory = new VerifyInventory(this);


        loadParticles();

        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, () ->
                VerifyServer.getInstance().getParticleAPI().getDisplayRing().displayFlat(VerifyServer.getInstance().getParticleAPI().getParticle("happy-villager"), new Location(Bukkit.getWorld("world"), -37.5, 186, -40.5, 0, 0), 0, 0D, 2
        ), 20L, 20L);



    }

    @Override
    public void onDisable() {
    }

    public void loadParticles(){
        this.particleAPI.addParticle("mob-ring", new Particle(particleAPI.getData(ParticleType.SPELL_WITCH)));
        this.particleAPI.addParticle("rotator", new Particle(particleAPI.getData(ParticleType.CLOUD)));
        this.particleAPI.addParticle("happy-villager", new Particle(particleAPI.getData(ParticleType.SPELL)));
    }

}
