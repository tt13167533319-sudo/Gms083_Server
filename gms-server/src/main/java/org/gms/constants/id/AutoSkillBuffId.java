package org.gms.constants.id;

import org.gms.client.Character;
import org.gms.client.inventory.Equip;
import org.gms.client.inventory.InventoryType;
import org.gms.client.inventory.Item;
import org.gms.constants.inventory.EquipSlot;
import org.gms.constants.inventory.EquipType;

public class AutoSkillBuffId {
    public static int[] getBuffList(int jobId, Character chr){
        Item wp = chr.getInventory(InventoryType.EQUIPPED).getItem((short) -11);
        switch (jobId) {
            case 100 : return new int[]{1001003}; //战士
            case 110 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        return new int[]{1001003, 1101004, 1101006, 1101007}; //剑客
                    } else if (wpType == EquipType.AXE.getValue() || wpType == EquipType.AXE_2H.getValue()){
                        return new int[]{1001003, 1101005, 1101006, 1101007}; //剑客
                    }
                }
                return new int[]{1001003, 1101006, 1101007}; //剑客
            case 111 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        return new int[]{1001003, 1101004, 1101006, 1101007, 1111002}; //勇士
                    } else if (wpType == EquipType.AXE.getValue() || wpType == EquipType.AXE_2H.getValue()){
                        return new int[]{1001003, 1101005, 1101006, 1101007, 1111002}; //勇士
                    }
                }
                return new int[]{1001003, 1101006, 1101007, 1111002}; //勇士
            case 112 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        if (chr.getSkillLevel(1121010) > 3) return new int[]{1001003, 1101004, 1121010, 1101007, 1111002, 1121000, 1121002}; //葵花宝典
                        else return new int[]{1001003, 1101004, 1101006, 1101007, 1111002, 1121000, 1121002}; //英雄
                    } else if (wpType == EquipType.AXE.getValue() || wpType == EquipType.AXE_2H.getValue()){
                        if (chr.getSkillLevel(1121010) > 3) return new int[]{1001003, 1101005, 1121010, 1101007, 1111002, 1121000, 1121002}; //英雄
                        else return new int[]{1001003, 1101005, 1101006, 1101007, 1111002, 1121000, 1121002}; //英雄
                    }
                }
                return new int[]{1001003, 1101006, 1101007, 1111002, 1121000, 1121002}; //英雄
            case 120 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        return new int[]{1001003, 1201004, 1201006, 1201007}; //准骑士
                    } else if (wpType == EquipType.MACE.getValue() || wpType == EquipType.MACE_2H.getValue()){
                        return new int[]{1001003, 1201005, 1201006, 1201007}; //准骑士
                    }
                }
                return new int[]{1001003, 1201006, 1201007}; //准骑士
            case 121 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        return new int[]{1001003, 1201004, 1201006, 1201007}; //骑士
                    } else if (wpType == EquipType.MACE.getValue() || wpType == EquipType.MACE_2H.getValue()){
                        return new int[]{1001003, 1201005, 1201006, 1201007}; //骑士
                    }
                }
                return new int[]{1001003, 1201006, 1201007}; //骑士
            case 122 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SWORD.getValue() || wpType == EquipType.SWORD_2H.getValue()){
                        return new int[]{1001003, 1201004, 1201006, 1201007, 1221000, 1221002}; //圣骑士
                    } else if (wpType == EquipType.MACE.getValue() || wpType == EquipType.MACE_2H.getValue()){
                        return new int[]{1001003, 1201005, 1201006, 1201007, 1221000, 1221002}; //圣骑士
                    }
                }
                return new int[]{1001003, 1201006, 1201007, 1221000, 1221002}; //圣骑士
            case 130 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SPEAR.getValue()){
                        return new int[]{1001003, 1301004, 1301006, 1301007}; //枪战士
                    } else if (wpType == EquipType.POLEARM.getValue()){
                        return new int[]{1001003, 1301005, 1301006, 1301007}; //枪战士
                    }
                }
                return new int[]{1001003, 1301006, 1301007}; //枪战士
            case 131 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SPEAR.getValue()){
                        return new int[]{1001003, 1301004, 1301006, 1301007, 1311008}; //龙骑士
                    } else if (wpType == EquipType.POLEARM.getValue()){
                        return new int[]{1001003, 1301005, 1301006, 1301007, 1311008}; //龙骑士
                    }
                }
                return new int[]{1001003, 1301006, 1301007, 1311008}; //龙骑士
            case 132 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.SPEAR.getValue()){
                        return new int[]{1301004, 1301007, 1311008, 1321002, 1321000, 1321007}; //黑骑士
                    } else if (wpType == EquipType.POLEARM.getValue()){
                        return new int[]{1301005, 1301007, 1311008, 1321002, 1321000, 1321007}; //黑骑士
                    }
                }
                return new int[]{1301007, 1311008, 1321002, 1321000, 1321007}; //黑骑士
            case 200 : return new int[]{2001002, 2001003}; //魔法师
            case 210 : return new int[]{2001002, 2001003, 2101001}; //火毒法师
            case 211 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.WAND.getValue() || wpType == EquipType.STAFF.getValue()) {
                        return new int[]{2001002, 2001003, 2101001, 2111005}; //火毒巫师
                    }
                }
                return new int[]{2001002, 2001003, 2101001}; //火毒巫师
            case 212 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.WAND.getValue() || wpType == EquipType.STAFF.getValue()) {
                        return new int[]{2001002, 2001003, 2101001, 2111005, 2121000, 2121002, 2121005}; //火毒魔导士
                    }
                }
                return new int[]{2001002, 2001003, 2101001, 2121000, 2121002, 2121005}; //火毒魔导士
            case 220 : return new int[]{2001002, 2001003, 2201001}; //冰雷法师
            case 221 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.WAND.getValue() || wpType == EquipType.STAFF.getValue()) {
                        return new int[]{2001002, 2001003, 2201001, 2211005}; //冰雷巫师
                    }
                }
                return new int[]{2001002, 2001003, 2201001}; //冰雷巫师
            case 222 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.WAND.getValue() || wpType == EquipType.STAFF.getValue()) {
                        return new int[]{2001002, 2001003, 2201001, 2211005, 2221000, 2221002, 2221005}; //冰雷魔导士
                    }
                }
                return new int[]{2001002, 2001003, 2201001, 2221000, 2221002, 2221005}; //冰雷魔导士
            case 230 : return new int[]{2001002, 2001003, 2301003, 2301004}; //牧师
            case 231 : return new int[]{2001002, 2001003, 2301003, 2301004, 2311003, 2311006}; //祭司
            case 232 : return new int[]{2001002, 2001003, 2301003, 2301004, 2311003, 2321000, 2321002, 2321003}; //主教
            case 300 : return new int[]{3001003}; //弓箭手
            case 310 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.BOW.getValue()) {
                        return new int[]{3001003, 3101002, 3101004}; //猎人
                    }
                }
                return new int[]{3001003}; //猎人
            case 311 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.BOW.getValue()) {
                        return new int[]{3001003, 3101002, 3101004, 3111005}; //射手
                    }
                }
                return new int[]{3001003, 3111005}; //射手
            case 312 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.BOW.getValue()) {
                        return new int[]{3001003, 3101002, 3101004, 3121000, 3121002, 3121006, 3121007, 3121008}; //神箭
                    }
                }
                return new int[]{3001003, 3121000, 3121002, 3121006, 3121007, 3121008}; //神箭
            case 320 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CROSSBOW.getValue()) {
                        return new int[]{3001003, 3201002, 3201004}; //弩弓手
                    }
                }
                return new int[]{3001003}; //弩弓手
            case 321 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CROSSBOW.getValue()) {
                        return new int[]{3001003, 3201002, 3201004, 3211005}; //游侠
                    }
                }
                return new int[]{3001003, 3211005}; //游侠
            case 322 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CROSSBOW.getValue()) {
                        return new int[]{3001003, 3201002, 3201004, 3221000, 3221002, 3221005, 3221006}; //箭神
                    }
                }
                return new int[]{3001003, 3221000, 3221002, 3221005, 3221006}; //箭神
