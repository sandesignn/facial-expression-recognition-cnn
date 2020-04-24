 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Gambar_Entity;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Gambar_Manager {
    private String lokasi, gambar[];
    private BufferedImage image,img, resize;
    private int[] labeltrain;
    private File file;
    
    Gambar_Entity ge;
    Praproses_Manager pm;
    Deteksi_Manager dm;
    
    public Gambar_Manager(){
        ge = new Gambar_Entity();
        pm = new Praproses_Manager();
        dm = new Deteksi_Manager();
    }
    
    public void setDirektori_Train(String direktori) throws IOException{
        Scanner input = new Scanner(direktori);
        String dir = input.nextLine();
        ge.setImage(dir);
    }
    
    public String getDirektori_Train(){
        lokasi = ge.getImage();
        return lokasi;
    } 
    
    public void loadImage_Test(File file) throws IOException{
        img = ImageIO.read(file);
        ge.setImg(img);
    }
    
    public BufferedImage getImage_Test(){
        img = ge.getImg();
        return img;
    }
    
    public String[] getImageGray(){
        gambar = ge.gambarGray();
        return gambar;
    }
    
    public String[] getImageDeteksi(){
        gambar = ge.gambarDeteksi();
        return gambar;
    }
    
     public String[] getImageResize(){
        gambar = ge.gambarResize();
        return gambar;
    }
    
    public void deteksiTrain(){
        for(int i=0;i<100;i++){
            dm.setLokasi(ge.getGrayTrain()+(i+1)+".jpg");
            dm.deteksiWajah(ge.getDeteksiTrain()+(i+1)+".jpg");
        }
    }
    
    public void deteksiTest(){
        dm.setLokasi(ge.gGrayTest());
        dm.deteksiWajah(ge.gDeteksiTest());
    }
    
    public String gambarDeteksiTest(){
        String gambar = ge.gDeteksiTest();
        return gambar;
    }
    
    public String[] gambar32x32(){
        gambar = ge.gambarResize();
        return gambar;
    }
    
    public void resizeImage() throws IOException{        
        String[] nmfileDeteksi = new String[100];
        String[] nmfileResize = new String[100];
        
        nmfileDeteksi = getImageDeteksi();
        nmfileResize = getImageResize();
        
        for(int i=0;i<100;i++){
            img = ImageIO.read(ge.openFile(nmfileDeteksi[i]));
            resize = pm.set_Resize(img, 32, 32);
            ImageIO.write(resize,"JPG", new File(nmfileResize[i]));
        }
    }
    
    public int[][] inputTraining(String input) throws IOException{        
        BufferedImage img = ImageIO.read(ge.openFile(input));
        
        int w = img.getWidth();
        int h = img.getHeight();
        int[][] image = new int[w][h];
        int i,j;
        
        for(i=0;i<w;i++){
            for(j=0;j<h;j++){
                image[i][j]=img.getRGB(i, j)&0xFF;
//                System.out.print(image[i][j]+" ");
            }
//            System.out.println("");
        }return image;    
    }
    
     public int[][] inputTesting(BufferedImage input) throws IOException{        
        int w = input.getWidth();
        int h = input.getHeight();
        int[][] image = new int[w][h];
        int i,j;
        
        for(i=0;i<w;i++){
            for(j=0;j<h;j++){
                image[i][j]=input.getRGB(i, j)&0xFF;
            }
        }return image;    
    }
    
    public void setLabel_Train()throws FileNotFoundException,IOException{
        int i;
        String baris, nama;
        labeltrain = new int[100];
        
        nama = "D:/NewFER/data latih/label/new_labels.txt";
        file = new File(nama);
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<100;i++){
            if((baris = dis.readLine()) != null){
                labeltrain[i] = Integer.parseInt(baris);
            }
        }in.close();
    }
    
    public int[] getLabel_Train(){
        return labeltrain;
    }
}
