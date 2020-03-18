package hu.hdani1337.marancsicsdash.Stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import hu.csanyzeg.master.MyBaseClasses.Assets.AssetList;
import hu.csanyzeg.master.MyBaseClasses.Game.MyGame;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.OneSpriteStaticActor;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.PrettyStage;
import hu.csanyzeg.master.MyBaseClasses.Scene2D.ResponseViewport;
import hu.csanyzeg.master.MyBaseClasses.UI.MyLabel;
import hu.hdani1337.marancsicsdash.MarancsicsDash;
import hu.hdani1337.marancsicsdash.Screen.MenuScreen;

import static hu.hdani1337.marancsicsdash.HudActors.TextBox.RETRO_FONT;

public class IntroStage extends PrettyStage {

    public static final String GDX_TEXTURE = "pic/gdx.png";
    public static final String CSANY_TEXTURE = "pic/csany.png";

    public static AssetList assetList = new AssetList();
    static {
        assetList.addTexture("pic/menuBg.jpg");
        assetList.addTexture(GDX_TEXTURE);
        assetList.addTexture(CSANY_TEXTURE);
        assetList.addFont(RETRO_FONT, RETRO_FONT, 32, Color.WHITE, AssetList.CHARS);
    }

    private OneSpriteStaticActor bg;
    private OneSpriteStaticActor gdxLogo;
    private OneSpriteStaticActor csanyLogo;
    private MyLabel copyright;

    public IntroStage(MyGame game) {
        super(new ResponseViewport(900), game);
    }

    @Override
    public void assignment() {
        bg = new OneSpriteStaticActor(game, "pic/menuBg.jpg");
        gdxLogo = new OneSpriteStaticActor(game, GDX_TEXTURE);
        csanyLogo = new OneSpriteStaticActor(game, CSANY_TEXTURE);

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
    }

    @Override
    public void setPositions() {
        if(getViewport().getWorldWidth() < bg.getWidth()) bg.setX((getViewport().getWorldWidth()-bg.getWidth())/2);

        gdxLogo.setPosition(getViewport().getWorldWidth()/2-gdxLogo.getWidth()/2,getViewport().getWorldHeight()/2-gdxLogo.getHeight()/2);
        csanyLogo.setPosition(getViewport().getWorldWidth()/2-csanyLogo.getWidth()/2, getViewport().getWorldHeight()/2-csanyLogo.getHeight()/2);
        copyright.setPosition(getViewport().getWorldWidth()/2-copyright.getWidth()/2, 20);
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

        for (Actor actor : getActors()) actor.setColor(1,1,1,0);
    }

    float alpha = 0;

    private void fadeIn(OneSpriteStaticActor... actor) {
        if (alpha < 0.95) alpha += 0.05;
        else alpha = 1;

        for (OneSpriteStaticActor actor1 : actor)
        {
            actor1.setAlpha(alpha);
        }
    }

    private void fadeOut(OneSpriteStaticActor... actor) {
        if (alpha > 0.05) alpha -= 0.05;
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
        if((1/5.0f) * elapsedTime < 1) bg.setAlpha((1/5.0f) * elapsedTime);
        else bg.setAlpha(1);

        switch (index) {
            case 0: {
                pElapsed += delta;
                if (pElapsed < 0.75) fadeIn(gdxLogo);
                else if (pElapsed > 1.5) fadeOut(gdxLogo);
                break;
            }

            case 1: {
                pElapsed += delta;
                if (pElapsed < 0.75) {
                    fadeIn(csanyLogo);
                } else if (pElapsed > 1){
                    copyright.setColor(1,1,1,elapsedTime-2.8f);
                    if (pElapsed > 2.5) {
                        fadeOut(csanyLogo);
                        copyright.setColor(1,1,1, alpha);
                    }
                }

                break;
            }
        }

        if(elapsedTime > 5) {
            MarancsicsDash.needsLoading = true;
            game.setScreenWithPreloadAssets(MenuScreen.class, true, new LoadingStage(game));
        }
    }
}
