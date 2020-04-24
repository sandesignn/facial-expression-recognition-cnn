/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Boundary.FER_CNN;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Kurnia Sandi Pratama
 */
public class CNN_Manager {
    Bobot_Manager bm;
    Gambar_Manager gm;
    private double [][][] C1 = new double[6][30][30];
    private double [][][] S2 = new double[6][15][15];
    private double [][][] C3 = new double[16][13][13];
    private double [][][] S4 = new double[16][6][6];
    private double [] C5 = new double[120];
    private double [] F6 = new double[84];
    private double [] output = new double[5];
    
    private double[][][] bobot_C1 = new double[6][3][3];
    private double[] bobot_S2 = new double[6];
    private double[][][][] bobot_C3 = new double[6][16][3][3];
    private double[] bobot_S4 = new double[16];
    private double[][][] bobot_C5 = new double[16][36][120];
    private double[][] bobot_F6 = new double[120][84];
    
    private double[] bias_C1 = new double[6];
    private double[] bias_S2 = new double[6];
    private double[] bias_C3 = new double[16];
    private double[] bias_S4 = new double[16];
    private double[] bias_C5 = new double[120];
    private double[] bias_F6 = new double[84];
    
    private double[][][] delta_C1 = new double[6][30][30];
    private double[][][] delta_S2 = new double[6][15][15];
    private double[][][] delta_C3 = new double[16][13][13];
    private double[][][] delta_S4 = new double[16][6][6];
    private double[] delta_C5 = new double[120];
    private double[] delta_F6 = new double[84];
    
    private double[][][] newbobot_c1 = new double[6][3][3];
    private double[] newbobot_s2 = new double[6];
    private double[][][][] newbobot_c3 = new double[6][16][3][3];
    private double[] newbobot_s4 = new double[16];
    private double[][][] newbobot_c5 = new double[16][36][120];
    private double[][] newbobot_f6 = new double[120][84];
    
    private double[] newbias_c1 = new double[6];
    private double[] newbias_s2 = new double[6];
    private double[] newbias_c3 = new double[16];
    private double[] newbias_s4 = new double[16];
    private double[] newbias_c5 = new double[120];
    private double[] newbias_f6 = new double[84];
    
    private int[][] input = new int[32][32];
    private double[][] target = new double[4][84];
    
    private double alpha, net_C1, net_S2, net_C3;
    private double net_S4, net_C5, net_F6;
    private int lbl;
    private String prediksi;
    
    public CNN_Manager() {
        bm = new Bobot_Manager();
        gm = new Gambar_Manager();
    }
    
    public void training(FER_CNN fer) throws IOException{
        int index,epochmax,epoch,i,n;
        double errorToleren,mse,error;
        String[] inputImage = new String[100];
        int[] label = new int[100];
        String Epoch = fer.epoch_max.getText();
        
        epochmax= Integer.parseInt(Epoch);
        errorToleren = (double)0.5;
         
        inputImage = gm.gambar32x32();
        gm.setLabel_Train();
        label = gm.getLabel_Train();
        setBobotBias();
        
        mse = 999;
        epoch = 1;
        n=100;
        
        while(epochmax>=epoch && errorToleren<=mse){
            alpha = (double) Double.parseDouble(fer.learning_rate.getText());
            for(i=0;i<n;i++){
                error = 0;
                try {
                    input=gm.inputTraining(inputImage[i]);
                } catch (IOException ex) {
                }
                index = label[i];
                feedforward();
                error = (double) error+output[index];
                mse = error/(i+1);
                backward(index);
                updateBobotBias();
                
            }
            fer.indikator.setText("Pelatihan selesai....                 Epoch : "+epoch+"        MSE : "+mse);
            System.out.println("epoch: "+epoch+"|   Mse: "+mse);
            System.out.println();
            simpanBobotBias();
            epoch++;
        }
    }
    
    public void testing(int[][] input) throws IOException{
        int i;
        double min;
        
        
        String ekspresi[] = {"Senang","Netral","Sedih","Terkejut"};
               
        //ReadupdateBobotBias();
        min = 999;
        this.input = input;
        
        
        
        feedforward();
        for(i=0;i<4;i++){
            if(output[i]<min){
                min = output[i];
                lbl = i;
            }System.out.println("Output: "+min+"-----> "+lbl);
        }
        
        if(lbl==0){
            prediksi = ekspresi[0];
        }else if(lbl==1){
            prediksi = ekspresi[1];
        }else if(lbl==2){
            prediksi = ekspresi[2];
        }else if(lbl==3){
            prediksi = ekspresi[3];
        }
       
        System.out.println("Prediksi:  " + prediksi);
    }
    
