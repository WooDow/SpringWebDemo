/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author student
 */
public class D {
    private String s1;
    private String s2;
    private String mesg;
    
    public D() {
        System.out.println("D() 無參數建構式 ");
    }
    
    public D(String s1 , String s2) {
        this.s1 = s1;
        this.s2 = s2;
        System.out.println("D() 有參數建構式: " + s1 + " : " + s2 );
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
        System.out.println("設定 mesg: " + mesg);
    }
    
    public void setS2(String s2) {
        this.s2 = s2;
    }
    
}
