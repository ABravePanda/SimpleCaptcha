package captcha;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import captcha.events.Events;
import captcha.inventory.CaptchaMenu;

public class Main extends JavaPlugin
{
    
    public static ArrayList<UUID> confirmedPlayers = new ArrayList<UUID>();
    public static HashMap<Player, CaptchaMenu> playersInCaptcha = new HashMap<Player, CaptchaMenu>();
    
    @Override
    public void onEnable()
    {
	this.registerEvents();
        super.onEnable();
    }
    
    @Override
    public void onDisable()
    {
        super.onDisable();
    }
    
    public void registerEvents()
    {
	this.getServer().getPluginManager().registerEvents(new Events(this), this);
    }

    

}
