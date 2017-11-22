# InCompleteGifDecoder
一个可以显示不完整gif图片的基于glide的解码器

对于不完整的GIF图, 比如非正常截断, 下载中断等原因造成gif图数据不完整,  使用glide是无法显示动画效果的.  

这个demo通过修改glide中自带的gif解码代码, 并替换为默认解码器. 

# 如何使用
增加一个自定义的AppGlideModule, 如果之前已经有了自定义的AppGlideModule, 那只需要在registerComponents()方法中添加具体的逻辑
<pre><code>
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
</code></pre>

# 示例GIF
http://pic.wenwen.soso.com/pqpic/wenwenpic/0/20171116143737-1895154083_gif_398_305_3740344/0
