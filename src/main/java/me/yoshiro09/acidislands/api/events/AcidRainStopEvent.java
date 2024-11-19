package me.yoshiro09.acidislands.api.events;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AcidRainStopEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final World world;

    public AcidRainStopEvent(World world){
        this.world = world;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public World getWorld() {
        return world;
    }
}
