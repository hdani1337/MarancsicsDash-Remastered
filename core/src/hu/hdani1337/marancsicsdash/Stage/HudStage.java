package hu.hdani1337.marancsicsdash.Stage;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.HudActors.Jump;
import hu.hdani1337.marancsicsdash.HudActors.Pause;

public class HudStage extends PrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Jump.class,assetList);
    }

    public static GameStage stage;
    private Jump jump;
    private Pause pause;

    public HudStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void beforeInit() {
        super.beforeInit();
        setCameraMoveToXY(10000,10000,1);
    }

    @Override
    public void assignment() {
        jump = new Jump(game, stage);
        pause = new Pause(game);
    }

    @Override
    public void setSizes() {
        jump.setSize(jump.getWidth()*1.2f,jump.getHeight()*1.2f);
    }

    @Override
    public void setPositions() {
        jump.setPosition(getViewport().getWorldWidth()-jump.getWidth()-15,15);
        pause.setPosition(getViewport().getWorldWidth()-pause.getWidth()-15,getViewport().getWorldHeight()-pause.getHeight()-15);
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
        addActor(pause);
    }
}
