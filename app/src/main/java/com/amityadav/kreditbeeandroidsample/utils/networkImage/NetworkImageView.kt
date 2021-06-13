package com.amityadav.kreditbeeandroidsample.utils.networkImage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.postprocessors.BlurPostProcessor
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

@SuppressWarnings("TooManyFunctions")
class NetworkImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : SimpleDraweeView(context, attrs, defStyleAttr) {
    // region Hide Public Methods
    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageRequest(request: ImageRequest?) {
        super.setImageRequest(request)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageURI(uriString: String?) {
        super.setImageURI(uriString)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageURI(uri: Uri?, callerContext: Any?) {
        super.setImageURI(uri, callerContext)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setController(draweeController: DraweeController?) {
        super.setController(draweeController)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setImageURI(uriString: String?, callerContext: Any?) {
        super.setImageURI(uriString, callerContext)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setActualImageResource(resourceId: Int) {
        super.setActualImageResource(resourceId)
    }

    /**
     * Hide method for public access
     *
     * @deprecated Use {@link #setController(DraweeController)} instead.
     */
    @Suppress("DEPRECATION")
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "use .load method instead")
    override fun setActualImageResource(resourceId: Int, callerContext: Any?) {
        super.setActualImageResource(resourceId, callerContext)
    }

    //endregion

    fun setBackgroundTintRes(@ColorRes tintResId: Int) {
        backgroundTintList = ContextCompat.getColorStateList(context, tintResId)
    }

    override fun setBackgroundResource(resid: Int) {
        background = ContextCompat.getDrawable(context, resid)
    }

    override fun setBackground(background: Drawable?) {
        hierarchy?.setBackgroundImage(background)
    }

    fun setCornerRadius(radius: Float) {
        hierarchy?.roundingParams = RoundingParams.fromCornersRadius(radius)
    }

    fun renderAsCircle() {
        hierarchy?.roundingParams = RoundingParams.asCircle()
    }

    fun setPlaceholderResource(@DrawableRes resourceId: Int, scaleType: ScaleType) {
        hierarchy?.setPlaceholderImage(resourceId, scaleType.toFrescoScaleType())
    }

    @JvmOverloads
    fun load(
        actualImage: String?,
        thumbImage: String? = null,
        controllerListener: ImageRequestListener? = null,
        imageOverlay: ImageOverlay? = null,
    ) = actualImage?.let {
        loadImage(Uri.parse(actualImage), thumbImage?.let { Uri.parse(thumbImage) }, controllerListener, imageOverlay)
    }

    @JvmOverloads
    fun load(
        actualImage: Uri,
        thumbImage: Uri? = null,
        controllerListener: ImageRequestListener? = null,
        imageOverlay: ImageOverlay? = null,
    ) {
        loadImage(actualImage, thumbImage, controllerListener, imageOverlay)
    }

    override fun setScaleType(scaleType: ScaleType) {
        hierarchy?.actualImageScaleType = scaleType.toFrescoScaleType()
    }

    @JvmOverloads
    fun loadBlurredImage(
        actualImage: String?,
        thumbImage: Uri? = null,
        controllerListener: ImageRequestListener? = null,
        @Px @IntRange(from = 0, to = 25) blurRadius: Int = 20,
    ) = actualImage?.let {
        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(actualImage))
            .setPostprocessor(BlurPostProcessor(blurRadius, context))
            .build()

        val lowResImageRequest = thumbImage?.let { ImageRequest.fromUri(it) }
        loadImage(imageRequest, lowResImageRequest, controllerListener)
    }

    private fun loadImage(
        actualImage: Uri,
        thumbImage: Uri? = null,
        controllerListener: ImageRequestListener?,
        imageOverlay: ImageOverlay? = null,
    ) {
        val lowResImageRequest = thumbImage?.let { ImageRequest(it, imageOverlay) }
        val imageRequest = ImageRequest(actualImage, imageOverlay)

        loadImage(imageRequest, lowResImageRequest, controllerListener)
    }

    private fun loadImage(
        imageRequest: ImageRequest?,
        lowResImageRequest: ImageRequest?,
        controllerListener: ImageRequestListener?,
    ) {
        val controller: DraweeController = Fresco.newDraweeControllerBuilder().apply {
            this.lowResImageRequest = lowResImageRequest
            controllerListener?.let {
                setControllerListener(controllerListener)
            }
            this.imageRequest = imageRequest
            oldController = this@NetworkImageView.controller
        }.build()
        super.setController(controller)
    }

    private fun ScaleType.toFrescoScaleType() = when (this) {
        ScaleType.FIT_XY -> ScalingUtils.ScaleType.FIT_XY
        ScaleType.FIT_START -> ScalingUtils.ScaleType.FIT_START
        ScaleType.FIT_CENTER -> ScalingUtils.ScaleType.FIT_CENTER
        ScaleType.FIT_END -> ScalingUtils.ScaleType.FIT_END
        ScaleType.CENTER -> ScalingUtils.ScaleType.CENTER
        ScaleType.CENTER_CROP -> ScalingUtils.ScaleType.CENTER_CROP
        ScaleType.CENTER_INSIDE -> ScalingUtils.ScaleType.CENTER_INSIDE
        else -> ScalingUtils.ScaleType.CENTER
    }
}

@Suppress("FunctionName") //Factory function
private fun ImageRequest(uri: Uri, imageOverlay: ImageOverlay?) =
    ImageRequestBuilder.newBuilderWithSource(uri)
        .setPostprocessor(imageOverlay?.postProcessor)
        .build()
