package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public interface CollectableItem {
    /**
     * Ez a begyűjthető tárgy act-jában hívódik meg
     * **/
    default void isCollected(Zsolti zsolti){
        move((Actor)this);
        collisionZsolti((Actor)this, zsolti);
    }

    /**
     * Mozgatás világhoz mért sebességgel
     * **/
    default void move(Actor actor){
        actor.setX(actor.getX() - (difficulty*0.08f));
    }

    /**
     * Megvizsgáljuk, hogy a tárgy ütközik e Zsoltival
     * Ha igen, végrehajtuk azt, aminek ütközéskor történni kell, majd eltávolítjuk a tárgyat a Stage-ről
     * **/
    default void collisionZsolti(Actor actor, Zsolti zsolti){
        if(actor.getX() < zsolti.getX()+zsolti.getWidth() && actor.getY() < zsolti.getY()+zsolti.getHeight()){
            if(actor.getX()+actor.getWidth() > zsolti.getX() && actor.getY()+actor.getHeight() > zsolti.getY()) {
                collected();
                actor.remove();
            }
        }
    }

    /**
     * Ez fut le akkor, hogyha Zsolti felveszi a tárgyat
     * **/
    void collected();
}
