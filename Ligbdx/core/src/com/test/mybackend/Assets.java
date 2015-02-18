package com.test.mybackend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;



public class Assets implements Disposable {
	public final static String TAG = Assets.class.getName();
	public static Assets instance = new Assets();
	private AssetManager assetManager;
	public Textures textures;
	public Buttons buttons;
	
	private Assets(){

	}
	
	public void init(){
		assetManager = new AssetManager();
		assetManager.load("images/images.pack", TextureAtlas.class);
		assetManager.update();
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, " # of assets loaded: " + assetManager.getAssetNames().size);
		for(String a : assetManager.getAssetNames()){
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas atlas = assetManager.get("images/images.pack");
		for (Texture t: atlas.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		textures = new Textures(atlas);
		buttons = new Buttons(atlas);
	}
	@Override
	public void dispose() {
		textures.dispose();
		buttons.dispose();
	}
	
	


public class Textures{
	public final AtlasRegion redRing, greenRing, yellowRing, octogon;
	public Textures(TextureAtlas atlas){
		redRing = new AtlasRegion(atlas.findRegion("RedRing"));
		greenRing = new AtlasRegion(atlas.findRegion("GreenRing"));
		yellowRing = new AtlasRegion(atlas.findRegion("YellowRing"));
		octogon = new AtlasRegion(atlas.findRegion("octogon"));
		
		
	}

	public void dispose(){
	}
}

public class Buttons{
	public final AtlasRegion one, two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace, back;
	public final BitmapFont font;
	
	public Buttons(TextureAtlas atlas){
		font = new BitmapFont(Gdx.files.internal("Font/white.fnt"));
		
		one = new AtlasRegion(atlas.findRegion("card1"));
		two = new AtlasRegion(atlas.findRegion("card2"));
		three = new AtlasRegion(atlas.findRegion("card3"));
		four = new AtlasRegion(atlas.findRegion("card4"));
		five = new AtlasRegion(atlas.findRegion("card5"));
		six = new AtlasRegion(atlas.findRegion("card6"));
		seven = new AtlasRegion(atlas.findRegion("card7"));
		eight = new AtlasRegion(atlas.findRegion("card8"));
		nine = new AtlasRegion(atlas.findRegion("card9"));
		ten = new AtlasRegion(atlas.findRegion("card10"));
		jack = new AtlasRegion(atlas.findRegion("jack"));
		queen = new AtlasRegion(atlas.findRegion("queen"));
		king = new AtlasRegion(atlas.findRegion("king"));
		ace = new AtlasRegion(atlas.findRegion("ace"));
		back = new AtlasRegion(atlas.findRegion("back"));

	}

	public void dispose(){
		
	}
}


	
}

