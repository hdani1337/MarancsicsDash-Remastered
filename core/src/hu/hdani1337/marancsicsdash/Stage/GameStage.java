package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2DWorldHelper;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.Box2dStage;
import hu.csanyzeg.master.MyBaseClasses.Box2dWorld.WorldBodyEditorLoader;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.IPrettyStage;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Blood;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.Marancsics;
import hu.hdani1337.marancsicsdash.Actor.MarancsicsBoss;
import hu.hdani1337.marancsicsdash.Actor.Mushroom;
import hu.hdani1337.marancsicsdash.Actor.Tank;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;

public class GameStage extends Box2dStage implements IPrettyStage {

    public static AssetList assetList = new AssetList();
    static {
        assetList.collectAssetDescriptor(Zsolti.class,assetList);
        assetList.collectAssetDescriptor(Marancsics.class,assetList);
        assetList.collectAssetDescriptor(Tank.class,assetList);
        assetList.collectAssetDescriptor(Mushroom.class,assetList);
        assetList.collectAssetDescriptor(Coin.class,assetList);
        assetList.collectAssetDescriptor(MarancsicsBoss.class,assetList);
        assetList.collectAssetDescriptor(Blood.class,assetList);
        assetList.collectAssetDescriptor(Background.class,assetList);
    }

    public boolean isShakeScreen;//KÉPERNYŐ MEGRÁZÁSA
    public WorldBodyEditorLoader loader;

    /**
     * OBJEKTUMOK
     * **/
    public Zsolti zsolti;//ZSÁÚTTI
    public Marancsics marancsics;//TOMI BÁ'
    public ArrayList<Coin> coins;//ÉRME LISTA
    public Mushroom mushroom;//GOMBA
    public ArrayList<Background> backgrounds;//HÁTTÉR LISTA
    public ArrayList<Tank> tanks;//TANK LISTA
    public ArrayList<Blood> blood;//TANK LISTA

    public static boolean isAct;

