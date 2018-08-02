package com.example.mm_kau.smart_point;

import java.util.ArrayList;

public class REWARDS {

     private String ID ;
     private String name;
     private ArrayList<packages> redeem;

    public void setRedeem(ArrayList<packages> redeem) {
        this.redeem = redeem;
    }

    public REWARDS(String name) {
        this.name = name;
    }

    public void setID(String ID) {

          this.ID = ID;

     }

    public ArrayList<packages> getRedeem() {
        return redeem;
    }

    public void setName(String name) {
          this.name = name;
     }

     public String getID() {
          return ID;
     }

     public String getName() {
          return name;
     }
}