    public String getEkspresi(){
        return prediksi;
    }
    
    public void setBobotBias() throws FileNotFoundException, IOException{
        String fbobot[] = new String[12];
        fbobot = bm.getParameter();
        
        bm.bacaBobot_C1(fbobot[0]);
        bm.bacaBias_C1(fbobot[1]);
        
        bm.bacaBobot_S2(fbobot[2]);
        bm.bacaBias_S2(fbobot[3]);
        
        bm.bacaBobot_C3(fbobot[4]);
        bm.bacaBias_C3(fbobot[5]);
        
        bm.bacaBobot_S4(fbobot[6]);
        bm.bacaBias_S4(fbobot[7]);
        
        bm.bacaBobot_C5(fbobot[8]);
        bm.bacaBias_C5(fbobot[9]);
        
        bm.bacaBobot_F6(fbobot[10]);
        bm.bacaBias_F6(fbobot[11]);
        
        bobot_C1 = bm.getBobot_C1();
        bias_C1 = bm.getBias_C1();
        
        bobot_S2 = bm.getBobot_S2();
        bias_S2 = bm.getBias_S2();
        
        bobot_C3 = bm.getBobot_C3();
        bias_C3 = bm.getBias_C3();
        
        bobot_S4 = bm.getBobot_S4();
        bias_S4 = bm.getBias_S4();
        
        bobot_C5 = bm.getBobot_C5();
        bias_C5 = bm.getBias_C5();
        
        bobot_F6 = bm.getBobot_F6();
        bias_F6 = bm.getBias_F6();
    }
    
    //-------------------Baca Bobot Bias-----------//
    public void ReadupdateBobotBias()throws FileNotFoundException, IOException{
        String fbobot[] = new String[12];
        fbobot = bm.getParameterUpdate();
        
        bm.bacaBobot_C1(fbobot[0]);
        bobot_C1 = bm.getBobot_C1();
        bm.bacaBias_C1(fbobot[1]);
        bias_C1 = bm.getBias_C1();
        
        bm.bacaBobot_S2(fbobot[2]);
        bobot_S2 = bm.getBobot_S2();
        bm.bacaBias_S2(fbobot[3]);
        bias_S2 = bm.getBias_S2();
        
        bm.bacaBobot_C3(fbobot[4]);
        bobot_C3 = bm.getBobot_C3();
        bm.bacaBias_C3(fbobot[5]);
        bias_C3 = bm.getBias_C3();
        
        bm.bacaBobot_S4(fbobot[6]);
        bobot_S4 = bm.getBobot_S4();
        bm.bacaBias_S4(fbobot[7]);
        bias_S4 = bm.getBias_S4();
        
        bm.bacaBobot_C5(fbobot[8]);
        bobot_C5 = bm.getBobot_C5();
        bm.bacaBias_C5(fbobot[9]);
        bias_C5 = bm.getBias_C5();
        
        bm.bacaBobot_F6(fbobot[10]);
        bobot_F6 = bm.getBobot_F6();
        bm.bacaBias_F6(fbobot[11]);
        bias_F6 = bm.getBias_F6();
    }
    
    //------------------BOBOT BARU---------------------//
    private void simpanBobotBias(){
        String temp[] = new String[12];
        temp = bm.getParameterUpdate();
        
        bm.simpanBobot_C1(temp[0], bobot_C1);
        bm.simpanBias_C1(temp[1], bias_C1);
        
        bm.simpanBobot_S2(temp[2], bobot_S2);
        bm.simpanBias_S2(temp[3], bias_S2);
        
        bm.simpanBobot_C3(temp[4], bobot_C3);
        bm.simpanBias_C3(temp[5], bias_C3);
        
        bm.simpanBobot_S4(temp[6], bobot_S4);
        bm.simpanBias_S4(temp[7], bias_S4);
        
        bm.simpanBobot_C5(temp[8], bobot_C5);
        bm.simpanBias_C5(temp[9], bias_C5);
        
        bm.simpanBobot_F6(temp[10], bobot_F6);
        bm.simpanBias_F6(temp[11], bias_F6);
    }
    
