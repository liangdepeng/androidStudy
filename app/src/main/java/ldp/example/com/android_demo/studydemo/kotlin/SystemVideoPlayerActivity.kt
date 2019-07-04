package ldp.example.com.android_demo.studydemo.kotlin

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_video_player.*
import ldp.example.com.android_demo.R

class SystemVideoPlayerActivity : AppCompatActivity(), View.OnClickListener {

    private val PROGRESS: Int = 1
    private val AUTO_PLAY_NEXT: Int = 2
    var countDownTime: Int = 5
    var currentPosition: Int = 0
    var currentVoice: Int = 0
    var voiceMax: Int = 0
    var url: String? = null
    var heightUrl: String? = null
    var list: ArrayList<TestBean.TrailersBean>? = null
    lateinit var audio_service: AudioManager
    var mediaController: MediaController? = null
    var videoWidth: Int = 0
    var videoHeight: Int = 0
    private val DEFAULT_SIZE = 0
    private val FULL_SCREEN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        initView()
        getData()
        setOnClickListener()
    }

    private fun initView() {
        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.definitions, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.prompt = "清晰度"
        spinner.onItemSelectedListener = SpinnerSelectedListener()
        audio_service = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        currentVoice = audio_service.getStreamVolume(AudioManager.STREAM_MUSIC)
        voiceMax = audio_service.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        voice_seekBar.progress = currentVoice
        voice_seekBar.max = voiceMax

        voice_seekBar.setOnSeekBarChangeListener(VoiceSeekBarChangeListener())
        //播放进度
        seekBar.setOnSeekBarChangeListener(SeekBarChangedListener())
        //准备播放
        videoView.setOnPreparedListener(PreparedListener())
        //播放完成
        videoView.setOnCompletionListener(PlayCompletionListener())
        //播放出错
        videoView.setOnErrorListener(PlayErrorListener())
    }

    private fun setOnClickListener() {
        pre_btn.setOnClickListener(this)
        start_pause_btn.setOnClickListener(this)
        next_btn.setOnClickListener(this)
        systemMediaController.setOnClickListener(this)
    }

    private fun getData() {
        currentPosition = intent.getIntExtra("video_position", 0)
//        url = intent.getStringExtra("video_url")
//        heightUrl = intent.getStringExtra("video_heightUrl")
        list = intent?.getSerializableExtra("video_list") as ArrayList<TestBean.TrailersBean>
        url = list!![currentPosition].url
        movieDataChangedPlay(currentPosition)
    }

    private fun playVideo() {
        loading_layout.visibility = View.VISIBLE
        videoView.setVideoPath(url)
//        videoView.start()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.pre_btn -> {
                currentPosition--
                if (currentPosition < 0) {
                    showToast(getString(R.string.atFirst))
                    currentPosition = 0
                } else {
                    movieDataChangedPlay(currentPosition)
                }
            }
            R.id.start_pause_btn -> {
                if (videoView.isPlaying) {
                    videoView.pause()
                    start_pause_btn.text = getString(R.string.playing)
                } else {
                    videoView.start()
                    start_pause_btn.text = getString(R.string.pause)
                }
            }
            R.id.next_btn -> {
                currentPosition++
                if (currentPosition > list!!.size - 1) {
                    showToast(getString(R.string.atLast))
                    currentPosition = list!!.size - 1
                } else {
                    movieDataChangedPlay(currentPosition)
                }
            }
            R.id.systemMediaController -> {
                if (mediaController == null) {
                    mediaController = MediaController(this, true)
                    systemMediaController.text = getString(R.string.closeController)
                } else {
                    mediaController = null
                    systemMediaController.text = getString(R.string.openController)
                }
                videoView.setMediaController(mediaController)
            }
        }
    }

    private fun movieDataChangedPlay(currentPosition: Int) {
        url = if (spinner.selectedItemPosition == 0)
            list!![currentPosition].url
        else
            list!![currentPosition].hightUrl

        playVideo()
        movie_name.text = list!![currentPosition].movieName
        movie_summary.text = list!![currentPosition].summary
        movie_type.text = list!![currentPosition].type.toString()

    }

    inner class SpinnerSelectedListener : AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (position == 0) {
                url = list!![currentPosition].url
            } else if (position == 1) {
                url = list!![currentPosition].hightUrl
            }
            playVideo()
        }

    }

    inner class SeekBarChangedListener : SeekBar.OnSeekBarChangeListener {

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                videoView.seekTo(progress)
            }
        }

    }

    inner class PreparedListener : MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer?) {
            videoView.start()
            seekBar.max = videoView.duration
            handler.sendEmptyMessage(PROGRESS)
            loading_layout.visibility = View.GONE

            videoWidth = mp?.videoWidth ?: videoView.width
            videoHeight = mp?.videoHeight ?: videoView.height

            fixSetVideoSize(DEFAULT_SIZE)
        }
    }

    inner class PlayErrorListener : MediaPlayer.OnErrorListener {
        override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
            showToast(getString(R.string.playFailed))
            return false
        }
    }

    inner class PlayCompletionListener : MediaPlayer.OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer?) {
            showToast("finished!")
            handler.sendEmptyMessage(AUTO_PLAY_NEXT)
        }

    }

    inner class VoiceSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                upDateVoice(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }

    }

    private fun upDateVoice(progress: Int) {
        audio_service.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
        voice_seekBar.progress = progress
        currentVoice = progress
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                PROGRESS -> {
                    seekBar.progress = videoView.currentPosition
                    msg.target.removeMessages(PROGRESS)
                    msg.target.sendEmptyMessageDelayed(PROGRESS, 100)
                }
                AUTO_PLAY_NEXT -> {
                    next_loading_layout.visibility = View.VISIBLE
                    time.text = countDownTime.toString()
                    countDownTime--
                    msg.target.removeMessages(AUTO_PLAY_NEXT)
                    if (countDownTime >= 0) {
                        msg.target.sendEmptyMessageDelayed(AUTO_PLAY_NEXT, 1_000)
                    } else {
                        next_loading_layout.visibility = View.GONE
                        countDownTime = 5
                        currentPosition++
                        if (currentPosition > list!!.size - 1) {
                            showToast(getString(R.string.atLast))
                        } else {
                            movieDataChangedPlay(currentPosition)
                        }
                    }
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                currentVoice++
                upDateVoice(currentVoice)
                return true
            }
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                currentVoice--
                upDateVoice(currentVoice)
                return true
            }
            KeyEvent.KEYCODE_POWER -> {
                showToast("KEYCODE_POWER_ONCLICK")
            }

        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setVideoSize(width: Int, height: Int) {
        val params = videoView.layoutParams
        params.width = width
        params.height = height
        videoView.layoutParams = params
    }

    fun fixSetVideoSize(type: Int) {
        when (type) {
            DEFAULT_SIZE -> {
                var videoViewWidth = videoView.width
                var videoViewHeight = videoView.height
                if (videoWidth * videoViewHeight < videoViewWidth * videoHeight) {
                    videoViewWidth = videoWidth * videoViewHeight / videoHeight
                } else if (videoWidth * videoViewHeight > videoViewWidth * videoHeight) {
                    videoViewHeight = videoHeight * videoViewWidth / videoWidth
                }
                setVideoSize(videoViewWidth, videoViewHeight)
            }
            FULL_SCREEN -> {

            }
        }
    }

}
