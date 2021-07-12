package Perma;

import java.util.Arrays;
import java.util.Random;

import static Perma.Data.*;

public class PermanentBanner {

    private int count;
    private int pity4;
    private int pity5;
    private int primogems;
    private Item[] item5rolled;
    private Item[] item4rolled;
    private Item[] item3rolled;

    public PermanentBanner(int primogems) {
        this.count = 0;
        this.primogems = primogems;
        this.pity4 = 10;
        this.pity5 = 90;
        this.item5rolled = new Item[]{};
        this.item4rolled = new Item[]{};
        this.item3rolled = new Item[]{};
    }

    public void reset() {
        this.count = 0;
        this.primogems = 65000;
        this.pity4 = 10;
        this.pity5 = 90;
    }

    public Item wish(){
        Item wish;
        Random ran = new Random();
        if(pity4 == 1){ //hit 4* pity
            wish = rollItem4();
            pity4 = 10;
        }else if(pity5 == 1){  //hit 5* pity
            wish = rollItem5();
            pity5 = 90;
        }else if(pity5 == 2 && pity4 == 2){ //prevent hitting bot 4* and 5* pity at the same time
            int chance5or4 = ran.nextInt(2);
            if(chance5or4 == 0){
                wish = rollItem4();
                pity4 = 10;
            }else{
                wish = rollItem5();
                pity5 = 90;
            }
        }else { //normal roll with chance of getting 4* and 5*
            int chance5 = ran.nextInt(pity5-1);
            int chance4 = ran.nextInt(pity4-1);
            if (chance4 == 0 && chance5 == 0){
                int num = ran.nextInt(2);
                if (num == 1){
                    wish = rollItem4();
                    pity4 = 10;
                }else{
                    wish = rollItem5();
                    pity5 = 90;
                }
            }else if (chance4 == 0){
                wish = rollItem4();
                pity4 = 10;
            }else if (chance5 == 0){
                wish = rollItem5();
                pity5 = 90;
            }else{
                wish = rollItem3();
                pity4--;
                pity5--;
            }
        }
        primogems = primogems - 160;
        count++;
        return wish;
    }

    public void wishx1(){
        if(this.primogems < 160){
            System.out.println("Current primogems: " + this.primogems);
            System.out.println("You need " + (160 - this.primogems) + " more primogems, top up in shop?");
        }else {
            Item wish = wish();
            System.out.println("Congratulations! You got: ");
            displayRoll(wish);
            System.out.println("\nPrimogems: " + this.primogems);
            System.out.println("Count: " + this.count);
            System.out.println("Pity4: " + this.pity4);
            System.out.println("Pity5: " + this.pity5);
        }
    }

    public void wishx10(){
        if(this.primogems < 1600){
            System.out.println("Current primogems: " + this.primogems);
            System.out.println("You need " + (1600 - this.primogems) + " more primogems, top up in shop?");
        }else {
            System.out.println("Congratulations! You got: ");
            for (int i = 0; i<10; i++){
                Item wish = wish();
                displayRoll(wish);
            }
            System.out.println("\nPrimogems: " + this.primogems);
            System.out.println("Count: " + this.count);
            System.out.println("Pity4: " + this.pity4);
            System.out.println("Pity5: " + this.pity5);
        }
    }

    public Item rollItem3(){
        Random ran = new Random();
        Item wish = weap3[ran.nextInt(weap3.length)];
        item3rolled = Arrays.copyOf(item3rolled, item3rolled.length + 1);
        item3rolled[item3rolled.length - 1] = wish;
        return wish;
    }

    public Item rollItem4(){
        Random ran = new Random();
        Item wish = item4[ran.nextInt(item4.length)];
        item4rolled = Arrays.copyOf(item4rolled, item4rolled.length + 1);
        item4rolled[item4rolled.length - 1] = wish;
        return wish;
    }

    public Item rollItem5(){
        Random ran = new Random();
        Item wish = item5[ran.nextInt(item5.length)];
        item5rolled = Arrays.copyOf(item5rolled, item5rolled.length + 1);
        item5rolled[item5rolled.length - 1] = wish;
        return wish;
    }

    public void displayRoll(Item wish){
        switch (wish.getRating()){
            case 5:
                System.out.println(wish.getRating() + "***** \t" + wish.getName());
                break;
            case 4:
                System.out.println(wish.getRating() + "**** \t" + wish.getName());
                break;
            default:
                System.out.println(wish.getRating() + "*** \t" + wish.getName());
        }
    }

    public void displayStats(){
        System.out.println("Total rolls: " + this.count);
        System.out.println("Primogems left: " + this.primogems);
        System.out.println("\nTotal 5***** rolled: " + this.item5rolled.length);
        for(int i = 0; i<this.item5rolled.length; i++){
            displayRoll(item5rolled[i]);
        }System.out.println("\nTotal 4**** rolled: " + this.item4rolled.length);
        for(int i = 0; i<this.item4rolled.length; i++){
            displayRoll(item4rolled[i]);
        }System.out.println("\nTotal 3*** rolled: " + this.item3rolled.length);
        for(int i = 0; i<this.item3rolled.length; i++){
            displayRoll(item3rolled[i]);
        }

    }



}
