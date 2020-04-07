package tm.fantom.tmdb;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;

import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;
import tm.fantom.tmdb.di.component.AppComponent;
import tm.fantom.tmdb.di.component.DaggerAppComponent;
import tm.fantom.tmdb.di.module.ApiModule;

public final class TmApp extends Application {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        initDagger();
        Fresco.initialize(this, ImagePipelineConfig.newBuilder(this)
                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                .setDiskCacheEnabled(true)
                .build());
        RxJavaPlugins.setErrorHandler(e -> Timber.e(e, "unhandled exception: %s", e.getMessage()));
    }
    public static TmApp get(Context context) {
        return (TmApp) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void initDagger() {
        appComponent = DaggerAppComponent
                .builder()
                .apiModule(new ApiModule(this))
                .build();
//        appComponent.inject(this);
    }

}
