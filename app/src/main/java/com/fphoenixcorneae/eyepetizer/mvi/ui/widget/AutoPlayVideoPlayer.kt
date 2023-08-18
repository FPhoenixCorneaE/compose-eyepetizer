package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ImageView
import com.fphoenixcorneae.common.ext.view.gone
import com.fphoenixcorneae.eyepetizer.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * @desc：列表播放器
 * @date：2023/08/16 15:19
 */
class AutoPlayVideoPlayer : StandardGSYVideoPlayer {

    private lateinit var start: ImageView

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, fullFlag: Boolean) : super(context, fullFlag)

    override fun getLayoutId(): Int {
        return R.layout.layout_auto_play_video_player
    }

    override fun init(context: Context?) {
        super.init(context)
        start = findViewById(R.id.start)
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        // 不给触摸快进，如果需要，屏蔽下方代码即可
        mChangePosition = false

        // 不给触摸音量，如果需要，屏蔽下方代码即可
        mChangeVolume = false

        // 不给触摸亮度，如果需要，屏蔽下方代码即可
        mBrightness = false
    }

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                GSYVideoView.CURRENT_STATE_ERROR -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
                else -> imageView.setImageResource(R.drawable.ic_play_white_24dp)
            }
        } else {
            super.updateStartImage()
        }
    }

    override fun touchDoubleUp(e: MotionEvent?) {
        super.touchDoubleUp(e)
        // 不需要双击暂停
    }

    /**
     * 正常
     */
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        mBottomContainer.gone()
    }

    /**
     * 准备中
     */
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        mBottomContainer.gone()
    }

    /**
     * 播放中
     */
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        mBottomContainer.gone()
        start?.gone()
    }

    /**
     * 开始缓冲
     */
    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
    }

    /**
     * 暂停
     */
    override fun changeUiToPauseShow() {
        super.changeUiToPauseShow()
        mBottomContainer.gone()
    }

    /**
     * 自动播放结束
     */
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
    }

    /**
     * 错误状态
     */
    override fun changeUiToError() {
        super.changeUiToError()
    }
}