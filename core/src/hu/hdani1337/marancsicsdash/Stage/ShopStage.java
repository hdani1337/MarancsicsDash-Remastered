package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
import hu.hdani1337.marancsicsdash.HudActors.TextBox;
import hu.hdani1337.marancsicsdash.SoundManager;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.muted;
import static hu.hdani1337.marancsicsdash.MarancsicsDash.preferences;
import static hu.hdani1337.marancsicsdash.Stage.MenuStage.MENU_BG_TEXTURE;

public class ShopStage extends PrettyStage {

    public static final String PAYSOUND = "sound/pay.mp3";
    public static final String NOMONEY = "sound/error.mp3";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture(MENU_BG_TEXTURE);
        assetList.addSound(PAYSOUND);
        assetList.addSound(NOMONEY);
        SoundManager.load(assetList);
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
    private TextBox termekNevAr;//Termék neve és ára
    private TextBox purchase;//Vásárlás gomb
    private TextBox back;//Vissza a menübe gomb
    private Coin coin;//Pénz ikon
    private MyLabel coinText;//Pénz mennyiség label
    private Logo shopLogo;//Bolt logo
    private ShopBackgroundPreview shopBackgroundPreview;//Háttér előnézete
    private TextBox shopBackgroundPreviewBackground;//Háttér a háttér előnézetének
    private InstantBoss instantBoss;//Instant boss előnézete
    private Left left;//Balra gomb
    private Right right;//Jobbra gomb
    private Zsolti superZs;//Super Zsolti
    private Zsolti doubleJumpZs;//Double Jump Zsolti
    private SuperCoin superCoin;//Super Coin

    /**
     * Hangok
     * **/
    private Sound paySound;//Fizetés hangja
    private Sound noMoney;//Nincs elég pénz hangja

    /**
     * Egyéb
     * **/
    private int itemID;//Képernyőn megjelenő termék ID-ja

