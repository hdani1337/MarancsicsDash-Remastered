package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.Screen.BossScreen;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.SoundManager.bossMusic;
import static hu.hdani1337.marancsicsdash.SoundManager.gameMusic;

public class VictoryStage extends PrettyStage {
    //region AssetList
    public static final String BLACK_TEXTURE = "pic/fekete.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(TextBox.class,assetList);
        assetList.addTexture(BLACK_TEXTURE);
    }
    //endregion
    //region Változók
    private TextBox info;
    private TextBox pontok;
    private TextBox again;
    private TextBox menu;

    private OneSpriteStaticActor black;
    //endregion
    //region Konstruktor
    public VictoryStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }
    //endregion
    //region Absztrakt metódusok
    @Override
    public void assignment() {
        info = new TextBox(game, "Gratulálok,\nlegyözted Marancsicsot!",1.75f);
        pontok = new TextBox(game, "Elért pontszámod\n-NULL-");
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
        info.setPosition(getViewport().getWorldWidth()/2-info.getWidth()/2,getViewport().getWorldHeight()*0.65f);
        pontok.setPosition(getViewport().getWorldWidth()/2-pontok.getWidth()/2,getViewport().getWorldHeight()*0.45f);
        again.setPosition(getViewport().getWorldWidth()/2-again.getWidth()/2,getViewport().getWorldHeight()*0.33f);
        menu.setPosition(getViewport().getWorldWidth()/2-menu.getWidth()/2,getViewport().getWorldHeight()*0.2f);
    }

    @Override
    public void addListeners() {
        again.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                game.setScreenWithPreloadAssets(GameScreen.class, false, new LoadingStage(game));
                preferences.putLong("coin", Coin.coin);
                preferences.flush();
            }
        });

        menu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if(!muted) gameMusic.stop();
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
                preferences.putLong("coin", Coin.coin);
                preferences.flush();
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        /**
         * MINDEN ACTOR LÁTHATATLAN LESZ
         * EZUTÁN HOZZÁADJUK AZ ACTOROKAT A STAGEHEZ
         * ÁTVÁLTJUK AZ {@link addedActors} VÁLTOZÓT IGAZRA, ÍGY CSAK EGYSZER KERÜLNEK FEL A STAGERE AZ ACTOROK, MERT TÖBBSZÖR NEM HÍVÓDIK MEG EZ A METÓDUS
         * **/
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

    @Override
    public void afterInit() {
        /**
         * MIVEL AZ addActors() METÓDUS AUTOMATIKUSAN LEFUT, EZÉRT EGYSZER HOZZÁADJA A GOMBOKAT A STAGEHEZ 0-ÁS ALPHÁVAL
         * EZÉRT A STAGE LÉTREHOZÁSA UTÁN EL KELL ŐKET TÁVOLÍTANI, NEHOGY VÉLETLENÜL KATTINTSUNK A GOMBOKRA
         * **/
        black.remove();
        info.remove();
        pontok.remove();
        again.remove();
        menu.remove();
        addedActors = false;
    }
    //endregion
    //region Act metódusai
    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * HA VÉGE VAN A JÁTÉKNAK
         * **/
        if(getScreen() != null){
            if(getScreen() instanceof BossScreen){
                if(!BossStage.isAct && !Zsolti.isDead && MarancsicsBoss.hp <= 0){
                    bossMusic.stop();
                    makeStage();
                }
            }
        }
    }

    private boolean addedActors;
    private float alpha;
    private void makeStage(){
        pontok.setText("Elért pontszámod\n"+GameStage.score);
        setPositions();

        //Adjuk hozzá a gombokat a stagehez ha még nincsenek rajta
        if(!addedActors)
            addActors();

        //Áttűnés
        if(alpha < 0.99f)
            alpha += 0.01f;
        else
            alpha = 1;

        black.setAlpha(alpha*0.6f);
        info.setAlpha(alpha);
        pontok.setAlpha(alpha);
        again.setAlpha(alpha);
        menu.setAlpha(alpha);
        //Áttűnés vége
    }
    //endregion
}
