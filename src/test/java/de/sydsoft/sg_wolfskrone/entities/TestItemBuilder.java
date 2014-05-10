/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.libst.util.Console;
import de.sydsoft.sg_wolfskrone.entities.items.armors.Chestplate;
import java.awt.TextArea;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author sythelux
 */
public class TestItemBuilder {

    private static JFrame jf = new JFrame();
    private static JLabel jl = new JLabel();
    
    public static void main(String[] args) {
        //testRandomBuilder();
        testXMLBuilder();
    }
    
    public static void showTooltipText(String HTMLformattedText){
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jl = new JLabel(HTMLformattedText);
        jf.add(jl);
        jf.setSize(640, 480);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
    
    public static void testXMLBuilder(){
        showTooltipText(new ItemBuilder().buildFromXML(0).toTooltipText());
    }
    
    public static void testRandomBuilder(){
        Chestplate[] cps = new Chestplate[]{
            (Chestplate) new ItemBuilder().buildRandomBlue("de.sydsoft.sg_wolfskrone.entities.items.Armors.Chestplate", 10),
            (Chestplate) new ItemBuilder().buildRandomBlue("de.sydsoft.sg_wolfskrone.entities.items.Armors.Chestplate", 5),
            (Chestplate) new ItemBuilder().buildRandomGreen("de.sydsoft.sg_wolfskrone.entities.items.Armors.Chestplate", 10)
        };
        cps[0].setAttribut(Attribute.AGILITY, 8);
        cps[0].setAttribut(Attribute.STRENGTH, 21);
        for (int i = 0; i < cps.length; i++) {
            System.out.println(cps[i].toTooltipText());
        }

//        ItemBuilder testItemBuilder = new ItemBuilder();
        //Item Testitem = testItemBuilder.build();
//        System.out.println("testItem: " + Testitem.toString());        
    }
}
