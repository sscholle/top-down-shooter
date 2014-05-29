
package com.badlogicgames.gradletest;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.RatioResolutionStrategy;
import com.badlogic.gradletest.HelloApp;

public class MainActivity extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useAccelerometer = true;
        config.useCompass = true;
        config.useGL20 = true;
        //config.resolutionStrategy = new RatioResolutionStrategy(1.3f);
		initialize(new HelloApp(), config);
	}
}
