/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Praproses_Manager {
    private BufferedImage gambar;
    public void binary(BufferedImage gambar){
        this.gambar = gambar;
        
        int l = gambar.getWidth();
        int t = gambar.getHeight();
        
        for(int i=0; i<l;i++){
            for(int j=0;j<t;j++){
                Color c = new Color(gambar.getRGB(i, j));
                
                int red = (int)c.getRed();
                int green = (int)c.getGreen();
                int blue = (int)c.getBlue();
                int nilai = (red+green+blue)/3;
                int n=0;
                if(nilai<95){
                    n=0;
                }
                else{
                    n=255;
                }
                Color newcolor = new Color(n, n, n);
                gambar.setRGB(i, j, newcolor.getRGB());
            }
        }
    }
//    public void grayscale(BufferedImage gambar){
//        this.gambar = gambar;
//        
//        int l = gambar.getWidth();
//        int t = gambar.getHeight();
//        
//        for(int i=0; i<l;i++){
//            for(int j=0;j<t;j++){
//                Color c = new Color(gambar.getRGB(i, j));
//                
//                int red = (int)c.getRed();
//                int green = (int)c.getGreen();
//                int blue = (int)c.getBlue();
//                int nilai = (red+green+blue)/3;
//                
//                Color newcolor = new Color(nilai, nilai, nilai);
//                gambar.setRGB(i, j, newcolor.getRGB());
//            }
//        }
//    }
    
    public BufferedImage getBinary(){
        return gambar;
    }   
    
    public BufferedImage set_Resize(BufferedImage image, int l, int p)throws IOException{
        int w = image.getWidth();
        int h = image.getHeight();
        
        BufferedImage img = new BufferedImage(l, p, image.getType());
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(image, 0, 0, l, p, 0, 0, w, h, null);
        g.dispose();
        return img;        
    }
}
