package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.Screen.GameScreen;

import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.SoundManager.gameMusic;
import static hu.hdani1337.marancsicsdash.Stage.GameStage.isAct;

public class PauseStage extends PrettyStage {
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

    public PauseStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void assignment() {
        info = new TextBox(game, "Megállítva",2f);
        pontok = new TextBox(game, "Pontszámok\nHamarosan!");
        again = new TextBox(game, "Folytatás",1.5f);
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
                isAct = true;
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

    private boolean addedActors;
    private float alpha;

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * HA MEGÁLL A JÁTÉK DE ZSOLTI MÉG NEM HALT MEG (PAUSE)
         * **/
        if(!isAct && !Zsolti.isDead){
            MarancsicsDash.presenceDetail = "Paused the game";
            UpdatePresence();
            if(getScreen() != null && getScreen() instanceof GameScreen){
                if(!pontok.text.equals("Jelenlegi pontszámod\n"+((GameScreen)getScreen()).gameStage.score)) {
                    pontok.setText("Jelenlegi pontszámod\n"+((GameScreen)getScreen()).gameStage.score);
                    pontok.setX(getViewport().getWorldWidth()/2-pontok.getWidth()/2);
                }
            }
            //Adjuk hozzá a gombokat a stagehez ha még nincsenek rajta
            if(!addedActors)
                addActors();

            //Áttűnés
            if(alpha < 0.95f)
                alpha += 0.05f;
            else
                alpha = 1;

            black.setAlpha(alpha*0.6f);
            info.setAlpha(alpha);
            pontok.setAlpha(alpha);
            again.setAlpha(alpha);
            menu.setAlpha(alpha);
            //Áttűnés vége
        }
        /**
         * HA FOLYTATÓDIK A JÁTÉK ÉS FENTVANNAK A GOMBOK A STAGEN
         * **/
        else if(isAct && addedActors){
            //Áttűnéssel tűnnek el a stageről
            if(alpha > 0.05f) {
                alpha -= 0.05f;
                black.setAlpha(alpha*0.6f);
                info.setAlpha(alpha);
                pontok.setAlpha(alpha);
                again.setAlpha(alpha);
                menu.setAlpha(alpha);
            }
            //Ha már láthatatlanok, akkor eltávolítjuk őket a stageről
            else {
                alpha = 0;
                black.remove();
                info.remove();
                pontok.remove();
                again.remove();
                menu.remove();
                addedActors = false;
            }
        }
    }
}
