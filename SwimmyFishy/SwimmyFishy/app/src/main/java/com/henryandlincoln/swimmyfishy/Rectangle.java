package com.henryandlincoln.swimmyfishy;


public class Rectangle {

    public int x;
    public int y;
    public float width;
    public float height;

    public Rectangle(){

    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public boolean intersects(Rectangle other){

        return (this.x < (other.x + other.width) && (this.x + this.width) > other.x && this.y < (other.y  + other.height) && (this.y +  this.height) > other.y );
    }
}
