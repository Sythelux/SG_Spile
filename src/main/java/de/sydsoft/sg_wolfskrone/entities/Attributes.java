/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sydsoft.sg_wolfskrone.entities;

import de.sydsoft.sg_wolfskrone.util.LocHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dbft111asc
 */
public class Attributes extends HashMap<Attribute, Float> {

    public void add(Attribute key, Float value) {
        if (!super.containsKey(key)) {
            super.put(key, value);
        } else {
            super.put(key, value + super.get(key));
        }
    }

    public void addAll(Attributes attrs) {
        for (Attribute key : attrs.keySet()) {
            //System.out.println(attrs.get(key) + "+" + super.get(key) + "=" + (attrs.get(key) + ((super.get(key) != null) ? super.get(key) : 0f)));
            super.put(key, attrs.get(key) + ((super.get(key) != null) ? super.get(key) : 0f));
        }
    }

    public void remove(Attribute key, Float value) {
        if (super.containsKey(key)) {
            super.put(key, super.get(key) - value);
        }
    }

    public void removeAll(Attributes attrs) {
        for (Attribute key : attrs.keySet()) {
            //System.out.println(attrs.get(key) + "+" + super.get(key) + "=" + (attrs.get(key) + ((super.get(key) != null) ? super.get(key) : 0f)));
            super.put(key, ((super.get(key) != null) ? super.get(key) : 0f) - attrs.get(key));
        }
    }

    public String toTooltipText() {
        List sortedList = new ArrayList();
        sortedList.addAll(super.keySet());
        Collections.sort(sortedList);
        StringBuilder strB = new StringBuilder();
        for (Attribute key : (ArrayList<Attribute>) sortedList) {
            strB.append(key.getName()).append(": ").append(LocHelper.roundTwoDecimals(super.get(key))).append("<br />");
        }
        return strB.toString();
    }

    @Override
    public String toString() {
        List sortedList = new ArrayList();
        sortedList.addAll(super.keySet());
        Collections.sort(sortedList);
        StringBuilder strB = new StringBuilder();
        for (Attribute key : (ArrayList<Attribute>) sortedList) {
            strB.append(key.getName()).append(": ").append(super.get(key)).append("\r\n");
        }
        return strB.toString();
    }
    
    public static Attributes deSerializer(String serializedString){
        Attributes a = new Attributes();
        String attrS = serializedString.substring(serializedString.indexOf("Attributes{"), serializedString.indexOf("}"));
        serializedString = serializedString.replace(attrS, "");
        for (String string : attrS.split(";")) {
            a.add(Attribute.getByName(string.split(":")[0]), Float.parseFloat(string.split(":")[1]));
        }
        return a;
    }
    
    public String serialize(){
        StringBuilder sb = new StringBuilder();
        for (Attribute key : this.keySet()) {
            sb.append(key.name()).append(":").append(this.get(key));
            sb.append(";");
        }
        if (sb.lastIndexOf(";")>-1) {
            sb.replace(sb.lastIndexOf(";"),sb.length(),"");
        }
        return sb.toString();
    }
}
