package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.hdani1337.marancsicsdash.HudActors.Jump;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;

public class HudStage extends MyStage implements StageInterface {

    private GameStage stage;
    private Jump jump;

    public HudStage(MyGame game, GameStage gameStage) {
        super(new ResponseViewport(900), game);
        this.stage = gameStage;
        setCameraMoveToXY(10000,10000,1);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        jump = new Jump(game, stage);
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
        addActor(jump);
    }
}
