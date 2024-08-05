package com.skyblock.skyblock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SkyblockStat {

    STRENGTH("❁" , 0.0),
    HEALTH("❤" , 100.0),
    MAX_HEALTH("" , 100.0),
    SPEED("✦" , 100.0),
    MANA("✎" , 100.0),
    MAX_MANA("" , 100.0),
    MINING_SPEED("⸕" , 0.0),
    ATTACK_SPEED("⚔" , 0.0),
    CRIT_CHANCE("☣" , 30.0),
    CRIT_DAMAGE("☠" , 50.0),
    PET_LUCK("♣" , 0),
    MAGIC_FIND("✯" , 0),
    SEA_CREATURE_CHANCE("α" , 0),
    TRUE_DEFENSE("❂" , 0),
    DEFENSE("❈" , 0),
    FEROCITY("⫽" , 0),
    ABILITY_DAMAGE("๑" , 0),
    DAMAGE("" , 0),

    ;

    private final String icon;
    private final double defaultValue;


}
