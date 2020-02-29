package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;

public class GameOverStage extends PrettyStage {

    public static final String BLACK_TEXTURE = "pic/fekete.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.addTexture(BLACK_TEXTURE);
    }

    private TextBox info;
    private TextBox pontok;
    private TextBox again;
    private TextBox menu;

    private OneSpriteStaticActor black;

    public GameOverStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void assignment() {
        info = new TextBox(game, "Vesztettél!",2f);
        pontok = new TextBox(game, "Pontszámok\nHamarosan!");
        again = new TextBox(game, "Új játék",1.5f);
        menu = new TextBox(game, "Menü",1.5f);

        black = new OneSpriteStaticActor(game, BLACK_TEXTURE);

        addedActors = false;
        alpha = 0;
    }

    @Override
    public void setSizes() {
        black.setSize(getViewport().getWorldWidth(),getViewport().getWorldHeight());
        menu.setWidth(menu.getWidth()*1.1f);
        pontok.setWidth(pontok.getWidth()*0.7f);
    }

    @Override
    public void setPositions() {
        info.setPosition(getViewport().getWorldWidth()/2-info.getWidth()/2,getViewport().getWorldHeight()*0.75f);
        pontok.setPosition(getViewport().getWorldWidth()/2-pontok.getWidth()/2,getViewport().getWorldHeight()*0.52f);
        again.setPosition(getViewport().getWorldWidth()/2-again.getWidth()/2,getViewport().getWorldHeight()*0.37f);
        menu.setPosition(getViewport().getWorldWidth()/2-menu.getWidth()/2,getViewport().getWorldHeight()*0.25f);
    }

    @Override
    public void addListeners() {
        again.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenWithPreloadAssets(GameScreen.class, false, new LoadingStage(game));
            }
        });

        menu.addListener(new ClickListener(){
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
        black.setAlpha(0);
        info.setAlpha(0);
        pontok.setAlpha(0);
        again.setAlpha(0);
        menu.setAlpha(0);

        addActor(black);
        addActor(info);
        addActor(pontok);
        addActor(again);
        addActor(menu);

        addedActors = true;
    }

    private boolean addedActors;
    private float alpha;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!isAct){
            if(!addedActors) addActors();

            if(alpha < 0.99f) alpha += 0.01f;
            else alpha = 1;

            black.setAlpha(alpha*0.6f);
            info.setAlpha(alpha);
            pontok.setAlpha(alpha);
            again.setAlpha(alpha);
            menu.setAlpha(alpha);
        }
    }
}
