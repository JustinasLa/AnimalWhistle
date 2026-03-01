package tfmc.justin.listeners;

import me.Plugins.TLibs.Objects.API.ItemAPI;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.block.Action;
import tfmc.justin.config.AnimalWhistleConfig;

import java.util.List;

// ==============================================
// Handles animal whistle interaction
// ==============================================
public class WhistleInteractListener implements Listener {
    
    private final JavaPlugin plugin;
    private final ItemAPI api;
    private final AnimalWhistleConfig config;
    
    public WhistleInteractListener(JavaPlugin plugin, ItemAPI api, AnimalWhistleConfig config) {
        this.plugin = plugin;
        this.api = api;
        this.config = config;
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Only right-click
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        
        // Dont do anything if item is null
        if (item == null) {
            return;
        }
        
        // Check if the item is an animal whistle using TLibs
        if (!isAnimalWhistle(item)) {
            return;
        }
        
        // Play whistle sound for all nearby players
        playWhistleSound(player);
        
        // Get all nearby entities
        List<Entity> nearbyEntities = player.getNearbyEntities(
            config.getDetectionRadius(),
            config.getDetectionRadius(),
            config.getDetectionRadius()
        );
        
        List<String> whitelist = config.getWhitelistedAnimals();
        
        // Apply glowing effect to all nearby whitelisted animals
        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            
            // Check if entity type is in the whitelist (case-insensitive)
            String entityTypeName = entity.getType().name();
            boolean isWhitelisted = whitelist.isEmpty() ||
                whitelist.stream().anyMatch(name -> name.equalsIgnoreCase(entityTypeName));
            
            if (isWhitelisted) {
                LivingEntity animal = (LivingEntity) entity;
                
                // Apply glowing effect for the configured duration (converted to ticks)
                int durationTicks = config.getGlowDuration() * 20;
                PotionEffect glowEffect = new PotionEffect(
                    PotionEffectType.GLOWING,
                    durationTicks,
                    0,
                    false,
                    false
                );
                
                animal.addPotionEffect(glowEffect);
            }
        }
    }
    
    // ==============================================
    // Check if an item is an animal whistle using TLibs
    // ==============================================
    private boolean isAnimalWhistle(ItemStack item) {
        try {
            ItemStack whistleTemplate = api.getCreator().getItemFromPath(config.getAnimalWhistlePath());
            if (whistleTemplate == null) {
                return false;
            }
            return item.isSimilar(whistleTemplate);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to validate animal whistle: " + e.getMessage());
            return false;
        }
    }
    
    // ==============================================
    // Play whistle sound at player location
    // ==============================================
    private void playWhistleSound(Player player) {
        try {
            Sound sound = Sound.valueOf(config.getWhistleSound());
            player.getWorld().playSound(
                player.getLocation(),
                sound,
                SoundCategory.AMBIENT,
                config.getSoundVolume(),
                config.getSoundPitch()
            );
        } catch (IllegalArgumentException e) {
            plugin.getLogger().warning("Invalid sound type in config: " + config.getWhistleSound());
        }
    }
}