    public GameStage(MyGame game) {
        super(new ResponseViewport(9), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        loader = new WorldBodyEditorLoader("bodies.json");
        isAct = true;
        isShakeScreen = false;
        zsolti = new Zsolti(game, world, loader);
        marancsics = new Marancsics(game, world, loader);
        tanks = new ArrayList<>();
        coins = new ArrayList<>();
        blood = new ArrayList<>();
        mushroom = new Mushroom(game,world,loader);
        backgrounds = new ArrayList<>();
        generateBaseBackgrounds();
    }

    @Override
    public void setSizes() {

    }

    @Override
    public void setPositions() {
        zsolti.setPosition(2,3);
        mushroom.setPosition(7,5);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(zsolti);
        addActor(marancsics);
        //addActor(mushroom);
    }

    private int offset = 1;

    private void shakeScreen(){
        /**
         * SZINUSZ-KOSZINUSZ FÜGGVÉNYEK SEGÍTSÉGÉVEL MOZGATJUK A VIEVPORTOT
         * **/
        getViewport().setScreenX((int) (Math.sin(offset)*15));
        getViewport().setScreenY((int) (Math.cos(offset)*15));
        offset++;
    }

    private void generateBaseBackgrounds(){
        /**
         * KEZDETNEK 3 HÁTTÉR ELÉG
         * **/
        for (int i = 0; i < 3; i++){
            backgrounds.add(new Background(game, Background.BackgroundType.SZAHARA, world, getViewport()));
            backgrounds.get(i).setX(backgrounds.get(i).getWidth()*i);
            addActor(backgrounds.get(i));
            backgrounds.get(i).setZIndex(0);
        }
    }

    private float tankElapsed = elapsedTime;

    @Override
    public void act(float delta) {
        super.act(delta);
        /**
         * AMÍG NINCS VÉGE A JÁTÉKNAK ADDIG FUT AZ ACT
         * **/
        if(isAct) {
            /**
             * HA MARANCSICS VAGY SUPERZSOLTI BELEÉRT A TANKBA, AKKOR RÁZZUK MEG A KÉPERNYŐT
             * AZ ÉRINTKEZÉS BEFEJEZTÉVEL ÁLLÍTSUK VISSZA A VIEWPORTOT ÉS AZ OFFSETET
             *
             * U.I: A KAMERA MOZGATÁSÁHOZ EGYSZERŰEN AUTISTA VAGYOK, EZÉRT A VIEWPORTOT MOZGATOM
             * **/
            if (isShakeScreen) {
                shakeScreen();
            } else {
                offset = 1;
                getViewport().setScreenX(0);
                getViewport().setScreenY(0);
            }

            /**
             * HA AZ UTOLSÓ ELŐTTI HÁTTÉR MÁR ÉPPENHOGY BEÉR A KÉPERNYŐRE, AKKOR RAKJUNK BE ÚJ HÁTTERET
             * */
            if (backgrounds.get(backgrounds.size() - 2).getX() < getViewport().getWorldWidth()) {
                backgrounds.add(new Background(game, Background.BackgroundType.SZAHARA, world, getViewport()));
                backgrounds.get(backgrounds.size() - 1).setX(backgrounds.get(backgrounds.size() - 2).getX() + backgrounds.get(backgrounds.size() - 2).getWidth());
                addActor(backgrounds.get(backgrounds.size() - 1));
                backgrounds.get(backgrounds.size() - 1).setZIndex(0);
            }

            /**
             * RANDOM IDŐKÖZÖNKÉNT ÚJ TANK HOZZÁADÁSA
             * */
            if (elapsedTime > tankElapsed) {
                tankElapsed = (float) (elapsedTime + Math.random() * 5);
                tanks.add(new Tank(game, world, loader));
                addActor(tanks.get(tanks.size() - 1));
            }

            /**
             * HA A TANK MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Tank tank : tanks) {
                if (tank.getX() < -tank.getWidth() * 2) {
                    tank.remove();
                    tanks.remove(tank);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
            }

            /**
             * HA A HÁTTÉR MÁR BŐVEN NEM LÁTSZIK, AKKOR TÁVOLÍTSUK EL
             * **/
            for (Background background : backgrounds) {
                if (background.getX() < -background.getWidth() * 2) {
                    background.remove();
                    backgrounds.remove(background);
                    break;//LISTA HOSSZÁNAK VÁLTOZÁSA MIATTI EXCEPTION ELKERÜLÉSE EGY BREAKKEL
                }
            }

            /**
             * MEGÁLLÍTÁS UTÁN HA MÉG NEM MOZOGNAK AZ ANIMATED ACTOROK AKKOR MOZOGJANAK
             * **/
            //ZSOLTI ÚJRAINDÍTÁSA
            if(zsolti.getFps() == 0) {
                zsolti.setFps(12);
                ((Box2DWorldHelper) zsolti.getActorWorldHelper()).getBody().setActive(true);
            }

            //MARANCSICS ÚJRAINDÍTÁSA
            if(marancsics.getFps() == 0) {
                ((Box2DWorldHelper) marancsics.getActorWorldHelper()).getBody().setActive(true);
                marancsics.setFps(12);
            }

            //TANKOK ÚJRAINDÍTÁSA
            for (Tank t : tanks)
                if(t.getFps() == 0) {
                    ((Box2DWorldHelper) t.getActorWorldHelper()).getBody().setActive(true);
                    t.setFps(15);
                }
        }
        /**
         * HA VÉGE A JÁTÉKNAK AKKOR ZSOLTI VÉREZZEN
         * **/
        else if(zsolti.isDead){
            if(blood.size() < 128) {
                for (int i = 0; i < 4; i++){
                    blood.add(new Blood(game, world, zsolti));
                    addActor(blood.get(blood.size() - 1));
                }
            }
        }
        /**
         * HA MEGÁLLÍTJUK A JÁTÉKOT AKKOR AZ ANIMATED ACTOROK NE MOZOGJANAK
         * **/
        else{
            //ZSOLTI MEGÁLLÍTÁSA
            if(zsolti.getFps() != 0) {
                ((Box2DWorldHelper) zsolti.getActorWorldHelper()).getBody().setActive(false);
                zsolti.setFps(0);
            }

            //MARANCSICS MEGÁLLÍTÁSA
            if(marancsics.getFps() != 0) {
                ((Box2DWorldHelper) marancsics.getActorWorldHelper()).getBody().setActive(false);
                marancsics.setFps(0);
            }

            //TANKOK MEGÁLLÍTÁSA
            for (Tank t : tanks)
                if(t.getFps() != 0) {
                    ((Box2DWorldHelper) t.getActorWorldHelper()).getBody().setActive(false);
                    t.setFps(0);
                }

            //HA RÁZKÓDIK A KÉPERNYŐ AKKOR ÁLLÍTSUK VISSZA
            offset = 1;
            getViewport().setScreenX(0);
            getViewport().setScreenY(0);
        }
    }
}
