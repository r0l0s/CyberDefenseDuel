package network;

import org.json.JSONObject;
public class InitialConfigManager {

    private String type = "CONFIG";
    private int initialHp = 100;
    private double baseSpawnRate = 1.0;
    private double baseAttackSpeed = 2.0;
    private int scorePerKill = 10;
    private int difficultyStepScore = 100;
    private double spawnMultiplierPerLevel = 1.15;
    private double speedAddPerLevel = 0.3;

    // Attack Types --------------------------------------------
    private int ddosDamage = 5;
    private int malwareDamage = 8;
    private int credDamage = 10;
    // ---------------------------------------------------------

    public JSONObject getInitConfig(){

        JSONObject damageByType = new JSONObject();
        damageByType.put("DDOS", ddosDamage);
        damageByType.put("MALWARE", malwareDamage);
        damageByType.put("CRED", credDamage);

        JSONObject initialConfig = new JSONObject();
        initialConfig.put("type", type);
        initialConfig.put("initialHp", initialHp);
        initialConfig.put("baseSpawnRate", baseSpawnRate);
        initialConfig.put("baseAttackSpeed", baseAttackSpeed);
        initialConfig.put("scorePerKill", scorePerKill);
        initialConfig.put("difficultyStepScore", difficultyStepScore);
        initialConfig.put("spawnMultiplierPerLevel", spawnMultiplierPerLevel);
        initialConfig.put("speedAddPerLevel", speedAddPerLevel);
        initialConfig.put("damageByType", damageByType);
        return initialConfig;
    }
}