//            case 400 : return new int[]{}; //飞侠
            case 410 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CLAW.getValue()) {
                        return new int[]{4101003, 4101004}; //刺客
                    }
                }
                return new int[]{4101004}; //刺客
            case 411 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CLAW.getValue()) {
                        return new int[]{4101003, 4101004, 4111002, 4111001}; //无影人
                    }
                }
                return new int[]{4101004, 4111002, 4111001}; //无影人
            case 412 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.CLAW.getValue()) {
                        return new int[]{4101003, 4101004, 4111002, 4111001, 4121000, 4121006}; //隐士
                    }
                }
                return new int[]{4101004, 4111002, 4111001, 4121000, 4121006}; //隐士
            case 420 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.DAGGER.getValue()) {
                        return new int[]{4201002, 4201003}; //侠客
                    }
                }
                return new int[]{4201003}; //侠客
            case 421 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.DAGGER.getValue()) {
                        return new int[]{4201002, 4201003, 4211003, 4211005}; //独行客
                    }
                }
                return new int[]{4201003, 4211003, 4211005}; //独行客
            case 422 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.DAGGER.getValue()) {
                        return new int[]{4201002, 4201003, 4211003, 4211005, 4221000}; //侠盗
                    }
                }
                return new int[]{4201003, 4211003, 4211005, 4221000}; //侠盗
//            case 500 : return new int[]{}; //海盗
            case 510 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.KNUCKLER.getValue()) {
                        return new int[]{5101006}; //拳手
                    }
                }
                return new int[]{};
            case 511 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.KNUCKLER.getValue()) {
                        return new int[]{5101006}; //斗士
                    }
                }
                return new int[]{};
            case 512 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.KNUCKLER.getValue()) {
                        return new int[]{5101006, 5121000, 5121009}; //冲锋队长
                    }
                }
                return new int[]{5121000, 5121009}; //冲锋队长
            case 520 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.PISTOL.getValue()) {
                        return new int[]{5201003};
                    }
                }
                return new int[]{};
            case 521 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.PISTOL.getValue()) {
                        return new int[]{5201003};
                    }
                }
                return new int[]{};
            case 522 :
                if (wp != null) {
                    int wpType = wp.getItemId() / 1000;
                    if (wpType == EquipType.PISTOL.getValue()) {
                        return new int[]{5201003, 5221000};
                    }
                }
                return new int[]{5221000};
            default : return new int[]{};
        }
    }
}
