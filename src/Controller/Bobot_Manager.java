/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Bobot_Entity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Kurnia Sandi Pratama
 */
public class Bobot_Manager {
    private double [][][] bobot_C1 = new double[6][3][3];
    private double [] bobot_S2 = new double[6];
    private double [][][][] bobot_C3 = new double[6][16][3][3];
    private double [] bobot_S4 = new double[16];
    private double [][][] bobot_C5 = new double[16][36][120];
    private double [][] bobot_F6 = new double[120][84];
    private double [][] target = new double[4][84];
    
    private double [] bias_C1 = new double[6];
    private double [] bias_S2 = new double[6];
    private double [] bias_C3 = new double[16];
    private double [] bias_S4 = new double[16];
    private double [] bias_C5 = new double[120];
    private double [] bias_F6 = new double[84];
    
    private File fbobot;
    private Bobot_Entity be;
    
    private int x,y,i,j,s2,p,q,index;
    private char[] charArr;
    private String baris;
    File file;
    
    public Bobot_Manager(){
        be = new Bobot_Entity();
    }
    
    public void setBobotBias_Random() throws IOException{
        String fbobot[] = new String[12];
        fbobot=getParameter();
        
        setBobot_C1();
        getBobot_C1();
        tulisBobot_C1(fbobot[0]);
        
        setBias_C1();
        getBias_C1();
        tulisBias_C1(fbobot[1]);
        
        setBobot_S2();
        getBobot_S2();
        tulisBobot_S2(fbobot[2]);
        
        setBias_S2();
        getBias_S2();
        tulisBias_S2(fbobot[3]);
        
        setBobot_C3();
        getBobot_C3();
        tulisBobot_C3(fbobot[4]);
        
        setBias_C3();
        getBias_C3();
        tulisBias_C3(fbobot[5]);
        
        setBobot_S4();
        getBobot_S4();
        tulisBobot_S4(fbobot[6]);
        
        setBias_S4();
        getBias_S4();
        tulisBias_S4(fbobot[7]);
        
        setBobot_C5();
        getBobot_C5();
        tulisBobot_C5(fbobot[8]);
        
        setBias_C5();
        getBias_C5();
        tulisBias_C5(fbobot[9]);
        
        setBobot_F6();
        getBobot_F6();
        tulisBobot_F6(fbobot[10]);
        
        setBias_F6();
        getBias_F6();
        tulisBias_F6(fbobot[11]);
    }
           
    private double rand(){
        double a = (double)(-1.0);
        double z = (double)(1.0);
        double range = z-a;
        double random = (double)((double)(Math.random()*range)+a);
        return random;
    }

    //inisialisasi S2 ke C3
    public String[] koneksi() { //hubungan antara feature map S2 dan C3
        String[] koneksi = new String[16];
        
        koneksi[0] = "012";
        koneksi[1] = "123";
        koneksi[2] = "234";
        koneksi[3] = "345";
        koneksi[4] = "045";
        koneksi[5] = "015";
        koneksi[6] = "0123";
        koneksi[7] = "1234";
        koneksi[8] = "2345";
        koneksi[9] = "0345";
        koneksi[10] = "0145";
        koneksi[11] = "0125";
        koneksi[12] = "0134";
        koneksi[13] = "1245";
        koneksi[14] = "0235";
        koneksi[15] = "012345";
        
        return koneksi;
    }    
    
