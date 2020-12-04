package com.drrotstein.cp.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.drrotstein.cp.UltimateChat;

public class SlowModeCountdown extends Countdown {

	
	int seconds;
	Player player;
	
	boolean free = true;
	
	
	public SlowModeCountdown(Player player, int seconds) {
		this.seconds = seconds;
		this.player = player;
	}
	
	
	@Override
	public void start() {
		free = false;
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(UltimateChat.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(seconds == 0) stop();
				seconds--;
			}
		}, 0, 20);
	}

	@Override
	public void stop() {
		Bukkit.getScheduler().cancelTask(taskID);
		free = true;
	}
	
	
	public boolean isFree() {
		return free;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getSeconds() {
		return seconds;
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
}
