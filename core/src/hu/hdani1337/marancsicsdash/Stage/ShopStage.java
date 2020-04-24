package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimer;
import hu.csanyzeg.master.MyBaseClasses.Timers.TickTimerListener;
import hu.csanyzeg.master.MyBaseClasses.Timers.Timer;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.Actor.Background;
import hu.hdani1337.marancsicsdash.Actor.Coin;
import hu.hdani1337.marancsicsdash.Actor.SuperCoin;
import hu.hdani1337.marancsicsdash.Actor.Zsolti;
import hu.hdani1337.marancsicsdash.HudActors.InstantBoss;
import hu.hdani1337.marancsicsdash.HudActors.Left;
import hu.hdani1337.marancsicsdash.HudActors.Logo;
import hu.hdani1337.marancsicsdash.HudActors.Right;
import hu.hdani1337.marancsicsdash.HudActors.ShopBackgroundPreview;
import hu.hdani1337.marancsicsdash.HudActors.ShopCategory;
import hu.hdani1337.marancsicsdash.HudActors.ShopCategoryType;
import hu.hdani1337.marancsicsdash.HudActors.ShopItem;
import hu.hdani1337.marancsicsdash.HudActors.ShopItemType;
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.UpdatePresence;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.MenuBackgroundStage.MENU_BG_TEXTURE;

public class ShopStage extends PrettyStage {