    @Override
    public void assignment() {
        SoundManager.assign();
        if(!muted)
            SoundManager.menuMusic.play();
        MenuBackground = new OneSpriteStaticActor(game,MENU_BG_TEXTURE);
        termekNevAr = new TextBox(game, "asd\nasd");
        purchase = new TextBox(game, "Vásárlás");
        back = new TextBox(game, "Vissza a menübe");
        coin = new Coin(game, true);
        coinText = new MyLabel(game, "10000", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {

            }
        };
        shopLogo = new Logo(game, Logo.LogoType.SHOP);
        shopBackgroundPreview = new ShopBackgroundPreview(game);
        shopBackgroundPreview.step(Background.BackgroundType.SZIBERIA);
        instantBoss = new InstantBoss(game);
        left = new Left(game);
        right = new Right(game);
        shopBackgroundPreviewBackground = new TextBox(game," ");
        paySound = game.getMyAssetManager().getSound(PAYSOUND);
        noMoney = game.getMyAssetManager().getSound(NOMONEY);
        itemID = 0;
        superZs = new Zsolti(game);
        doubleJumpZs = new Zsolti(game){
            float time = 0;

            @Override
            public void act(float delta) {
                super.act(delta);
                if(itemID != 6) time = -0.5f;
                else time += delta;
                if(time >= 0) {
                    if (time < 0.5) {
                        setY(getY() + 8);
                        setRotation(getRotation() + 5);
                    } else if (time < 0.7) {
                        setY(getY() - 8);
                        setRotation(getRotation() - 5);
                    } else if (time < 0.85) {
                        setY(getY() + 8);
                        setRotation(getRotation() + 5);
                    } else if (time < 1.3) {
                        setY(getY() - 8);
                        setRotation(getRotation() - 5);
                    } else time = -1.5f;
                }
            }
        };
        superCoin = new SuperCoin(game,false);
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > MenuBackground.getWidth()) MenuBackground.setWidth(getViewport().getWorldWidth());
        left.setSize(120,120);
        right.setSize(120,120);
        superCoin.setSize(160,160);
        shopBackgroundPreviewBackground.setSize(shopBackgroundPreview.getWidth() + 16, shopBackgroundPreview.getHeight() + 18);
        coin.setSize(coin.getWidth()*0.7f,coin.getHeight()*0.7f);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < MenuBackground.getWidth()) MenuBackground.setX((getViewport().getWorldWidth()-MenuBackground.getWidth())/2);
        instantBoss.setPosition(getViewport().getWorldWidth()/2-instantBoss.getWidth()/2,getViewport().getWorldHeight()/2-instantBoss.getHeight()/2);
        shopBackgroundPreview.setPosition(getViewport().getWorldWidth()/2-shopBackgroundPreview.getWidth()/2,getViewport().getWorldHeight()/2-shopBackgroundPreview.getHeight()/2 + 50);
        shopBackgroundPreviewBackground.setPosition(shopBackgroundPreview.getX() - 8, shopBackgroundPreview.getY() - 10);
        termekNevAr.setPosition(getViewport().getWorldWidth()/2-termekNevAr.getWidth()/2,shopBackgroundPreview.getY() - 150);
        purchase.setPosition(getViewport().getWorldWidth()/2-purchase.getWidth()/2,termekNevAr.getY() - 90);
        left.setPosition(termekNevAr.getX() - left.getWidth() - 30,termekNevAr.getY());
        right.setPosition(termekNevAr.getX() + termekNevAr.getWidth() + 30,termekNevAr.getY());
        shopLogo.setPosition(getViewport().getWorldWidth()/2 - shopLogo.getWidth()/2, getViewport().getWorldHeight() - shopLogo.getHeight()*1.8f);
        back.setPosition(getViewport().getWorldWidth() - (back.getWidth() + 45),50);
        coin.setPosition(15, getViewport().getWorldHeight()-15-coin.getHeight());
        coinText.setPosition(coin.getX() + coin.getWidth() + 10, coin.getY() + coin.getHeight()/4);
        superZs.setPosition(getViewport().getWorldWidth()/2-superZs.getWidth()/2,getViewport().getWorldHeight()/2-superZs.getHeight()/2 + 25);
        doubleJumpZs.setPosition(superZs.getX(),superZs.getY());
        superCoin.setPosition(getViewport().getWorldWidth()/2-superCoin.getWidth()/2,getViewport().getWorldHeight()/2-superCoin.getHeight()/2 + 25);
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

        /**
         * Jobbra léptető gomb
         * **/
        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                /**
                 * Amíg az ID nem éri el a maximumot, ami most 7, addig mindig 1-el léptetjük felfele
                 * Frissítjül a Labeleket és actorokat
                 * **/
                if (itemID == 7) {
                    itemID = 7;
                } else {
                    itemID++;
                }
                setTexts();
                setActor();
            }
        });

        /**
         * Balra léptető gomb
         * **/
        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                /**
                 * Amíg az ID nem éri el a minimumot, ami most 0, addig mindig 1-el léptetjük lefelé
                 * Frissítjül a Labeleket és actorokat
                 * **/
                if (itemID == 0) {
                    itemID = 0;
                } else {
                    itemID--;
                }
                setTexts();
                setActor();
            }
        });

        /**
         * Vásárlás gomb
         * **/
        purchase.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (itemID == 0)
                {
                    /**
                     * InstantBoss, példaként itt kommentezve van minden
                     * **/
                    if(!boughtInstantBoss) {
                        /**
                         * Ha még nem vette meg a játékos az InstantBosst
                         * **/
                        if (Coin.coin >= 100) {
                            /**
                             * És ha van elég pénze akkor megveheti
                             * **/
                            if (!muted) paySound.play();//Ha nincs némítva a játék akkor fizetéshang lejátszása
                            Coin.coin -= 100;//Az összeg levonása
                            coinText.setText(""+Coin.coin);//Birtokolt pénzmennyiség label frissítése
                            termekNevAr.setText("Instant Boss\nMár megvetted!");//Termék árának frissítése
                            purchase.remove();//Vásárlás gomb eltávolítása
                            boughtInstantBoss = true;//Innentől kezdve birtokolja az InstantBosst a játékok

                            /**
                             * Adatok mentése
                             * **/
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtInstantBoss", boughtInstantBoss);
                            preferences.flush();
                        }

                        /**
                         * Ha nincs elég pénze és nincs némítva a játék akkor hibahang lejátszása
                         * **/
                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 1)
                {
                    /**
                     * Szibéria háttér
                     * **/
                    if(!boughtSiberia) {
                        if (Coin.coin >= 200) {
                            if (!muted) paySound.play();
                            Coin.coin -= 200;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Szibéria\nMár megvetted!");
                            boughtSiberia = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtSiberia", boughtSiberia);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 2)
                {
                    /**
                     * Zala háttér
                     * **/
                    if(!boughtZala) {
                        if (Coin.coin >= 200) {
                            if (!muted) paySound.play();
                            Coin.coin -= 200;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Zala\nMár megvetted!");
                            boughtZala = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtZala", boughtZala);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 3)
                {
                    /**
                     * Szahara háttér
                     * **/
                    if(!boughtDesert) {
                        if (Coin.coin >= 200) {
                            if (!muted) paySound.play();
                            Coin.coin -= 200;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Szahara\nMár megvetted!");
                            boughtDesert = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtDesert", boughtDesert);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 4)
                {
                    /**
                     * Óceán háttér
                     * **/
                    if(!boughtOcean) {
                        if (Coin.coin >= 200) {
                            if (!muted) paySound.play();
                            Coin.coin -= 200;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Atlanti-óceán\nMár megvetted!");
                            boughtOcean = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtOcean", boughtOcean);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 5)
                {
                    /**
                     * Super Zsolti
                     * **/
                    if(!boughtZsolti) {
                        if (Coin.coin >= 250) {
                            if (!muted) paySound.play();
                            Coin.coin -= 250;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Super Zsolti\nMár megvetted!");
                            boughtZsolti = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtZsolti", boughtZsolti);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 6)
                {
                    /**
                     * Double Jump
                     * **/
                    if(!boughtDouble) {
                        if (Coin.coin >= 250) {
                            if (!muted) paySound.play();
                            Coin.coin -= 250;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Double Jump\nMár megvetted!");
                            boughtDouble = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtDouble", boughtDouble);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                else if (itemID == 7)
                {
                    /**
                     * Super Coin
                     * **/
                    if(!boughtCoin) {
                        if (Coin.coin >= 2000) {
                            if (!muted) paySound.play();
                            Coin.coin -= 2000;
                            coinText.setText(""+Coin.coin);
                            termekNevAr.setText("Coin Rain\nMár megvetted!");
                            boughtCoin = true;
                            purchase.remove();
                            preferences.putLong("coin", Coin.coin);
                            preferences.putBoolean("boughtCoin", boughtCoin);
                            preferences.flush();
                        }

                        else if (!muted) noMoney.play();
                    }
                }

                /**
                 * Mindig újrapozicionáljuk a gombokat és a terméknevet, mert ha vásárlunk akkor változhatnak a méretek
                 * **/
                termekNevAr.setPosition(getViewport().getWorldWidth()/2-termekNevAr.getWidth()/2,shopBackgroundPreview.getY() - 150);
                left.setPosition(termekNevAr.getX() - left.getWidth() - 30,termekNevAr.getY());
                right.setPosition(termekNevAr.getX() + termekNevAr.getWidth() + 30,termekNevAr.getY());
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
        addActor(left);
        addActor(right);
        addActor(shopLogo);
        addActor(coin);
        addActor(coinText);
        addActor(back);
        addActor(purchase);
        addActor(termekNevAr);
        addActor(shopBackgroundPreviewBackground);
        addActor(shopBackgroundPreview);
        addActor(instantBoss);
        addActor(superZs);
        addActor(doubleJumpZs);
        addActor(superCoin);
    }

    /**
     * A Stage felépítése után frissítjük egyszer a Labeleket és Actorokat
     * **/
    @Override
    public void afterInit() {
        setTexts();
        setActor();
    }

    /**
     * Actorok beállítása a jelenlegi ID alapján
     * **/
    private void setActor()
    {
        termekNevAr.setPosition(getViewport().getWorldWidth()/2-termekNevAr.getWidth()/2,shopBackgroundPreview.getY() - 150);
        left.setPosition(termekNevAr.getX() - left.getWidth() - 30,termekNevAr.getY());
        right.setPosition(termekNevAr.getX() + termekNevAr.getWidth() + 30,termekNevAr.getY());

        /**
         * INSTANTBOSS
         * */
        if (itemID == 0){
            left.remove();
            superZs.setVisible(false);
            superCoin.setVisible(false);
            doubleJumpZs.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(false);
            shopBackgroundPreview.setVisible(false);
            instantBoss.setVisible(true);
            if(boughtInstantBoss) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * SZIBÉRIA
         * **/
        else if (itemID == 1){
            addActor(right);
            instantBoss.setVisible(false);
            superZs.setVisible(false);
            superCoin.setVisible(false);
            doubleJumpZs.setVisible(false);
            shopBackgroundPreview.setVisible(true);
            shopBackgroundPreviewBackground.setVisible(true);
            shopBackgroundPreview.step(Background.BackgroundType.SZIBERIA);
            addActor(left);
            if(boughtSiberia) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * ZALA HÁTTÉR
         * **/
        else if (itemID == 2){
            addActor(right);
            shopBackgroundPreview.setVisible(true);
            superZs.setVisible(false);
            superCoin.setVisible(false);
            doubleJumpZs.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(true);
            shopBackgroundPreview.step(Background.BackgroundType.ZALA);
            if(boughtZala) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * SZAHARA HÁTTÉR
         * **/
        else if (itemID == 3){
            addActor(right);
            shopBackgroundPreview.setVisible(true);
            superZs.setVisible(false);
            superCoin.setVisible(false);
            doubleJumpZs.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(true);
            shopBackgroundPreview.step(Background.BackgroundType.SZAHARA);
            if(boughtDesert) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * ÓCEÁN HÁTTÉR
         * **/
        else if (itemID == 4){
            addActor(right);
            shopBackgroundPreview.setVisible(true);
            superZs.setVisible(false);
            superCoin.setVisible(false);
            doubleJumpZs.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(true);
            shopBackgroundPreview.step(Background.BackgroundType.OCEAN);
            if(boughtOcean) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * SUPER ZSOLTI
         * **/
        else if (itemID == 5){
            addActor(right);
            superZs.superTime = 3;
            addTimer(new TickTimer(5,true,new TickTimerListener(){
                @Override
                public void onTick(Timer sender, float correction) {
                    super.onTick(sender, correction);
                    superZs.superTime = 3;
                }
            }));
            superZs.setVisible(true);
            doubleJumpZs.setVisible(false);
            superCoin.setVisible(false);
            shopBackgroundPreview.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(false);
            if(boughtZsolti) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * DOUBLE JUMP
         * **/
        else if (itemID == 6){
            addActor(right);
            superZs.setVisible(false);
            doubleJumpZs.setVisible(true);
            superCoin.setVisible(false);
            doubleJumpZs.setPosition(superZs.getX(),superZs.getY());
            shopBackgroundPreview.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(false);
            if(boughtDouble) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }

        /**
         * SUPER COIN
         * **/
        else if (itemID == 7){
            right.remove();
            superZs.setVisible(false);
            doubleJumpZs.setVisible(false);
            superCoin.setVisible(true);
            shopBackgroundPreview.setVisible(false);
            shopBackgroundPreviewBackground.setVisible(false);
            if(boughtCoin) {
                purchase.remove();
            } else {
                addActor(purchase);
            }
        }
    }

    /**
     * Termék nevének és szövegének beállítása a jelenlegi ID-hoz
     * **/
    private void setTexts()
    {
        /**
         * Ha valamiért hibás a mentés és még nincs Coin mező benne, akkor 0-t állítunk be a kijelzőhöz
         * **/
        if(Coin.coin >= 0) coinText.setText(""+Coin.coin);
        else coinText.setText("0");

        /**
         * Ha megvettünk egy terméket, akkor ne írjuk ki az árát, hanem azt, hogy a játékos azt más megvásárolta
         * **/

        if(itemID == 0)
        {
            if (boughtInstantBoss) termekNevAr.setText("Instant Boss\nMár megvetted!");
            else termekNevAr.setText("Instant Boss\nÁr: 100");
        }

        else if(itemID == 1)
        {
            if (boughtSiberia) termekNevAr.setText("Szibéria\nMár megvetted!");
            else termekNevAr.setText("Szibéria\nÁr: 200");
        }

        else if(itemID == 2)
        {
            if (boughtZala) termekNevAr.setText("Zala\nMár megvetted!");
            else termekNevAr.setText("Zala\nÁr: 200");
        }

        else if(itemID == 3)
        {
            if (boughtDesert) termekNevAr.setText("Szahara\nMár megvetted!");
            else termekNevAr.setText("Szahara\nÁr: 200");
        }

        else if(itemID == 4)
        {
            if (boughtOcean) termekNevAr.setText("Atlanti-óceán\nMár megvetted!");
            else termekNevAr.setText("Atlanti-óceán\nÁr: 200");
        }

        else if(itemID == 5)
        {
            if (boughtZsolti) termekNevAr.setText("Super Zsolti\nMár megvetted!");
            else termekNevAr.setText("Super Zsolti\nÁr: 250");
        }

        else if(itemID == 6)
        {
            if (boughtDouble) termekNevAr.setText("Double Jump\nMár megvetted!");
            else termekNevAr.setText("Double Jump\nÁr: 250");
        }

        else if(itemID == 7)
        {
            if (boughtCoin) termekNevAr.setText("Coin Rain\nMár megvetted!");
            else termekNevAr.setText("Coin Rain\nÁr: 2000");
        }
    }

    float alpha = 0;
    boolean setBack = false;

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!setBack) {
            //Áttűnéssel jönnek be az actorok
            if (alpha < 0.95) alpha += 0.05;
            else alpha = 1;
            setAlpha();
        }
        else
        {
            //Áttűnéssel mennek ki az actorok
            if (alpha > 0.05) {
                setAlpha();
                alpha -= 0.05;
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
    }

    /**
     * Actorok átlátszóságának egyidejű beállítása
     * **/
    private void setAlpha(){
        coin.setColor(1, 1, 1, alpha);
        coinText.setColor(1, 1, 1, alpha);
        left.setColor(1, 1, 1, alpha);
        right.setColor(1, 1, 1, alpha);
        shopLogo.setColor(1, 1, 1, alpha);
        instantBoss.setColor(1, 1, 1, alpha);
        purchase.setAlpha(alpha);
        termekNevAr.setAlpha(alpha);
        back.setAlpha(alpha);
        doubleJumpZs.setAlpha(alpha);
        superZs.setAlpha(alpha);
        shopBackgroundPreviewBackground.setAlpha(alpha);
        shopBackgroundPreview.setAlpha(alpha);
        superCoin.setAlpha(alpha);
    }
}
