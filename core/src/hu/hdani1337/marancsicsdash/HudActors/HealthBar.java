package hu.hdani1337.marancsicsdash.HudActors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyGroup;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Stage.BossStage;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;

public class HealthBar extends MyGroup implements IPrettyStage {

    public static final String PIROS = "pic/piros.png";
    public static final String ZOLD = "pic/zold.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(PIROS);
        assetList.addTexture(ZOLD);
        assetList.addFont(RETRO_FONT, RETRO_FONT, 32, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor pirosCsik;
    private OneSpriteStaticActor zoldCsik;
    private MyLabel marancsicsElete;
    private MarancsicsBoss marancsicsBoss;

    public HealthBar(MyGame game, MyStage stage) {
        super(game);
        if(stage != null && stage instanceof BossStage) this.marancsicsBoss = ((BossStage)stage).marancsics;
        assignment();
        setSizes();
        setPositions();
        addActors();
    }

    @Override
    public void assignment() {
        pirosCsik = new OneSpriteStaticActor(game,PIROS);
        zoldCsik = new OneSpriteStaticActor(game,ZOLD);
        marancsicsElete = new MyLabel(game,"Marancsics élete", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
    }

    @Override
    public void setSizes() {
        zoldCsik.setSize(new ResponseViewport(900).getWorldWidth()-225,60);
        pirosCsik.setSize(0,60);
    }

    @Override
    public void setPositions() {
        marancsicsElete.setAlignment(0);
        marancsicsElete.setPosition(zoldCsik.getX()+zoldCsik.getWidth()/2-marancsicsElete.getWidth()/2,zoldCsik.getY()+zoldCsik.getHeight()/2-marancsicsElete.getHeight()/2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(zoldCsik);
        addActor(pirosCsik);
        addActor(marancsicsElete);
    }

    @Override
    public float getWidth() {
        return zoldCsik.getWidth();
    }

    @Override
    public float getHeight() {
        return zoldCsik.getHeight();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(marancsicsBoss != null) {
            if(pirosCsik.getWidth() < zoldCsik.getWidth() * ((250-marancsicsBoss.hp) / 250.0f)) {
                pirosCsik.setWidth(pirosCsik.getWidth() + 15);
            }
            pirosCsik.setX(zoldCsik.getX() + zoldCsik.getWidth() - pirosCsik.getWidth());
        }
    }
}
