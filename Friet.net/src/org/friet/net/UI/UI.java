package org.friet.net.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author arne
 */
public class UI {
    private static final Color geel = new Color(255,229,0);
    private static final Color orangje = new Color(81,70,0);
    private static final Color grijs = new Color(75,75,75);
    private static final Color grijs2 = new Color(145,145,145);
    
    public static void setUIFont (FontUIResource f, ColorUIResource c){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            
            if (value != null && value instanceof FontUIResource){
                UIManager.put (key, f);
            }
            if (value != null && key.toString().contains("foreground")){
                UIManager.put (key, c);
            }
            if (key.toString().startsWith("OptionPane")&& value != null){
                    System.out.println(key + " --- " + value.toString());
            }
        }
    } 
    
    public static List list(ColorUIResource c){
        List arr = new ArrayList();
        arr.add(1);arr.add(1);arr.add(c);arr.add(c);arr.add(c);
        return arr;
    }
    
    public static void setLAF(){
        
        //menubar
        UIManager.put("MenuBar.background", new ColorUIResource(new Color(0,0,0)));
        UIManager.put("MenuBar.gradient", list(new ColorUIResource(new Color(0,0,0))));
        UIManager.put("MenuBar.borderColor", new ColorUIResource(geel));
        UIManager.put("MenuBar.border", new BBorder());
        
        //menu
        UIManager.put("Menu.borderPainted", false);
        UIManager.put("Menu.selectionBackground", new ColorUIResource(geel));
        
        //paneel
        UIManager.put("Panel.background", new ColorUIResource(new Color(0,0,0)));
        
        //Option pane
        UIManager.put("OptionPane.background", new ColorUIResource(new Color(0,0,0)));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(new Color(255,255,255)));
        
        //Button
        UIManager.put("Button.gradient", list(new ColorUIResource(grijs)));
        UIManager.put("Button.border", new ButtonBorder());
        UIManager.put("Button.borderColor", new ColorUIResource(geel));
        UIManager.put("Button.enabledBorderColor", new ColorUIResource(orangje));
        UIManager.put("Button.disabledText", new ColorUIResource(new Color(10,10,10)));
        
        //tabbedPane
        UIManager.put("TabbedPane.borderHightlightColor",new ColorUIResource(orangje));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(new Color(10,10,10)));
        UIManager.put("TabbedPane.focus", new ColorUIResource(new Color(10,10,10)));
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(4,4,4,4));
        UIManager.put("TabbedPane.unselectedBackground", new ColorUIResource(grijs2));
        UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(4,4,4,4));
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(grijs));
        UIManager.put("TabbedPane.selected", new ColorUIResource(grijs));
        UIManager.put("TabbedPane.tabInsets", new Insets(4,4,4,4));
        
        //ScrollPane
        UIManager.put("ScrollPane.border", new ScrollBorder());
        UIManager.put("ScrollPane.background", new ColorUIResource(grijs));
        UIManager.put("ScrollPane.foreground", new ColorUIResource(grijs));
        UIManager.put("ScrollPane.borderColor", new ColorUIResource(orangje));
        
        //TableHeader
        UIManager.put("TableHeader.cellBorder", new NoBorder());
        UIManager.put("TableHeader.background", new ColorUIResource(grijs));
        
        //Table
        UIManager.put("Table.background", new ColorUIResource(grijs2));
        UIManager.put("Table.gridColor", new ColorUIResource(grijs));
        
        //font
        setUIFont(new FontUIResource("Monospaced",Font.PLAIN,12), new ColorUIResource(Color.white));
    }
    
    
}