package Perma;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
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
    private Item[] itemrolled;

    public PermanentBanner(int primogems) {
        this.count = 0;
        this.primogems = primogems;
        this.pity4 = 10;
        this.pity5 = 90;
        this.item5rolled = new Item[]{};
        this.item4rolled = new Item[]{};
        this.item3rolled = new Item[]{};
        this.itemrolled = new Item[]{};
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
            pity5--;
        }else if(pity5 == 1){  //hit 5* pity
            wish = rollItem5();
            pity4--;
            pity5 = 90;
        }else if(pity5 == 2 && pity4 == 2){ //prevent hitting bot 4* and 5* pity at the same time
            int chance5or4 = ran.nextInt(2);
            if(chance5or4 == 0){
                wish = rollItem4();
                pity4 = 10;
                pity5--;
            }else{
                wish = rollItem5();
                pity4--;
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
                    pity5--;
                }else{
                    wish = rollItem5();
                    pity4--;
                    pity5 = 90;
                }
            }else if (chance4 == 0){
                wish = rollItem4();
                pity4 = 10;
                pity5--;
            }else if (chance5 == 0){
                wish = rollItem5();
                pity4--;
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
            Item[] itemx10 = new Item[10];
            System.out.println("Congratulations! You got: ");
            for (int i = 0; i<10; i++){
                itemx10[i] = wish();
            }
            itemx10 = rearrange10x(itemx10);
            for (int i = 0; i<10; i++){
                displayRoll(itemx10[i]);
            }
            System.out.println("\nPrimogems: " + this.primogems);
            System.out.println("Count: " + this.count);
            System.out.println("Pity4: " + this.pity4);
            System.out.println("Pity5: " + this.pity5);
            System.out.println();
        }
    }

    public Item[] rearrange10x(Item[] item10x){
        Queue<Item> queue5char = new LinkedList<>();
        Queue<Item> queue5weap = new LinkedList<>();
        Queue<Item> queue4char = new LinkedList<>();
        Queue<Item> queue4weap = new LinkedList<>();
        Queue<Item> queue3 = new LinkedList<>();
        for (int i = 0; i<10; i++){
            if(item10x[i].getRating() == 5){
                if(item10x[i] instanceof Character){
                    queue5char.add(item10x[i]);
                }else{
                    queue5weap.add(item10x[i]);
                }
            }else if(item10x[i].getRating() == 4){
                if(item10x[i] instanceof Character){
                    queue4char.add(item10x[i]);
                }else{
                    queue4weap.add(item10x[i]);
                }
            }else{
                queue3.add(item10x[i]);
            }
        }
        Item[] orderedItem10x = new Item[10];
        int cnt = 0;
        while(!queue5char.isEmpty()){
            orderedItem10x[cnt] =queue5char.remove();
            cnt++;
        }
        while(!queue5weap.isEmpty()){
            orderedItem10x[cnt] =queue5weap.remove();
            cnt++;
        }
        while(!queue4char.isEmpty()){
            orderedItem10x[cnt] =queue4char.remove();
            cnt++;
        }
        while(!queue4weap.isEmpty()){
            orderedItem10x[cnt] =queue4weap.remove();
            cnt++;
        }
        while(!queue3.isEmpty()){
            orderedItem10x[cnt] =queue3.remove();
            cnt++;
        }
        return orderedItem10x;
    }

    public Item rollItem3(){
        Random ran = new Random();
        Item wish = weap3[ran.nextInt(weap3.length)];
        item3rolled = Arrays.copyOf(item3rolled, item3rolled.length + 1);
        item3rolled[item3rolled.length - 1] = wish;
        itemrolled = Arrays.copyOf(itemrolled, itemrolled.length + 1);
        itemrolled[itemrolled.length - 1] = wish;
        return wish;
    }

    public Item rollItem4(){
        Random ran = new Random();
        Item wish = item4[ran.nextInt(item4.length)];
        item4rolled = Arrays.copyOf(item4rolled, item4rolled.length + 1);
        item4rolled[item4rolled.length - 1] = wish;
        itemrolled = Arrays.copyOf(itemrolled, itemrolled.length + 1);
        itemrolled[itemrolled.length - 1] = wish;
        return wish;
    }

    public Item rollItem5(){
        Random ran = new Random();
        Item wish = item5[ran.nextInt(item5.length)];
        item5rolled = Arrays.copyOf(item5rolled, item5rolled.length + 1);
        item5rolled[item5rolled.length - 1] = wish;
        itemrolled = Arrays.copyOf(itemrolled, itemrolled.length + 1);
        itemrolled[itemrolled.length - 1] = wish;
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
        }
        System.out.println("\nTotal 4**** rolled: " + this.item4rolled.length);
        for(int i = 0; i<this.item4rolled.length; i++){
            displayRoll(item4rolled[i]);
        }
        System.out.println("\nTotal 3*** rolled: " + this.item3rolled.length);
        for(int i = 0; i<this.item3rolled.length; i++){
            displayRoll(item3rolled[i]);
        }
        System.out.println("\nHistory: ");
        for(int i = 0; i<this.itemrolled.length; i++) {
            System.out.print( i + 1 + ") ");
            displayRoll(itemrolled[i]);
        }
    }
}
