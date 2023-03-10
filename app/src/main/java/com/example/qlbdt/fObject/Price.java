package com.example.qlbdt.fObject;

public class Price implements Comparable<Price>{
    private int millions, thousands, inits;

    public Price(String price) {
        String[] field = price.split("\\.");
        this.millions = Integer.parseInt(field[0]);
        this.thousands = Integer.parseInt(field[1]);
        this.inits = Integer.parseInt(field[2]);
    }

    @Override
    public int compareTo(Price that) {
        if (this.millions < that.millions) return -1;
        if (this.millions > that.millions) return +1;
        if (this.thousands < that.thousands) return -1;
        if (this.thousands > that.thousands) return +1;
        if (this.inits < that.inits) return -1;
        if (this.inits > that.inits) return +1;
        return 0;
    }

    @Override
    public String toString() {
        if (thousands < 100){
            return millions+".0"+thousands+"."+inits+"00";
        }
        return millions+"."+thousands+"."+inits+"00";
    }

}
