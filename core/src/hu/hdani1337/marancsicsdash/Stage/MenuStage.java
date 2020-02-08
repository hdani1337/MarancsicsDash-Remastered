package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;

public class MenuStage extends MyStage implements StageInterface {
    public MenuStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    private OneSpriteStaticActor MenuBackgroundNormal;
    private OneSpriteStaticActor MenuBackgroundDark;

    @Override
    public void assignment() {
        MenuBackgroundNormal = new OneSpriteStaticActor(game,"pic/menuBg.jpg");
        MenuBackgroundDark = new OneSpriteStaticActor(game,"pic/loadingBg.jpg"){
            @Override
            public void act(float delta) {
                super.act(delta);
                setColor(1,1,1,getColor().a-0.01f);
            }
        };
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(MenuBackgroundNormal);
        addActor(MenuBackgroundDark);
    }
}
