/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.File;
/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Bobot_Entity {
    private File bobot;
    
    public void open(String name){
        bobot = new File(name);
    }
    
    public File getFile(){
        return bobot;
    }
}