    //Set dan Get Bobot Bias
    public void setBobot_C1(){        
        for(x=0;x<6;x++){
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    bobot_C1[x][i][j] = rand();
                }
            }
        }
    }
    
    public double[][][] getBobot_C1(){
        return bobot_C1;
    }
    
    public void setBias_C1(){
        for(x=0;x<6;x++){
            bias_C1[x] = rand();
        }
    }
    
    public double[] getBias_C1(){
        return bias_C1;
    }    
    
    public void setBobot_S2(){
        for(x=0;x<6;x++){
            bobot_S2[x] = rand();
        }
    }
    
    public double[] getBobot_S2(){
        return bobot_S2;
    }
    
    public void setBias_S2(){
        for(x=0;x<6;x++){
            bias_S2[x] = rand();
        }
    }
    
    public double[] getBias_S2(){
        return bias_S2;
    }
    
    public void setBobot_C3(){
        int p;
        String[] koneksi = koneksi();
        
        for(x=0;x<16;x++){//jumlah deep atau kedalaman
            charArr = koneksi[x].toCharArray();//baca array koneksi
            for(s2=0;s2<charArr.length;s2++){//baca array koneksi ke-i
                p = Integer.parseInt(Character.toString(charArr[s2]));//convert char ke int
                for(i=0;i<3;i++){ //baris bobot
                    for(j=0;j<3;j++){ //kolom bobot
                        bobot_C3[p][x][i][j] = rand(); //input kedalam array multidimensi
                    }
                }
            }
        }
    }
    
    public double[][][][] getBobot_C3(){
        return bobot_C3;
    }
    
    public void setBias_C3(){
        for(x=0;x<16;x++){
            bias_C3[x] = rand();
        }
    }
    
    public double[] getBias_C3(){
        return bias_C3;
    }
    
    public void setBobot_S4(){
        for(x=0;x<16;x++){
            bobot_S4[x] = rand();
        }
    }
    
    public double[] getBobot_S4(){
        return bobot_S4;
    }
    
    public void setBias_S4(){
        for(x=0;x<16;x++){
            bias_S4[x] = rand();
        }
    }
    
    public double[] getBias_S4(){
        return bias_S4;
    }
    
    public void setBobot_C5(){
        for(x=0;x<120;x++){ //jumlah kedalaman
            for(i=0;i<16;i++){//baris
                for(j=0;j<36;j++){//kolom
                    bobot_C5[i][j][x] = rand(); //total 16000 bobot
                }
            }
        }
    }
    
    public double[][][] getBobot_C5(){
        return bobot_C5;
    }
    
    public void setBias_C5(){
        for(x=0;x<120;x++){
            bias_C5[x] = rand();
        }
    }
    
    public double[] getBias_C5(){
        return bias_C5;
    }
    
    public void setBobot_F6(){
        for(i=0;i<84;i++){// jumlah kedalaman
            for(j=0;j<120;j++){ //array bobot 
                bobot_F6[j][i] = rand(); 
            }
        }
    }
    
    public double[][] getBobot_F6(){
        return bobot_F6;
    }
    
    public void setBias_F6(){
        for(x=0;x<84;x++){
            bias_F6[x] = rand();
        }
    }
    
    public double[] getBias_F6(){
        return bias_F6;
    }
    
    //--------------------Target--------------------------------//
    private String[] target(){
        String target[] = new String[4];
        target[0]="000011111111111111111111111111111111111111111111111111111111111111111111111111111111";
        target[1]="000000000000111111111111111111111111111111111111111111111111111111111111111111111111";
        target[2]="000000000000000000001111111111111111111111111111111111111111111111111111111111111111";
        target[3]="000000000000000000000000000011111111111111111111111111111111111111111111111111111111";
        
        
        return target;
    }
    
    public void setTarget(){
        int[] val;
        String q;
        String temp[] = target();
        
        for(i=0; i<4; i++){
            char p[] = temp[i].toCharArray();
            val = new int[84];
            
            for(x=0; x<val.length; x++){
                q = Character.toString(p[x]);
                val[x] = Integer.parseInt(q);
            }
            //System.out.println(val.length);
            for(j=0; j<val.length; j++){
                if(val[j]==1){
                    target[i][j] = (double) -1.0;
                }else{
                    target[i][j] = (double) 1.0;
                }
            }
        }
    }
    
    public double[][] getTarget(){
        return (target);
    }
    
    //-------------------Tulis Bobot dan Bias---------------------//
    public void tulisBobot_C1(String fname) throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotC1 = new PrintWriter(in);
        
        for(x=0;x<6;x++){
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    bobotC1.println(Double.valueOf(bobot_C1[x][i][j]));
                }
            }
        }in.close();
    }
    
    public void tulisBias_C1(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasC1 = new PrintWriter(in);
        
        for(i=0;i<6;i++){
            biasC1.println(Double.valueOf(bias_C1[i]));
        }in.close();
    }
    
    public void tulisBobot_S2(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotS2 = new PrintWriter(in);
        
        for(i=0;i<6;i++){
            bobotS2.println(Double.valueOf(bobot_S2[i]));
        }in.close();
    }
    
    public void tulisBias_S2(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasS2 = new PrintWriter(in);
        
        for(i=0;i<6;i++){
            biasS2.println(Double.valueOf(bias_S2[i]));
        }in.close();
    }
    
    public void tulisBobot_C3(String fname)throws FileNotFoundException, IOException{
        String[] hasil = koneksi();
        
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotC3 = new PrintWriter(in);
        
        for(x=0;x<16;x++){
            charArr = hasil[x].toCharArray();
            for(y=0;y<charArr.length;y++){
                index = Integer.parseInt(Character.toString(charArr[y]));
                for(i=0;i<3;i++){
                    for(j=0;j<3;j++){
                        bobotC3.println(Double.valueOf(bobot_C3[index][x][i][j]));
                    }
                }
            }
        }in.close();
    }
    
    public void tulisBias_C3(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasC3 = new PrintWriter(in);
        
        for(i=0;i<16;i++){
            biasC3.println(Double.valueOf(bias_C3[i]));
        }in.close();
    }
    
    public void tulisBobot_C5(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotC5 = new PrintWriter(in);
        
        for(x=0;x<120;x++){
            for(i=0;i<16;i++){
                for(j=0;j<36;j++){
                    bobotC5.println(Double.valueOf(bobot_C5[i][j][x]));
                }
            }
        }in.close();
    }
    
    public void tulisBobot_S4(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotS4 = new PrintWriter(in);
        
        for(i=0;i<16;i++){
            bobotS4.println(Double.valueOf(bobot_S4[i]));
        }in.close();
    }
    
    public void tulisBias_S4(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasS4 = new PrintWriter(in);
        
        for(i=0;i<16;i++){
            biasS4.println(Double.valueOf(bias_S4[i]));
        }in.close();
    }
    
    public void tulisBias_C5(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasC5 = new PrintWriter(in);
        
        for(i=0;i<120;i++){
            biasC5.println(Double.valueOf(bias_C5[i]));
        }in.close();
    }
    
    public void tulisBobot_F6(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter bobotF6 = new PrintWriter(in);
        
        for(i=0;i<84;i++){
            for(j=0;j<120;j++){
                bobotF6.println(Double.valueOf(bobot_F6[j][i]));
            }
        }in.close();
    }
    
    public void tulisBias_F6(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileWriter in = new FileWriter(file);
        PrintWriter biasF6 = new PrintWriter(in);
        
        for(i=0;i<84;i++){
            biasF6.println(Double.valueOf(bias_F6[i]));
        }in.close();
    }
    
    //----------------------------Baca Bobot dan bias-----------------------//
    public void bacaBobot_C1(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(x=0;x<6;x++){
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    if((baris = dis.readLine())!=null){
                        bobot_C1[x][i][j] = Double.parseDouble(baris);
                    }
                }
            }
        }in.close();
    }
    
    public void bacaBias_C1(String fname)throws FileNotFoundException, IOException{
        int i;
        String baris;
        File file;
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<6;i++){
            if((baris = dis.readLine())!=null){
                bias_C1[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBobot_S2(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<6;i++){
            if((baris = dis.readLine())!=null){
                bobot_S2[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBias_S2(String fname)throws FileNotFoundException, IOException{
        File file;
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<6;i++){
            if((baris = dis.readLine())!=null){
                bias_S2[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBobot_C3(String fname)throws FileNotFoundException, IOException{
        String[] hasil = koneksi();
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(x=0;x<16;x++){
            charArr = hasil[x].toCharArray();
            for(y=0;y<charArr.length;y++){
                index = Integer.parseInt(Character.toString(charArr[y]));
                for(i=0;i<3;i++){
                    for(j=0;j<3;j++){
                        if((baris=dis.readLine())!=null){
                            bobot_C3[index][x][i][j] = Double.parseDouble(baris);
                        }                        
                    }
                }
            }
        }in.close();
    }
    
    public void bacaBias_C3(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<16;i++){
            if((baris = dis.readLine())!=null){
                bias_C3[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBobot_S4(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<16;i++){
            if((baris = dis.readLine())!=null){
                bobot_S4[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBias_S4(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<16;i++){
            if((baris = dis.readLine())!=null){
                bias_S4[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBobot_C5(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(x=0;x<120;x++){
            for(i=0;i<16;i++){
                for(j=0;j<36;j++){
                    if((baris = dis.readLine())!=null){
                        bobot_C5[i][j][x] = Double.parseDouble(baris);
                    }
                }
            }
        }in.close();
    }
    
    public void bacaBias_C5(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<120;i++){
            if((baris = dis.readLine())!=null){
                bias_C5[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    public void bacaBobot_F6(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<84;i++){
            for(j=0;j<120;j++){
                if((baris = dis.readLine())!=null){
                    bobot_F6[j][i] = Double.parseDouble(baris);
                }
            }
        }in.close();
    }
    
    public void bacaBias_F6(String fname)throws FileNotFoundException, IOException{
        be.open(fname);
        file = be.getFile();
        
        FileReader in = new FileReader(file);
        BufferedReader dis = new BufferedReader(in);
        
        for(i=0;i<84;i++){
            if((baris = dis.readLine())!=null){
                bias_F6[i] = Double.parseDouble(baris);
            }
        }in.close();
    }
    
    //----------------------Simpan Bobot Bias----------------------//
    public void simpanBobot_C1(String name, double[][][] bobot_C1){
        int x,i,j;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<6;x++){
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    temp.append(bobot_C1[x][i][j]);
                    temp.append(System.getProperty("line.separator"));
                }
            }
        }
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter output = new BufferedWriter(fstream);
            output.write(temp.toString());
            output.close();
        } catch (Exception e) {
           // System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_C1(String name, double[] bias_C1){
        int x;
        StringBuffer temp = new StringBuffer();
        for(x=0;x<6;x++){
            temp.append(bias_C1[x]);
            temp.append(System.getProperty("line.separator"));
        }
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter output = new BufferedWriter(fstream);
            output.write(temp.toString());
            output.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBobot_C3(String name, double[][][][] bobot_C3){
        int index,x,i,j,s2;
        char[] charArr;
        StringBuffer temp = new StringBuffer();
        String[] koneksi = koneksi();
        
        for(x=0;x<16;x++){
            charArr = koneksi[x].toCharArray();
            for(s2=0;s2<charArr.length;s2++){
                index = Integer.parseInt(Character.toString(charArr[s2]));
                for(i=0;i<3;i++){
                    for(j=0;j<3;j++){
                        temp.append(bobot_C3[index][x][i][j]);
                        temp.append(System.getProperty("line.separator"));
                    }
                }
            }
        }
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBobot_S2(String name, double[] bobot_S2){
        int x;
        StringBuffer temp = new StringBuffer();
        for(x=0;x<6;x++){
            temp.append(bobot_S2[x]);
            temp.append(System.getProperty("line.separator"));
        }
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter output = new BufferedWriter(fstream);
            output.write(temp.toString());
            output.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_S2(String name, double[] bias_S2){
        int x;
        StringBuffer temp = new StringBuffer();        
        for(x=0;x<6;x++){
            temp.append(bias_S2[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try
        {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        }catch(Exception e){
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_C3(String name, double[] bias_C3){
        int x;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<16;x++){
            temp.append(bias_C3[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
     public void simpanBobot_S4(String name, double[] bobot_S4){
        int x;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<16;x++){
            temp.append(bobot_S4[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
           // System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_S4(String name, double[] bias_S4){
        int x;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<16;x++){
            temp.append(bias_S4[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBobot_C5(String name, double[][][] bobot_C5){
        int x,i,j;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<120;x++){
            for(i=0;i<16;i++){
                for(j=0;j<36;j++){
                    temp.append(bobot_C5[i][j][x]);
                    temp.append(System.getProperty("line.separator"));
                }
            }            
        }        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_C5(String name, double[] bias_C5){
        int x;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<120;x++){
            temp.append(bias_C5[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBobot_F6(String name, double[][] bobot_F6){
        int i,j;
        StringBuffer temp = new StringBuffer();
        
        for(i=0;i<84;i++){
            for(j=0;j<120;j++){
                temp.append(bobot_F6[j][i]);
                temp.append(System.getProperty("line.separator"));
            }            
        }        
        be.open(name);              
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    public void simpanBias_F6(String name, double[] bias_F6){
        int x;
        StringBuffer temp = new StringBuffer();
        
        for(x=0;x<84;x++){
            temp.append(bias_F6[x]);
            temp.append(System.getProperty("line.separator"));
        }
        
        be.open(name);
        fbobot = be.getFile();
        
        try {
            FileWriter fstream = new FileWriter(fbobot);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(temp.toString());
            out.close();
        } catch (Exception e) {
            //System.err.println("Error : "+e.getMessage());
        }
    }
    
    
    public String[] getParameter() {
        String fname[] = new String[12];
        
        fname[0] = "D:/NewFER/data latih/bobotbias/bobotc1.txt";
        fname[1] = "D:/NewFER/data latih/bobotbias/biasc1.txt";
        fname[2] = "D:/NewFER/data latih/bobotbias/bobots2.txt";
        fname[3] = "D:/NewFER/data latih/bobotbias/biass2.txt";
        fname[4] = "D:/NewFER/data latih/bobotbias/bobotc3.txt";
        fname[5] = "D:/NewFER/data latih/bobotbias/biasc3.txt";
        fname[6] = "D:/NewFER/data latih/bobotbias/bobots4.txt";
        fname[7] = "D:/NewFER/data latih/bobotbias/biass4.txt";
        fname[8] = "D:/NewFER/data latih/bobotbias/bobotc5.txt";
        fname[9] = "D:/NewFER/data latih/bobotbias/biasc5.txt";
        fname[10] = "D:/NewFER/data latih/bobotbias/bobotf6.txt";
        fname[11] = "D:/NewFER/data latih/bobotbias/biasf6.txt";
        return fname;
    }
    
    public String[] getParameterUpdate(){
        String fname[] = new String[12];
        
        fname[0] = "D:/NewFER/data latih/bobotbias/new_bobotc1.txt";
        fname[1] = "D:/NewFER/data latih/bobotbias/new_biasc1.txt";
        fname[2] = "D:/NewFER/data latih/bobotbias/new_bobots2.txt";
        fname[3] = "D:/NewFER/data latih/bobotbias/new_biass2.txt";
        fname[4] = "D:/NewFER/data latih/bobotbias/new_bobotc3.txt";
        fname[5] = "D:/NewFER/data latih/bobotbias/new_biasc3.txt";
        fname[6] = "D:/NewFER/data latih/bobotbias/new_bobots4.txt";
        fname[7] = "D:/NewFER/data latih/bobotbias/new_biass4.txt";
        fname[8] = "D:/NewFER/data latih/bobotbias/new_bobotc5.txt";
        fname[9] = "D:/NewFER/data latih/bobotbias/new_biasc5.txt";
        fname[10] = "D:/NewFER/data latih/bobotbias/new_bobotf6.txt";
        fname[11] = "D:/NewFER/data latih/bobotbias/new_biasf6.txt";
        return fname;
    }
}