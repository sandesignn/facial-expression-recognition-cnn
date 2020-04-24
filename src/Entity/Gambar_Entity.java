/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Gambar_Entity {
    private String fimage, img;
    private String gambar[] = new String[213];
    private File image;
    BufferedImage fgambar;
    //****************Training***************//
    public void setImage(String fimage) {
        this.fimage = fimage;
    }

    public String getImage() {
        return fimage;
    }
    
    public File openFile(String img){
        image = new File(img);
        return image;
    }
        
    public String getGrayTrain(){
        img = "D:/NewFER/data latih/praproses/";
        return img;
    }
    
    public String getDeteksiTrain(){
        img = "D:/NewFER/data latih/deteksi/";
        return img;
    }
    
    public String[] gambarGray(){
        for(int i=0;i<213;i++){
            gambar[i] = "D:/NewFER/data latih/praproses/"+(i+1)+".jpg";
        }return gambar;
    }
    
    public String[] gambarDeteksi(){
        String gmbr[] = new String[213];
        for(int i=0;i<213;i++){
            gmbr[i] = "D:/NewFER/data latih/deteksi/"+(i+1)+".jpg";
        }return gmbr;
    }
    
    public String[] gambarResize(){
        for(int i=0;i<213;i++){
            gambar[i] = "D:/NewFER/data latih/resize/"+(i+1)+".jpg";
        }return gambar;
    }  
    
    //*****************TESTING****************//
    public void setImg(BufferedImage fgambar){
        this.fgambar = fgambar;
    }
    
    public BufferedImage getImg(){
        return fgambar;
    }
    
    public String gGrayTest(){
        img = "D:/NewFER/data uji/praproses.jpg";
        return img;
    }
    
    public String gDeteksiTest(){
        img = "D:/NewFER/data uji/deteksi.jpg";
        return img;
    }
    
}