    private void feedforward(){
        layer_C1();
        layer_S2();
        layer_C3();
        layer_S4();
        layer_C5();
        layer_F6();
        layer_Output();
    }
    
    private void backward(int label){
        gLayer_F6(label);
        gLayer_C5();
        gLayer_S4();
        gLayer_C3();
        gLayer_S2();
        //gLayer_C1();
    }
    
    private void updateBobotBias(){
        updateBobot_C1();
        updateBias_C1();
        updateBobot_S2();
        updateBias_S2();
        updateBobot_C3();
        updateBias_C3();
        updateBobot_S4();
        updateBias_S4();
        updateBobot_C5();
        updateBias_C5();
        updateBobot_F6();
        updateBias_F6();
    }
    
    //-------------Feedforward-----------------//
    private double f_aktivasi(double x){
        double aktivasi = 1.7159*Math.tanh(0.667*x);
        return aktivasi;
    }

    private void layer_C1() { 
        int x,i,j,k,l,p,q;
        double temp1;
        
        for(x=0;x<6;x++){
            for(i=0;i<30;i++){
                for(j=0;j<30;j++){
                    temp1 = 0;
                    p = 0;
                    for(k=j;k<=j+2;k++){
                        q = 0;
                        for(l=i;l<=i+2;l++){
                            temp1 = temp1+(input[k][l]*bobot_C1[x][p][q]);
                            //System.out.println(bobot_C1[x][p][q]);
                           
                            q++;
                        }p++;
                    }
              
                    net_C1 = temp1+bias_C1[x];
                    //System.out.println(net_C1);
                    C1[x][i][j] = f_aktivasi(net_C1);                    
//                    System.out.print("C1"+i+j+" = "+C1[x][i][j]+" ");
                    
                }//System.out.println("");
            }//System.out.println("");
        }
    }

