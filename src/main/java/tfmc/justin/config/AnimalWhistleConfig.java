package tfmc.justin.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

// ==============================================
// Configuration manager for AnimalWhistle
// ==============================================
public class AnimalWhistleConfig {
    
    private final JavaPlugin plugin;
    
    private final String animalWhistlePath;

    private final double detectionRadius;
    private final int glowDuration;
    private final List<String> whitelistedAnimals;
    
    private final String whistleSound;
    private final float soundVolume;
    private final float soundPitch;
    
    public AnimalWhistleConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        
        // Load item paths
        this.animalWhistlePath = plugin.getConfig().getString("items.animal-whistle", "m.pets.animal_whistle");
        
        // Load settings
        this.detectionRadius = plugin.getConfig().getDouble("settings.detection-radius", 50.0);
        this.glowDuration = plugin.getConfig().getInt("settings.glow-duration", 5);
        this.whitelistedAnimals = plugin.getConfig().getStringList("settings.whitelisted-animals");
        
        // Load sound settings
        this.whistleSound = plugin.getConfig().getString("sound.type", "BLOCK_NOTE_BLOCK_FLUTE");
        this.soundVolume = (float) plugin.getConfig().getDouble("sound.volume", 1.0);
        this.soundPitch = (float) plugin.getConfig().getDouble("sound.pitch", 1.5);
    }
    
    public String getAnimalWhistlePath() {
        return animalWhistlePath;
    }
    
    public double getDetectionRadius() {
        return detectionRadius;
    }
    
    public int getGlowDuration() {
        return glowDuration;
    }
    
    // Returns the list of whitelisted entity types (e.g. "HORSE", "DONKEY")
    public List<String> getWhitelistedAnimals() {
        return whitelistedAnimals;
    }
    
    public String getWhistleSound() {
        return whistleSound;
    }
    
    public float getSoundVolume() {
        return soundVolume;
    }
    
    public float getSoundPitch() {
        return soundPitch;
    }
}
