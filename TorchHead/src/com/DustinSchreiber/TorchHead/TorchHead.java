package com.DustinSchreiber.TorchHead;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TorchHead extends JavaPlugin implements Listener{
	
	double pluginVersion = 1.0;
	HashMap<String, PlayerTorch> players = new HashMap<String, PlayerTorch>();
	
    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(this, this);
    		getLogger().info("[WELCOME] TorchHead " + pluginVersion + " enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("[BYE] TorchHead " + pluginVersion + " disabled!");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    		if(cmd.getName().equalsIgnoreCase("torch")){
    			if(!(sender instanceof Player)){
    				sender.sendMessage("This command can only be run by a player.");
    			}else{
    				Player player = (Player) sender;
    				if(!players.containsKey(player.getName())){
    					PlayerTorch newPlayer = new PlayerTorch(player.getName(), true);
    					players.put(player.getName(), newPlayer);
    					return true;
    				}else{
    	    				PlayerTorch pTorch = players.get(sender.getName());
    	    				if(pTorch.isActive()){
    	    					pTorch.torchOff();
    	    					return true;
    	    				}else{
    	    					pTorch.torchOn();
    	    					return true;
    	    				}
    				}
    			}
    		}
    		return false;
    	}
    	
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
    		Player player = event.getPlayer();
    		String playerName = player.getName();
    		if(players.containsKey(playerName)){
    			PlayerTorch pTorch = players.get(playerName);
    			if(pTorch.isActive()){
    				if(player.getInventory().contains(Material.TORCH)){
	    				if(pTorch.getPreviousTorchLoc() != null){
	    					pTorch.getPreviousTorchLoc().getBlock().setTypeId(pTorch.getPreviousTorchBlock());
	    					pTorch.setPreviousTorch(null, -1);
	    				}
	    				Location loc = player.getLocation();
	    				loc.setY(loc.getY() - 1);
	    				int blockBelow = loc.getBlock().getTypeId();
	    				if(blockBelow == 0){
	    					loc.setY(loc.getY() -1);
	    					if(blockBelow == 0){
	    						pTorch.setPreviousTorch(loc, loc.getBlock().getTypeId());
	    						loc.getBlock().setTypeId(89);
	    						return;
	    					}
	    					loc.setY(loc.getY() +1);
						pTorch.setPreviousTorch(loc, loc.getBlock().getTypeId());
						loc.getBlock().setTypeId(89);
						return;
	    				}else{
	    					pTorch.setPreviousTorch(loc, loc.getBlock().getTypeId());
	    					loc.getBlock().setTypeId(89);
	    					return;
	    				}
    				}
    			}
    		}
    }
}