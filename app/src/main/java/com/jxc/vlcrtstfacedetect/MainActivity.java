package com.jxc.vlcrtstfacedetect;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.objdetect.CascadeClassifier;
import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private MediaPlayer mMediaPlayer;
    private LibVLC mVlc;

    private LinearLayout ll_faces;

    private SurfaceView surfaceView ;

    private TextureView textureView;

    private FaceRtspUtil faceRtspUtil;

    private int PIC_WIDTH = 1000;
    private int PIC_HEIGHT = 1000;

    private Timer mTimer;

    private SVDraw svDraw;

    private Button bt_open;

    private DrawFaceView drawFaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ll_faces = (LinearLayout) findViewById(R.id.ll_faces);

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, mLoaderCallback);
        surfaceView = (SurfaceView) findViewById(R.id.mySurfaceView);

        drawFaceView = (DrawFaceView) findViewById(R.id.mDraw);

//        svDraw = (SVDraw) findViewById(R.id.mDraw);
//        svDraw.setVisibility(View.VISIBLE);
        String pathUri = "rtsp://192.168.1.10/user=admin&password=&channel=1&stream=0,sdp";
        createPlayer(pathUri,PIC_WIDTH,PIC_HEIGHT);
        /*textureView = (TextureView) findViewById(R.id.surface_web);
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                String pathUri = "rtsp://192.168.1.10/user=admin&password=&channel=1&stream=0,sdp";
                createPlayer(pathUri,PIC_WIDTH,PIC_HEIGHT);
                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Bitmap bitmap = snapShot();
                        final List<Bitmap> faces = faceRtspUtil.detectFrame(bitmap);
                        //添加到界面上
                        if (faces != null){
                            Log.d("detectFrame:",faces.size() + "");
//                            addPicToLayout(faces);
                        }

                    }
                }, 1000,1000);


            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });*/




//        fragment = new VideoPlayerFragment();
//        fragment.setRtspCallBack(new VideoPlayerFragment.RtspCallBack() {
//            @Override
//            public void pushData(final List<Bitmap> faces) {
//                //清除所有的子View
//                ll_faces.removeAllViews();
//                for (int i = 0; i < faces.size(); i++) {
//                    ImageView image = new ImageView(MainActivity.this);
//                    image.setImageBitmap(faces.get(i));
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
//                    ll_faces.addView(image, params);
//                }
//            }
//        });




//        try {
//            EventHandler em = EventHandler.getInstance();
//            em.addHandler(handler);
//            LibVLC mLibVLC = Util.getLibVlcInstance();
//            if (mLibVLC != null) {
//                mLibVLC.setSubtitlesEncoding("");
//                mLibVLC.setTimeStretching(false);
//                mLibVLC.setFrameSkip(true);
//                mLibVLC.setChroma("RV32");
//                mLibVLC.setVerboseMode(true);
//                mLibVLC.setAout(-1);
//                mLibVLC.setDeblocking(4);
//                mLibVLC.setNetworkCaching(1500);
//                //测试地址
////                String pathUri = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
//                String pathUri = "rtsp://192.168.1.10/user=admin&password=&channel=1&stream=0,sdp";
//                mLibVLC.playMyMRL(pathUri);
//            }
//        } catch (LibVlcException e) {
//            e.printStackTrace();
//        }
    }

    /*Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d(TAG, "Event = " + msg.getData().getInt("event"));
            switch (msg.getData().getInt("event")) {
                case EventHandler.MediaPlayerPlaying:
                case EventHandler.MediaPlayerPaused:
                    break;
                case EventHandler.MediaPlayerStopped:
                    break;
                case EventHandler.MediaPlayerEndReached:
                    break;
                case EventHandler.MediaPlayerVout:
                    if (msg.getData().getInt("data") > 0) {
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.add(R.id.frame_layout, fragment);
                        transaction.commit();
                    }
                    break;
                case EventHandler.MediaPlayerPositionChanged:
                    break;
                case EventHandler.MediaPlayerEncounteredError:
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("提示信息")
                            .setMessage("无法连接到网络摄像头，请确保手机已经连接到摄像头所在的wifi热点")
                            .setNegativeButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    break;
                default:
                    Log.d(TAG, "Event not handled ");
                    break;
            }
        }
    };*/

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void addPicToLayout(List<Bitmap> faces){
        for (int i = 0; i < faces.size(); i++) {
            Log.d("addPicToLayout","检测到人脸");
            ImageView image = new ImageView(MainActivity.this);
            image.setImageBitmap(faces.get(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            ll_faces.addView(image, params);
        }
    }



    public void createPlayer(String url, int width, int height) {
        ArrayList<String> options = new ArrayList<>();
        options.add("--aout=opensles");
        options.add("--audio-time-stretch");
        options.add("-vvv");
        mVlc = new LibVLC(this, options);

        mMediaPlayer = new MediaPlayer(mVlc);
        IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.setVideoView(surfaceView);
        vout.attachViews();

        vout.setWindowSize(width, height);

        Media m = new Media(mVlc, Uri.parse(url));
        int cache = 1000;
        m.addOption(":network-caching=" + cache);
        m.addOption(":file-caching=" + cache);
        m.addOption(":live-cacheing=" + cache);
        m.addOption(":sout-mux-caching=" + cache);
        m.addOption(":codec=mediacodec,iomx,all");
        mMediaPlayer.setMedia(m);
        mMediaPlayer.play();
    }

    public void releasePlayer() {
//        mMediaPlayer.setVideoCallback(null, null);
        mMediaPlayer.stop();
        IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.detachViews();
        mVlc.release();
        mVlc = null;
    }

    /**
     * 截图
     */
    private Bitmap snapShot() {
        Bitmap bitmap = textureView.getBitmap(PIC_WIDTH,PIC_HEIGHT);
//        try {
//            String name = mPicCachePath + System.currentTimeMillis() + ".jpg";
//            //调用LibVlc的截屏功能，传入一个路径，及图片的宽高
//            if (mVlc.takeSnapShot(name, PIC_WIDTH, PIC_HEIGHT)) {
//                Log.i(TAG, "snapShot: 保存成功--" + System.currentTimeMillis());
//                return name;
//            }
//            Log.i(TAG, "snapShot: 保存失败");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return bitmap;
    }

    private CascadeClassifier initializeOpenCVDependencies() {

        CascadeClassifier classifier = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            File cascadeDir = this.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
            FileOutputStream fos = new FileOutputStream(mCascadeFile);

            byte[] bytes = new byte[4096];
            int len;
            while ((len = is.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
            is.close();
            fos.close();
            classifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error loading cascade", e);
        }
        return classifier;
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    CascadeClassifier classifier = initializeOpenCVDependencies();
                    faceRtspUtil = new FaceRtspUtil(classifier,PIC_WIDTH,PIC_HEIGHT);
                    faceRtspUtil.setListener(new FaceRtspUtil.CallListener() {
                        @Override
                        public void updata(int x, int y, int width, int height) {
                            drawFaceView.setXY(x,y,x+width,y+height);
                        }
                    });
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

}
