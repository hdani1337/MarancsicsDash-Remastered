package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface CollectableItem {
    default void isCollected(Zsolti zsolti){
        Actor actor = (Actor)this;
        actor.setX(actor.getX() - 0.15f);
        if(actor.getX() < zsolti.getX()+zsolti.getWidth() && actor.getY() < zsolti.getY()+zsolti.getHeight()){
            if(actor.getX()+actor.getWidth() > zsolti.getX() && actor.getY()+actor.getHeight() > zsolti.getY()) {
                collected();
                actor.remove();
            }
        }
    }

    void collected();
}
