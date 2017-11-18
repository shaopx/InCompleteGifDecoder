package com.spx.incompletegifdecoder.sample;


import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.resource.gif.*;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by shaopengxiang on 2017/11/17.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    private static final String TAG = "MyAppGlideModule";

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);

        List<ImageHeaderParser> imageHeaderParsers = registry.getImageHeaderParsers();

        com.spx.gifdecoder.ByteBufferGifDecoder byteBufferGifDecoder =
                new com.spx.gifdecoder.ByteBufferGifDecoder(context, imageHeaderParsers, glide.getBitmapPool(), glide.getArrayPool());
        registry.prepend(Registry.BUCKET_GIF, ByteBuffer.class, GifDrawable.class, byteBufferGifDecoder);

        registry.prepend(Registry.BUCKET_GIF,
                InputStream.class,
                GifDrawable.class,  new StreamGifDecoder(imageHeaderParsers, byteBufferGifDecoder, glide.getArrayPool()));

    }
}