    private void layer_S2() {
        int x,i,j,p,q,baris,kolom;
        double temp1;
        
        for(x=0;x<6;x++){
            baris = 0;
            kolom = 0;
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    temp1=0;
                    double max=0;
                    for(p=baris;p<baris+2;p++){
                        for(q=kolom;q<kolom+2;q++){
                            if(C1[x][p][q] > max){
                                max=C1[x][p][q];
                            }
                            //temp1 = temp1+C1[x][p][q];
                        }
                    }
                
                    net_S2 = (max*bobot_S2[x])+bias_S2[x];
                    //System.out.println("S2 = "+temp2);
//                    System.out.println("Bias S2 = "+bias_S2[x]);
                    S2[x][i][j] = f_aktivasi(net_S2);
                    //System.out.println("S2 = "+S2[x][i][j]+" ");
                    kolom = kolom+2;
                }//System.out.println("");
                baris = baris+2;
                kolom = 0;                   
            }//System.out.println("");         
        }
    }

    private void layer_C3() {     
        int x,i,j,k,l,p,q,s2,index;
        double temp,temp1;
        char[] charArr;
        
        String[] koneksi = bm.koneksi();
        for(x=0;x<16;x++){
            charArr = koneksi[x].toCharArray();
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    temp=0;
                    for(s2=0;s2<charArr.length;s2++){
                        index = Integer.parseInt(Character.toString(charArr[s2]));
                        temp1=0; p=0;
                        //System.out.println("Index ke-"+x+" : "+index);
                        for(k=j;k<j+3;k++){
                            q=0;
                            for(l=i;l<i+3;l++){
                                temp1 = temp1+S2[index][k][l]*bobot_C3[index][x][p][q];
                                //System.out.print("Temp1: "+temp1+"\tS2 "+index+": "+S2[index][k][l]+"\t"+"Bobot: "+bobot_C3[index][x][p][q]+"\t");
                                q++;
                            }//System.out.println("");
                            p++;
                        }//System.out.println("");
                        temp = temp+temp1;
                        //System.out.print("Temp: "+temp+"\t");
                    }
                    net_C3 = temp+bias_C3[x];
                    //System.out.println("");
                    //System.out.print("temp: "+temp+"\tbias: "+bias_C3[x]+"\ttemp2: "+temp2);
                    C3[x][i][j] = f_aktivasi(net_C3);
                    //System.out.print("C3 "+x+" "+i+" "+j+" : "+C3[x][i][j]+"\t");
                }//System.out.println("");
            }//System.out.println("");
        }
    }

    private void layer_S4() {
        int x,i,j,p,q,baris,kolom;
        double temp1;
        
        for(x=0;x<16;x++){
            baris = 0;
            kolom = 0;
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    temp1=0;
                    double max=0;
                    for(p=baris;p<baris+3;p++){
                       for(q=kolom;q<kolom+3;q++){
                           if(C3[x][p][q] >max){
                               max=C3[x][p][q];
                           }
                           //temp1 = temp1+C3[x][p][q];
                           //System.out.print(C3[x][p][q]+"\t");
                       } 
                    }//System.out.println("");
                    net_S4 = (max*bobot_S4[x])+bias_S4[x];
                    S4[x][i][j] = f_aktivasi(net_S4);
                    //System.out.print("S4: "+S4[x][i][j]+"\t");
                    kolom = kolom+2;
                }//System.out.println("");
                baris = baris+2;
                kolom = 0;
            }//System.out.println("");
        }
    }

    private void layer_C5() {
        int x,y,i,j,p;
        double temp1,temp2;
        
        for(x=0;x<120;x++){
            temp1 = 0;
            for(y=0;y<16;y++){
                temp2 = 0;
                p = 0;
                for(i=0;i<6;i++){
                    for(j=0;j<6;j++){
                        temp2 = temp2+S4[y][i][j]*bobot_C5[y][p][x];
                        //System.out.print("Temp2: "+temp2+"\tS4: "+S4[y][i][j]+"\tbobot c5: "+bobot_C5[y][p][x]+"\t");
                        //System.out.println(bobot_C5[y][p][x]+"  ");
                        p++;
                    }//System.out.println("");
                }temp1 = temp1+temp2;
                //System.out.println("temp1: "+temp1);
            }//System.out.println("");
            net_C5 = temp1+bias_C5[x];
            C5[x] = f_aktivasi(net_C5);
            //System.out.println("hasil C5: "+C5[x]);
        }
    }

    private void layer_F6() {
        int x,y;
        double temp1,temp2;
        
        for(x=0;x<84;x++){
            temp1 = 0;
            for(y=0;y<120;y++){
                temp2 = C5[y]*bobot_F6[y][x];
                //System.out.println(temp2);
                temp1 = temp1+temp2;
                //System.out.println("temp1: "+temp1);
            }//System.out.println("\n");
            net_F6 = temp1+bias_F6[x];
            F6[x] = f_aktivasi(net_F6);
            //System.out.println("F6: "+F6[x]);
        }
    }

    private void layer_Output() {
        int x,y;
        double out;
        double[][] target = new double[4][84];
        
        bm.setTarget();
        target = bm.getTarget();

        for(x=0;x<4;x++){
           out = 0;
           for(y=0;y<84;y++){
               out = out + Math.pow(F6[y]-target[x][y], 2);
                //System.out.println("\tTarget: "+target[x][y]);
           }//System.out.println("");
           output[x] = out;
           
          //System.out.println("output "+x+" : "+output[x]);
        }//System.out.println();
    }
    
    //------------------Fungsi Derivatif-----------------//
    private double f_Derivatif(double x){
        double derivatif = 1.7159*0.667*(1-Math.pow(Math.tanh(0.667*x), 2));
        return derivatif;
    }
    
    //------------Backward Propagation-------------// 
    private void gLayer_F6(int label){
       int x,y;
       double t, error;
       double target[][] = new double[4][84];
       
       bm.setTarget();
       target = bm.getTarget();
       for(x=0;x<84;x++){
            t = target[label][x];
            
            error = t-F6[x];
            //System.out.println("t ke-"+x+"="+t+"f6 ke-"+x+"= "+F6[x]);
            //System.out.println(error);
            delta_F6[x] = error*f_Derivatif(net_F6);
            //System.out.println(delta_F6[i]);
            for(y=0;y<120;y++){
               newbobot_f6[y][x] = alpha*C5[y]*delta_F6[x];
            }newbias_f6[x] = alpha*delta_F6[x];
       }
    }
    
    
    private void gLayer_C5(){
        int x,y,i,j,p;
        double temp;
        
        for(x=0;x<120;x++){
            temp = 0;
            for(y=0;y<84;y++){
                temp = temp+bobot_F6[x][y]*delta_F6[y];
            }//System.out.println(temp);
            delta_C5[x] = f_Derivatif(net_C5)*temp;
            //System.out.println(delta_C5[i]);
        }
        
        for(x=0;x<120;x++){
            for(y=0;y<16;y++){
                p = 0;
                for(i=0;i<6;i++){
                    for(j=0;j<6;j++){
                        newbobot_c5[y][p][x] = alpha*S4[y][i][j]*delta_C5[x];
                        p++;
                    }
                }
            }
            newbias_c5[x] = alpha*delta_C5[x];
        }
    }
    
    private void gLayer_S4(){
        int x,i,j,p,q,baris,kolom,ctr;
        double s4[][][] = new double[16][6][6];
        double temp1;
        
        //inisialisasi temp=0
        for(x=0;x<16;x++){
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    s4[x][i][j] = 0;
                }
            }
            //System.out.println("Ini for ke-1");
        }
        
        for(p=0;p<120;p++){
            for(x=0;x<16;x++){
                ctr = 0;
                for(i=0;i<6;i++){
                    for(j=0;j<6;j++){
                        s4[x][i][j] = s4[x][i][j]+bobot_C5[x][ctr][p]*delta_C5[p];
                        //System.out.println(s4[x][i][j]+"\t"+bobot_C5[x][ctr][p]+"\t"+delta_C5[p]);
                        ctr++;
                    }
                }//System.out.println("");
            }
        }
        
        //delta S4
        for(x=0;x<16;x++){
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    delta_S4[x][i][j] = s4[x][i][j]*f_Derivatif(net_S4);
                }
            }
        }       
        
        for(x=0;x<16;x++){
            baris=0;
            kolom=0;
            newbobot_s4[x]=0;
            
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    temp1=0;
                    for(p=baris;p<baris+3;p++){
                        for(q=kolom;q<kolom+3;q++){
                            if(C3[x][p][q] > temp1){
                               temp1=C3[x][p][q];
                               //temp1 = temp1+C3[x][p][q];
                           }
                            
                        }
                    }
                    newbobot_s4[x] = newbobot_s4[x]+(temp1*delta_S4[x][i][j]);
                    kolom = kolom+2;
                }
                baris = baris+2;
                kolom=0;
            }newbobot_s4[x] = alpha*newbobot_s4[x];
        }
        
        for(x=0;x<16;x++){
            newbias_s4[x] = 0;
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    newbias_s4[x] = newbias_s4[x]+delta_S4[x][i][j];
                }
            }newbias_s4[x] = alpha*newbias_s4[x];
        }
    }
    
    private void gLayer_C3(){
        int x,i,j,p,q,baris,kolom,ctr1,ctr2,s2,index;
        double c3[][][] = new double[16][13][13];
        double temp[][][][] = new double[6][36][16][170];
        double temp1;
        char[] charArr;
        
        //inisialisasi temp
        for(x=0;x<16;x++){
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    c3[x][i][j] = 0;
                    //System.out.println("lolos");
                }
            }
        }
        
        for(x=0;x<16;x++){
            baris = 0;
            kolom = 0;
            for(i=0;i<6;i++){
                for(j=0;j<6;j++){
                    for(p=baris;p<baris+3;p++){
                        for(q=kolom;q<kolom+3;q++){
                            c3[x][p][q] = c3[x][p][q]+bobot_S4[x]*delta_S4[x][i][j];
                            //System.out.println(c3[x][p][q]);
                        }
                    }kolom = kolom+2;
                }
                baris = baris+2;
                kolom = 0;
            }
        }
        
        
        for(x=0;x<16;x++){
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    delta_C3[x][i][j] = c3[x][i][j]*f_Derivatif(net_C3);
                   // System.out.println(c3[x][i][j]);
                }
            }
        }
        //System.out.println("batas");
        String[] koneksi = bm.koneksi();
        for(x=0;x<16;x++){
            //System.out.println("ss");
            ctr1 = 0;
            charArr = koneksi[x].toCharArray();
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    for(s2=0;s2<charArr.length;s2++){
                        index = Integer.parseInt(Character.toString(charArr[s2]));
                        ctr2 = 0;
                        for(p=j;p<=j+2;p++){
                            for(q=i;q<=i+2;q++){
                                temp[index][ctr2][x][ctr1] = delta_C3[x][i][j]*S2[index][p][q];
                                //System.out.println(S2[index][p][q]);
                                //System.out.println(delta_C3[x][i][j]);
                                //System.out.println(temp[index][ctr2][x][ctr1]);
                                ctr2++;
                            }
                        }
                    }ctr1++;
                }
            }
        }
        
        for(x=0;x<16;x++){
            ctr1 = 0;
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    charArr = koneksi[i].toCharArray();
                    //System.out.println(charArr);
                    for(s2=0;s2<charArr.length;s2++){
                        index = Integer.parseInt(Character.toString(charArr[s2]));
                        temp1 = 0;
                        for(p=0;p<120;p++){
                            temp1 = temp1+temp[index][ctr1][x][p];
                           // System.out.println("temp[index][ctr1][x][p]");
                        }
                        //System.out.println(temp1);
                        newbobot_c3[index][x][i][j] = alpha*temp1;
                    }ctr1++;
                }
            }
        }
        
        for(x=0;x<16;x++){
            temp1 = 0;
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    temp1 = temp1+delta_C3[x][i][j];
                }
            }
            newbias_c3[x] = alpha*temp1;
        }
    }
    
    private void gLayer_S2(){
        int x,i,j,k,l,p,q,ss2,baris,kolom,index;
        double temp;
        double s2[][][] = new double[6][15][15];
        char[] charArr;
        
        //inisialisasi temp
        for(x=0;x<6;x++){
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    s2[x][i][j] = 0;
                }
            }
        }
        
        String[] koneksi = bm.koneksi();
        for(x=0;x<16;x++){
            for(i=0;i<13;i++){
                for(j=0;j<13;j++){
                    charArr = koneksi[x].toCharArray();
                    for(ss2=0;ss2<charArr.length;ss2++){
                        index = Integer.parseInt(Character.toString(charArr[ss2]));
                        p = 0;
                        for(k=j;k<=j+1;k++){
                            q = 0;
                            for(l=i;l<=i+1;l++){
                                s2[index][k][l] = s2[index][k][l]+bobot_C3[index][x][p][q]*delta_C3[x][i][j];
                                q++;
                            }p++;
                        }
                    }
                }
            }
        }
        
        for(x=0;x<6;x++){
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    delta_S2[x][i][j] = s2[x][i][j]*f_Derivatif(net_S2);
                }
            }
        }   
        
        for(x=0;x<6;x++){
            baris=0;
            kolom=0;
            newbobot_s2[x] = 0;
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                   temp = 0;
                   for(p=baris;p<baris+2;p++){
                       for(q=kolom;q<kolom+2;q++){
                           if(C1[x][p][q] > temp){
                               temp=C1[x][p][q];
                           }
                           //temp = temp+C1[x][p][q];
                       }
                   }
                   newbobot_s2[x] = newbobot_s2[x]+ (temp*delta_S2[x][i][j]);
                   kolom = kolom+2;
                }
                baris = baris+2;
                kolom = 0;
            }newbobot_s2[x] = alpha*newbobot_s2[x];
        }
        
        for(x=0;x<6;x++){
            newbias_s2[x]=0;
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    newbias_s2[x] = newbias_s2[x]+delta_S2[x][i][j];
                }
            }newbias_s2[x] = alpha*newbias_s2[x];
        }        
    }    
    
    private void gLayer_C1(){
        int x,i,j,p,q,ctr1,ctr2,baris,kolom;
        double temp;
        double temp1[][][] = new double[9][6][900];
        double c1[][][] = new double[6][30][30];
        
        for(x=0;x<6;x++){
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    c1[x][i][j] = 0;
                }
            }
        }
        
        for(x=0;x<6;x++){
            baris = 0;
            kolom = 0;
            for(i=0;i<15;i++){
                for(j=0;j<15;j++){
                    for(p=baris;p<baris+2;p++){
                        for(q=kolom;q<kolom+2;q++){
                            c1[x][p][q] = c1[x][p][q]+bobot_S2[x]*delta_S2[x][i][j];
                        }
                    }kolom = kolom+2;
                }
                baris = baris+2;
                kolom = 0;
            }
        }
        
        for(x=0;x<6;x++){
            for(i=0;i<30;i++){
                for(j=0;j<30;j++){
                    delta_C1[x][i][j] = c1[x][i][j]*f_Derivatif(net_C1);
                }
            }
        }
        
        for(x=0;x<6;x++){
            ctr1 = 0;
            for(i=0;i<30;i++){
                for(j=0;j<30;j++){
                    ctr2 = 0;
                    for(p=j;p<=j+2;p++){
                        for(q=i;q<=i+2;q++){
                            temp1[ctr2][x][ctr1]=temp1[ctr2][x][ctr1]+input[p][q]*delta_C1[x][i][j];
                            ctr2++;
                        }
                    }ctr1++;
                }
            }
        }
        
        for(x=0;x<6;x++){
            ctr1=0;
            for(i=0;i<3;i++){
                for(j=0;j<3;j++){
                    temp=0;
                    for(p=0;p<900;p++){
                        temp=temp+temp1[ctr1][x][p];
                    }newbobot_c1[x][i][j] = temp*alpha;
                    ctr1++;
                }
            }
        }
        
        for(x=0;x<6;x++){
            temp=0;
            for(i=0;i<30;i++){
                for(j=0;j<30;j++){
                    temp = temp+delta_C1[x][i][j];
                }
            }newbias_c1[x] = alpha*temp;
        }
    }
    
    //-------------------Update Bobot Bias---------------//
    private void updateBobot_C1(){
        for(int x=0;x<6;x++){
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    bobot_C1[x][i][j] = bobot_C1[x][i][j]+newbobot_c1[x][i][j];
                }
            }
        }
    }
    
    private void updateBias_C1(){
        for(int x=0;x<6;x++){
            bias_C1[x] = bias_C1[x]+newbias_c1[x];
        }
    }
    
    private void updateBobot_S2() {
        for(int x=0;x<6;x++){
            bobot_S2[x]=bobot_S2[x]+newbobot_s2[x];
        }
    }
    
    private void updateBias_S2() {
        for(int x=0;x<6;x++){
            bias_S2[x]=bias_S2[x]+newbias_s2[x];
        }
    }
    
    private void updateBobot_C3(){
        int index;
        char[] charArr;
        String[] koneksi = bm.koneksi();
        
        for(int x=0;x<16;x++){
            charArr = koneksi[x].toCharArray();
            for(int s2=0;s2<charArr.length;s2++){
                index = Integer.parseInt(Character.toString(charArr[s2]));
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        bobot_C3[index][x][i][j] = bobot_C3[index][x][i][j]+newbobot_c3[index][x][i][j];
                    }
                }
            }
        }
    }
    
    private void updateBias_C3(){
        for(int x=0;x<16;x++){
            bias_C3[x] = bias_C3[x]+newbias_c3[x];
        }
    }
    
    private void updateBobot_S4() {
        for(int x=0;x<16;x++){
            bobot_S4[x]=bobot_S4[x]+newbobot_s4[x];
        }
    }
    
    private void updateBias_S4() {
        for(int x=0;x<16;x++){
            bias_S4[x]=bias_S4[x]+newbias_s4[x];
        }
    }
    
    private void updateBobot_C5(){
        for(int x=0;x<120;x++){
            for(int i=0;i<16;i++){
                for(int j=0;j<36;j++){
                    bobot_C5[i][j][x] = bobot_C5[i][j][x]+newbobot_c5[i][j][x];
                }
            }
        }
    }
    
    private void updateBias_C5(){
        for(int x=0;x<120;x++){
            bias_C5[x] = bias_C5[x]+newbias_c5[x];
        }
    } 
    
    private void updateBobot_F6(){
        for(int i=0;i<84;i++){
            for(int j=0;j<120;j++){
                bobot_F6[j][i] = bobot_F6[j][i]+newbobot_f6[j][i];
            }
        }
    }
    
    private void updateBias_F6(){
        for(int x=0;x<84;x++){
            bias_F6[x] = bias_F6[x]+newbias_f6[x];
        }
    }

  
}
