package com.fphoenixcorneae.eyepetizer.mvi.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.fphoenixcorneae.common.ext.view.gone
import com.fphoenixcorneae.eyepetizer.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * @desc：详情播放器
 * @date：2023/08/16 17:14
 */
class DetailVideoPlayer : StandardGSYVideoPlayer {

    /** 是否第一次初始加载视频。用于隐藏进度条、播放按钮等UI。播放完成/重新加载视频，会重置为true。 */
    private var isFirstInitLoaded = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag)

    override fun getLayoutId() = R.layout.layout_video_detail_video_player

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            when (mCurrentState) {
                GSYVideoView.CURRENT_STATE_PLAYING -> {
                    imageView.setImageResource(R.drawable.ic_pause_white_24dp)
                    imageView.setBackgroundResource(R.drawable.selector_pause_blue_bg)
                }

                GSYVideoView.CURRENT_STATE_ERROR -> {
                    imageView.setImageResource(R.drawable.ic_play_white_24dp)
                    imageView.setBackgroundResource(R.drawable.selector_play_white_bg)
                }

                GSYVideoView.CURRENT_STATE_AUTO_COMPLETE -> {
                    imageView.setImageResource(R.drawable.ic_refresh_white_24dp)
                    imageView.setBackgroundResource(0)
                }

                else -> {
                    imageView.setImageResource(R.drawable.ic_play_white_24dp)
                    imageView.setBackgroundResource(R.drawable.selector_play_white_bg)
                }
            }

        } else {
            super.updateStartImage()
        }
    }

    /**
     * 正常
     */
    override fun changeUiToNormal() {
        super.changeUiToNormal()
        isFirstInitLoaded = true
    }

    /**
     * 准备中
     */
    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        mBottomContainer.gone()
        mStartButton.gone()
    }

    /**
     * 播放中
     */
    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        if (isFirstInitLoaded) {
            mBottomContainer.gone()
            mStartButton.gone()
        }
        isFirstInitLoaded = false
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
    }

    /**
     * 播放结束
     */
    override fun changeUiToCompleteShow() {
        super.changeUiToCompleteShow()
        mBottomContainer.gone()
    }

    /**
     * 错误状态
     */
    override fun changeUiToError() {
        super.changeUiToError()
    }

    fun getBottomContainer() = mBottomContainer
}