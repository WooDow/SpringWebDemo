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
public class C {
    private D d;
    private String mesg;

    public D getD() {
        return d;
    }

    public void setD(D d) {
        this.d = d;
        System.out.println("d物件的 s1: " + d.getS1() );
        System.out.println("d物件的 s2: " + d.getS2() );
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }
    
    
    
}

