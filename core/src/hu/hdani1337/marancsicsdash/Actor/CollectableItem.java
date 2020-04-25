package hu.hdani1337.marancsicsdash.Actor;

import com.badlogic.gdx.scenes.scene2d.Actor;

import static hu.hdani1337.marancsicsdash.Stage.OptionsStage.difficulty;

public interface CollectableItem {
    default void isCollected(Zsolti zsolti){
        Actor actor = (Actor)this;
        actor.setX(actor.getX() - (difficulty*0.08f));
        if(actor.getX() < zsolti.getX()+zsolti.getWidth() && actor.getY() < zsolti.getY()+zsolti.getHeight()){
            if(actor.getX()+actor.getWidth() > zsolti.getX() && actor.getY()+actor.getHeight() > zsolti.getY()) {
                collected();
                actor.remove();
            }
        }
    }

    void collected();
}
