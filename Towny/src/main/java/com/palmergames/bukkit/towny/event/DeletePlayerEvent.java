package com.palmergames.bukkit.towny.event;

import com.palmergames.bukkit.towny.object.Resident;

import org.bukkit.Warning;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/**
 * This event is no longer called.
 * @deprecated since 0.99.6.4 use {@link com.palmergames.bukkit.towny.event.resident.DeletePlayerEvent} instead.
 */
@Deprecated
@Warning(reason = "Event is no longer called. Event has been moved to the com.palmergames.bukkit.towny.event.resident package.")
public class DeletePlayerEvent extends TownyObjDeleteEvent {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DeletePlayerEvent(Resident resident) {
        super(resident.getName(), resident.getUUID(), resident.getRegistered());
    }

    /**
     * @return the deleted player's name.
     */
    public String getPlayerName() {
        return name;
    }

	/**
	 * @return deleted player's uuid. Can be null. 
	 */
	public UUID getPlayerUUID() {
    	return uuid;
	}

	/**
	 * @return when the deleted player first was registered by Towny (in ms).
	 */
	public long getRegistered() {
    	return registered;
	}
}
