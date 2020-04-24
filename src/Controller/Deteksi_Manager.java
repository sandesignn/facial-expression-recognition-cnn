/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Deteksi_Manager {
    private static Rect crop;
    private String lokasi;
    
    public  void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }
    
    public void deteksiWajah(String location){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier deteksi;
        deteksi = new CascadeClassifier("D:/NewFER/haarcascade_frontalface_alt.xml");
        Mat img = Highgui.imread(lokasi);
        MatOfRect detek = new MatOfRect();
        deteksi.detectMultiScale(img, detek);        
        
        for(Rect rect:detek.toArray()){
            Core.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            crop = new Rect(rect.x, rect.y, rect.width, rect.height);
        }
        
        Mat imgout = new Mat(img, crop);
        Highgui.imwrite(location, new Mat(img, crop));
    }
}
