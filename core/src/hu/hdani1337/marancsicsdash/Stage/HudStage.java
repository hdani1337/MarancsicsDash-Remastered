package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.hdani1337.marancsicsdash.HudActors.Jump;

public class HudStage extends MyStage implements StageInterface {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Jump.class,assetList);
    }

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
        jump.setSize(jump.getWidth()*1.2f,jump.getHeight()*1.2f);
    }

    @Override
    public void setPositions() {
        jump.setPosition(getViewport().getWorldWidth()-jump.getWidth()-15,15);
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
