package hu.csanyzeg.master.MyBaseClasses.Scene2D;

import com.badlogic.gdx.utils.viewport.Viewport;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;

/**
 * @author hdani1337
 * @date 2020.02.29.
 *
 * Ez az absztrakt osztály tulajdonképpen ugyanazt csinálja, mintha a StageInterfacet implementálnánk,
 * de itt van egy alap konstruktorunk, szóval nem kell minden stagenél egyesével végighívogatni az összes voidot
 * **/
public abstract class PrettyStage extends MyStage{

    public PrettyStage(Viewport viewport, MyGame game) {
        super(viewport, game);
        beforeInit();
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
        afterInit();
    }

    /**
     * Az értékek beállítása előtt végrehajtandó utasítások
     * Pl. debuggoláshos, vagy írhatsz egy programot ide ami minden sikeres lefutásnál lefőz egy kávét
     * **/
    public void beforeInit(){
    }

    /**
     * Értékek hozzárendelése a változókhoz és objektumokhoz
     * **/
    public abstract void assignment();


    /**
     * Actorok, Labelek méreteinek beállítása
     * **/
    public abstract void setSizes();


    /**
     * Actorok, Labelek pozícióinak beállítása
     * **/
    public abstract void setPositions();


    /**
     * Listenerek hozzáadása az Actorokhoz, Labelekhez
     * **/
    public abstract void addListeners();


    /**
     * Actorok, Labelek Z indexeinek beállítása
     * **/
    public abstract void setZIndexes();


    /**
     * Actorok, Labelek hozzáadása a Stagehez
     * **/
    public abstract void addActors();


    /**
     * Az értékek beállítása előtt végrehajtandó utasítások
     * Pl. itt is főzhetsz le kávét
     * **/
    public void afterInit(){
    }
}
