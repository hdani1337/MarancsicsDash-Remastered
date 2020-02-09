package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.MyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.StageInterface;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.Screen.MenuScreen;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;

public class IntroStage extends MyStage implements StageInterface {

    public static final String GDX_TEXTURE = "pic/gdx.png";
    public static final String CSANY_TEXTURE = "pic/csany.png";
    public static final String EN_TEXTURE = "pic/en.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture("pic/menuBg.jpg");
        assetList.addTexture(GDX_TEXTURE);
        assetList.addTexture(CSANY_TEXTURE);
        assetList.addFont(RETRO_FONT, RETRO_FONT, 32, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor bg;
    private OneSpriteStaticActor en;
    private OneSpriteStaticActor gdxLogo;
    private OneSpriteStaticActor csanyLogo;
    private MyLabel copyright;

    public IntroStage(MyGame game) {
        super(new ResponseViewport(900), game);
        assignment();
        setSizes();
        setPositions();
        addListeners();
        setZIndexes();
        addActors();
    }

    @Override
    public void assignment() {
        bg = new OneSpriteStaticActor(game, "pic/menuBg.jpg");
        gdxLogo = new OneSpriteStaticActor(game, GDX_TEXTURE);
        csanyLogo = new OneSpriteStaticActor(game, CSANY_TEXTURE);
        en = new OneSpriteStaticActor(game, EN_TEXTURE);

        copyright = new MyLabel(game,"Copyright 2020 Horváth Dániel. Minden jog fenntartva.", new Label.LabelStyle(game.getMyAssetManager().getFont(RETRO_FONT), Color.WHITE)) {
            @Override
            public void init() {
                setFontScale(1);
                setAlignment(0);
            }
        };
    }

    @Override
    public void setSizes() {
        if(getViewport().getWorldWidth() > bg.getWidth()) bg.setWidth(getViewport().getWorldWidth());
        en.setSize(en.getWidth()*0.6f,en.getHeight()*0.6f);
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < bg.getWidth()) bg.setX((getViewport().getWorldWidth()-bg.getWidth())/2);

        gdxLogo.setPosition(getViewport().getWorldWidth()/2-gdxLogo.getWidth()/2,getViewport().getWorldHeight()/2-gdxLogo.getHeight()/2);
        csanyLogo.setPosition(getViewport().getWorldWidth()/2-csanyLogo.getWidth()/2, getViewport().getWorldHeight()/2-csanyLogo.getHeight()/2);
        copyright.setPosition(getViewport().getWorldWidth()/2-copyright.getWidth()/2, 20);
        en.setPosition(getViewport().getWorldWidth()/2-en.getWidth()/2,getViewport().getWorldHeight()/2-en.getHeight()/2);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void setZIndexes() {

    }

    @Override
    public void addActors() {
        addActor(bg);
        addActor(gdxLogo);
        addActor(csanyLogo);
        addActor(copyright);
        addActor(en);

        for (Actor actor : getActors()) actor.setColor(1,1,1,0);
    }

    float alpha = 0;

    private void fadeIn(OneSpriteStaticActor... actor) {
        if (alpha < 0.98) alpha += 0.02;
        else alpha = 1;

        for (OneSpriteStaticActor actor1 : actor)
        {
            actor1.setAlpha(alpha);
        }
    }

    private void fadeOut(OneSpriteStaticActor... actor) {
        if (alpha > 0.02) alpha -= 0.02;
        else {
            alpha = 0;
            pElapsed = 0;
            index++;
        }

        for (OneSpriteStaticActor actor1 : actor)
        {
            actor1.setAlpha(alpha);
        }
    }

    private float pElapsed;
    private byte index = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        bg.setAlpha((1/8.0f) * elapsedTime);

        switch (index) {
            case 0: {
                pElapsed += delta;
                if (pElapsed < 1) fadeIn(gdxLogo);
                else if (pElapsed > 2) fadeOut(gdxLogo);
                break;
            }

            case 1: {
                pElapsed += delta;
                if (pElapsed < 1) {
                    fadeIn(csanyLogo);
                }
                else if (pElapsed > 2) {
                    fadeOut(csanyLogo);
                }
                break;
            }

            case 2: {
                pElapsed += delta;
                if (pElapsed < 1) {
                    fadeIn(en);
                    copyright.setColor(1,1,1,alpha);
                }
                else if (pElapsed > 1.5) copyright.setColor(1,1,1, copyright.getColor().a - 0.02f);
                break;
            }
        }

        if(elapsedTime > 7.9) {
            MarancsicsDash.needsLoading = true;
            game.setScreenWithPreloadAssets(MenuScreen.class, true, new LoadingStage(game));
        }
    }
}
