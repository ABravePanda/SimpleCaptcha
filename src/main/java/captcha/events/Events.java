package captcha.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import captcha.Main;
import captcha.inventory.CaptchaMenu;

public class Events implements Listener
{
    private Main plugin;
    
    public Events(Main plugin)
    {
    	this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
	Player p = e.getPlayer();
	
	//check if player is already confirmed
	if(Main.confirmedPlayers.contains(p.getUniqueId())) return;
	if(p.hasPermission("captcha.pass")) return;
	
	CaptchaMenu captcha = new CaptchaMenu();
	captcha.populate();
	Inventory i = captcha.getMenu();
	p.openInventory(i);
	
	//add them to the in captcha list
	Main.playersInCaptcha.put(p, captcha);
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
	Player p = e.getPlayer();
	
	//if player is in captcha, remove them from the list
	if(Main.playersInCaptcha.containsKey(p))
	    Main.playersInCaptcha.remove(p);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
	Player p = e.getPlayer();
	
	if(Main.playersInCaptcha.containsKey(p))
	    e.setCancelled(true);
	
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e)
    {
	Player p = (Player) e.getPlayer();
	
	if(!Main.playersInCaptcha.containsKey(p)) return;
	
	CaptchaMenu captcha = Main.playersInCaptcha.get(p);
	captcha.populate();
	
	Bukkit.getScheduler().runTaskLater(plugin, () -> {
	    	p.openInventory(captcha.getMenu());
	    }, 20L);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e)
    {
	Player p = (Player) e.getWhoClicked();
	if(!Main.playersInCaptcha.containsKey(p)) return;
	e.setCancelled(true);
	
	CaptchaMenu captcha = Main.playersInCaptcha.get(p);
	
	if(e.getCurrentItem() == null)
	    p.kickPlayer(ChatColor.RED + "Failed the captcha. Try again.");
	else if(e.getSlotType() != SlotType.CONTAINER || e.getClickedInventory() != e.getInventory() || e.getCurrentItem().getType() != captcha.getCorrectItem())
	    p.kickPlayer(ChatColor.RED + "Failed the captcha. Try again.");
	else
	{
	    Main.playersInCaptcha.remove(p);
	    Main.confirmedPlayers.add(p.getUniqueId());
	    p.closeInventory();
	    p.sendMessage(ChatColor.GREEN + "You have passed the captcha. You won't need to do it again!");
	}

    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onItemInteract(EntityInteractEvent e)
    {
	if(!(e instanceof Player)) return;
	
	Player p = (Player) e.getEntity();
	if(Main.playersInCaptcha.containsKey(p)) e.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e)
    {
	Player p = e.getPlayer();
	if(Main.playersInCaptcha.containsKey(p)) e.setCancelled(true);
    }

    
    
    
}
