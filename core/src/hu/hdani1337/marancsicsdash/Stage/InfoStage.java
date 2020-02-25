package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;

public class InfoStage extends MyStage implements StageInterface {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class, assetList);
        assetList.addTexture("pic/menuBg.jpg");
    }

    public InfoStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    private OneSpriteStaticActor bg;
    private TextBox back;
    private TextBox desc;

    private OneSpriteStaticActor en;
    private MyLabel name;
    private MyLabel title;

    @Override
    public void assignment() {
        bg = new OneSpriteStaticActor(game,"pic/menuBg.jpg");
        back = new TextBox(game, "Vissza a menübe");
        desc = new TextBox(game, "A játék valós eseményeken alapul.\nEgy szép napon a föhösünk, Zsolti beszólt szeretett\nosztályfönökünknek, Marancsicsnak.\nMarancsics nagyon megharagudott rá, s mindenáron elakarja kapni Zsoltit,\nhogy osztályfönökit adhasson neki. A Te feladatod az,\nhogy Zsolti minél tovább tudjon menekülni. Vigyázz, mert Marancsics\nneked tudja rúgni az akadályokat!");
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > bg.getWidth()) bg.setWidth(getViewport().getWorldWidth());
        desc.setWidth(900);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < bg.getWidth()) bg.setX((getViewport().getWorldWidth()-bg.getWidth())/2);
        desc.setPosition(getViewport().getWorldWidth()/2-desc.getWidth()/2,getViewport().getWorldHeight()/2-desc.getHeight()/2);
    }

    @Override
    public void addListeners() {
        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(bg);
        addActor(back);
        addActor(desc);
    }
}
