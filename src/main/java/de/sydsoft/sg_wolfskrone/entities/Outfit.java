/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.entities.chars.PlayerCharacter;
import de.sydsoft.sg_wolfskrone.entities.items.Armor;
import de.sydsoft.sg_wolfskrone.entities.items.armors.*;
import de.sydsoft.sg_wolfskrone.entities.items.Weapon;

/**
 *
 * @author sythelux
 */
public class Outfit {

    private Chestplate chestplate;
    private Cape cape;
    private Helmet helmet;
    private Weapon mainHand;
    private Weapon secuHand;

    public Item add(PlayerCharacter chara, Armor item) {
        //if (chara.isEquipAble(item)) {//TODO: dev skills
        switch (item.getClass().getSimpleName().toLowerCase()) {
            case "chestplate":
                item = (Armor) equip(chara, item, chestplate);
                chestplate = (Chestplate) item; //TODO: evtl. clone
                break;
            case "cape":
                item = (Armor) equip(chara, item, cape);
                cape = (Cape) item;
                break;
            case "helmet":
                item = (Armor) equip(chara, item, helmet);
                helmet = (Helmet) item;
                break;
        }
        //}
        return item;
    }

    public Item add(PlayerCharacter chara, Weapon weapon) {
        Weapon help = null;
        if (chara.isEquipAble(weapon)) {
            if (weapon.isTwoHanded()) {
                if (secuHand == null) {
                    help = mainHand;
                    chara.getAttributs().removeAll(mainHand.getAttributs());
                    mainHand = weapon;
                } else if (mainHand == null) {
                    help = secuHand;
                    chara.getAttributs().removeAll(secuHand.getAttributs());
                    secuHand = weapon;
                }
                chara.getAttributs().addAll(weapon.getAttributs());
            }
        }
        return help;
    }

    private Item equip(PlayerCharacter chara, Item swapper, Item swapped) {
        Item helpI = null;
        if (swapped == null) {
            helpI = swapper;
            chara.getAttributs().addAll(swapper.getAttributs());
        } else {
            helpI = swapped;
            chara.getAttributs().removeAll(swapped.getAttributs());
            swapped = swapper;
            chara.getAttributs().addAll(swapper.getAttributs());
        }
        return helpI;
    }

    public boolean remove() {
        return false;
    }

    public static Outfit deSerialize(String serializedString) {
        Outfit o = new Outfit();
        return o;
    }

    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chestplate{");
        if(chestplate != null)sb.append(chestplate.serialize());
        sb.append("};Cape{");
        if(cape != null)sb.append(cape.serialize());
        sb.append("};Helmet{");
        if(helmet != null)sb.append(helmet.serialize());
        sb.append("};MainHand{");
        if(mainHand != null)sb.append(mainHand.serialize());
        sb.append("};SecuHand{");
        if(secuHand != null)sb.append(secuHand.serialize());
        sb.append("}");
        return sb.toString();
    }
}