    public static final String PAYSOUND = "sound/pay.mp3";
    public static final String NOMONEY = "sound/error.mp3";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
        assetList.addSound(PAYSOUND);
        assetList.addSound(NOMONEY);
        SoundManager.load(assetList);
        assetList.collectAssetDescriptor(ShopItem.class,assetList);
        assetList.collectAssetDescriptor(ShopCategory.class,assetList);
    }

    public ShopStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    /**
     * Változók a mentésből
     * **/
    public static boolean boughtInstantBoss = preferences.getBoolean("boughtInstantBoss");
    public static boolean boughtSiberia = preferences.getBoolean("boughtSiberia");
    public static boolean boughtZala = preferences.getBoolean("boughtZala");
    public static boolean boughtDesert = preferences.getBoolean("boughtDesert");
    public static boolean boughtOcean = preferences.getBoolean("boughtOcean");
    public static boolean boughtZsolti = preferences.getBoolean("boughtZsolti");
    public static boolean boughtDouble = preferences.getBoolean("boughtDouble");
    public static boolean boughtCoin = preferences.getBoolean("boughtCoin");

    /**
     * Actorok
     * **/
    private OneSpriteStaticActor MenuBackground;
    private TextBox back;//Vissza a menübe gomb
    private Coin coin;//Pénz ikon
    public MyLabel coinText;//Pénz mennyiség label
    private Logo shopLogo;//Bolt logo

    /**
     * Megvehető dolgok
     * **/
    private ArrayList<ShopItem> backgrounds;//Hátterek
    private ArrayList<ShopItem> abilities;//Képességek
    private ArrayList<ShopCategory> categories;//Kategóriák

    public ShopCategoryType selectedCategory;

    @Override
    public void assignment() {
        selectedCategory = ShopCategoryType.NULL;
        MarancsicsDash.presenceDetail = "Shopping";
        UpdatePresence();
        SoundManager.assign();
        if(!muted)
            SoundManager.menuMusic.play();
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        back = new TextBox(game, "Vissza a menübe");
        coin = new Coin(game, true);
        coinText = new MyLabel(game, "10000", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
        shopLogo = new Logo(game, Logo.LogoType.SHOP);
        coinText.setText(Coin.coin+"");

        backgrounds = new ArrayList<>();
        abilities = new ArrayList<>();
        categories = new ArrayList<>();
        makeItems();
    }

    private void makeItems(){
        /**Hátterek**/
        backgrounds.add(new ShopItem(game, Background.BackgroundType.SZAHARA));
        backgrounds.add(new ShopItem(game, Background.BackgroundType.SZIBERIA));
        backgrounds.add(new ShopItem(game, Background.BackgroundType.ZALA));
        backgrounds.add(new ShopItem(game, Background.BackgroundType.OCEAN));

        /**Képességek**/
        abilities.add(new ShopItem(game, false, ShopItemType.DOUBLEJUMP, "Dupla ugrás"));
        abilities.add(new ShopItem(game, false, ShopItemType.INSTANTBOSS, "Instant Boss"));
        abilities.add(new ShopItem(game, false, ShopItemType.SUPERCOIN, "Pénzesö"));
        abilities.add(new ShopItem(game, false, ShopItemType.SUPERZSOLTI, "Super Zsolti"));

        /**Kategóriák**/
        categories.add(new ShopCategory(game, ShopCategoryType.SKINS));
        categories.add(new ShopCategory(game, ShopCategoryType.ABILITIES));
        categories.add(new ShopCategory(game, ShopCategoryType.BACKGROUND));
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
        coin.setSize(coin.getWidth()*0.7f,coin.getHeight()*0.7f);
        back.setPosition(getViewport().getWorldWidth() - (back.getWidth() + 45),50);
        coin.setPosition(15, getViewport().getWorldHeight()-15-coin.getHeight());
        coinText.setPosition(coin.getX() + coin.getWidth() + 10, coin.getY() + coin.getHeight()/4);
        shopLogo.setPosition(getViewport().getWorldWidth()/2 - shopLogo.getWidth()/2, getViewport().getWorldHeight() - shopLogo.getHeight()*1.8f);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        for (int i = 0; i < backgrounds.size(); i++){
            switch (i){
                case 0:{
                    if(getViewport().getWorldWidth()/getViewport().getWorldHeight()>=2)
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()/2-backgrounds.get(i).getWidth()-15,getViewport().getWorldHeight()/2+5);
                    else
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()*0.55f-backgrounds.get(i).getWidth()-15,getViewport().getWorldHeight()/2+5);
                    break;
                }
                case 1:{
                    if(getViewport().getWorldWidth()/getViewport().getWorldHeight()>=2)
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()/2+15,getViewport().getWorldHeight()/2+5);
                    else
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()*0.55f+15,getViewport().getWorldHeight()/2+5);
                    break;
                }
                case 2:{
                    if(getViewport().getWorldWidth()/getViewport().getWorldHeight()>=2)
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()/2-backgrounds.get(i).getWidth()-15,getViewport().getWorldHeight()/2-backgrounds.get(i).getHeight()-25);
                    else
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()*0.55f-backgrounds.get(i).getWidth()-15,getViewport().getWorldHeight()/2-backgrounds.get(i).getHeight()-25);
                    break;
                }
                case 3:{
                    if(getViewport().getWorldWidth()/getViewport().getWorldHeight()>=2)
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()/2+15,getViewport().getWorldHeight()/2-backgrounds.get(i).getHeight()-25);
                    else
                        backgrounds.get(i).setPosition(getViewport().getWorldWidth()*0.55f+15,getViewport().getWorldHeight()/2-backgrounds.get(i).getHeight()-25);
                    break;
                }
            }
        }
        for (int i = 0; i < categories.size(); i++){
            categories.get(i).setX(175-categories.get(i).getWidth()/2);
            if(i != 0)
                categories.get(i).setY(categories.get(i-1).getY()+categories.get(i-1).getHeight()+15);
            else
                categories.get(i).setY(getViewport().getWorldHeight()/6);
        }
        for (int i = 0; i < abilities.size(); i++){
            if(getViewport().getWorldWidth()/getViewport().getWorldHeight()<2){
                abilities.get(i).setPosition(categories.get(1).getX()+categories.get(1).getWidth()+120+i*275,getViewport().getWorldHeight() / 2 - 100);
            }else {
                abilities.get(i).setPosition(getViewport().getWorldWidth()/2 - 475 + i * 275, getViewport().getWorldHeight() / 2 - 100);
            }
        }
    }

    @Override
    public void addListeners() {
        /**
         * Vissza a menübe
         * **/
        back.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                /**
                 * Változók mentése
                 * **/
                preferences.putLong("coin", Coin.coin);
                preferences.putBoolean("boughtInstantBoss", boughtInstantBoss);
                preferences.putBoolean("boughtSiberia", boughtSiberia);
                preferences.putBoolean("boughtZala", boughtZala);
                preferences.putBoolean("boughtDesert", boughtDesert);
                preferences.putBoolean("boughtOcean", boughtOcean);
                preferences.putBoolean("boughtZsolti", boughtZsolti);
                preferences.putBoolean("boughtDouble", boughtDouble);
                preferences.putBoolean("boughtCoin", boughtCoin);
                preferences.flush();
                setBack = true;//Kifele áttűnés
            }
        });
    }

    @Override
    public void setZIndexes() {

    }

    /**
     * Minden actort hozzáadunk a stagehez, csak a Visible tulajdonságaikat állítjuk át
     * **/
    @Override
    public void addActors() {
        addActor(MenuBackground);
        addActor(shopLogo);
        addActor(coin);
        addActor(coinText);
        addActor(back);
        MenuBackground.setAlpha(0.25f);
        for (int i = 0; i < backgrounds.size(); i++) addActor(backgrounds.get(i));
        for (int i = 0; i < abilities.size(); i++) addActor(abilities.get(i));
        for (int i = 0; i < categories.size(); i++) {
            categories.get(i).setColor(Color.GRAY);
            addActor(categories.get(i));
        }
    }

    float alpha = 0;
    float bgAlpha = 1;
    boolean setBack = false;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) {
                alpha += 0.05;
                setAlpha();
            }
            else alpha = 1;
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
            if (alpha > 0.05) {
                setAlpha();
                alpha -= 0.05;
                if(bgAlpha<0.95) bgAlpha+= 0.05;
                MenuBackground.setAlpha(bgAlpha);
            } else {
                //Ha már nem látszanak akkor megyünk vissza a menübe
                alpha = 0;
                setAlpha();
                game.setScreenBackByStackPopWithPreloadAssets(new LoadingStage(game));
                addTimer(new TickTimer(1,false,new TickTimerListener(){
                    @Override
                    public void onTick(Timer sender, float correction) {
                        super.onTick(sender, correction);
                        setBack = false;
                    }
                }));
            }
        }
        if(alpha == 1) {
            switch (selectedCategory) {
                case NULL: {
                    for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                    for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                    for (int i = 0; i < categories.size(); i++)
                        if (categories.get(i).getColor() != Color.GRAY)
                            categories.get(i).setColor(Color.GRAY);
                    break;
                }
                case SKINS: {
                    for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                    for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                    for (int i = 0; i < categories.size(); i++)
                        categories.get(i).setColor(Color.GRAY);
                    categories.get(0).setColor(1, 1, 1, 1);

                    break;
                }
                case ABILITIES: {
                    for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                    for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(1);
                    for (int i = 0; i < categories.size(); i++)
                        categories.get(i).setColor(Color.GRAY);
                    categories.get(1).setColor(1, 1, 1, 1);
                    break;
                }
                case BACKGROUND: {
                    for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(1);
                    for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                    for (int i = 0; i < categories.size(); i++)
                        categories.get(i).setColor(Color.GRAY);
                    categories.get(2).setColor(1, 1, 1, 1);
                    break;
                }
            }
        }

        if(bgAlpha>0.25 && !setBack){
            bgAlpha-=0.025;
            MenuBackground.setAlpha(bgAlpha);
        }
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        switch (selectedCategory) {
            case NULL: {
                for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                for (int i = 0; i < categories.size(); i++) categories.get(i).setAlpha(alpha);
                break;
            }
            case SKINS: {
                for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                for (int i = 0; i < categories.size(); i++) categories.get(i).setAlpha(alpha);
                System.out.println();
                break;
            }
            case ABILITIES: {
                for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(0);
                for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(alpha);
                for (int i = 0; i < categories.size(); i++) categories.get(i).setAlpha(alpha);
                break;
            }
            case BACKGROUND: {
                for (int i = 0; i < backgrounds.size(); i++) backgrounds.get(i).setAlpha(alpha);
                for (int i = 0; i < abilities.size(); i++) abilities.get(i).setAlpha(0);
                for (int i = 0; i < categories.size(); i++) categories.get(i).setAlpha(alpha);
                break;
            }
        }
        coin.setColor(1, 1, 1, alpha);
        coinText.setColor(1, 1, 1, alpha);
        shopLogo.setColor(1, 1, 1, alpha);
        back.setAlpha(alpha);
    }
}
