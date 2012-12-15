package com.DustinSchreiber.TorchHead;

import org.bukkit.Location;

public class PlayerTorch {

	private String playerName;
	private boolean active = false;
	private Location oldTorch = null;
	private int oldBlock = -1;
	
	public PlayerTorch(String name, boolean state){
		playerName = name;
		active = state;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public String getPlayerName(){
		return playerName;
	}
	
	public Location getPreviousTorchLoc(){
		return oldTorch;
	}
	
	public int getPreviousTorchBlock(){
		return oldBlock;
	}
	
	public void setPreviousTorch(Location oldTorch, int oldBlock){
		this.oldTorch = oldTorch;
		this.oldBlock = oldBlock;
	}
	
	public void torchOn(){
		active = true;
	}
	
	public void torchOff(){
		if(oldTorch != null){
			oldTorch.getBlock().setTypeId(oldBlock);
		}
		active = false;
	}
}