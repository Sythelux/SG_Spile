/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities.items;

import de.sydsoft.sg_wolfskrone.entities.Item;
import de.sydsoft.sg_wolfskrone.entities.ItemBuilder;
import java.util.ResourceBundle;

/**
 *
 * @author sythelux
 */
public abstract class Weapon extends Item {

    protected int handling = 0;//0=singlehand,onehanded;1=twohanded;2=mainhand;3=secuhand
    protected WeaponPrefix prefix;

    public Weapon(ItemBuilder builder) {
        super(builder);
        this.prefix = builder.getWeaponPrefix();
        this.handling = builder.getHandling();
    }
    
    public Weapon(ItemSuffix suffix, WeaponPrefix prefix, ItemRareNess rareNess) {
        super(suffix, rareNess);
        this.prefix = prefix;
    }

    public boolean isTwoHanded() {
        return (handling == 1) ? true : false;
    }

    @Override
    public String toString() {
        return "ItemID#" + id + " "+ prefix.getName() + ResourceBundle.getBundle("ItemName").getString(name).toLowerCase() + " " + suffix.getName();
    }

    public WeaponPrefix getPrefix() {
        return prefix;
    }
    
}
