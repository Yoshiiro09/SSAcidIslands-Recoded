package me.yoshiro09.acidislands.api.gui.base.player;

import me.yoshiro09.acidislands.api.purifier.PurifyingConduit;
import org.bukkit.entity.Player;

public class PlayerCache {
    private final Player owner;
    private PurifyingConduit editingPurifyingConduit;

    public PlayerCache(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public PurifyingConduit getEditingPurifyingConduit() {
        return editingPurifyingConduit;
    }

    public void setEditingPurifyingConduit(PurifyingConduit editingPurifyingConduit) {
        this.editingPurifyingConduit = editingPurifyingConduit;
    }
}
