package kz.app.triangleapp

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class PagerFragment : Fragment(), GLSurfaceView.Renderer, SeekBar.OnSeekBarChangeListener {

    private lateinit var glSurfaceView: GLSurfaceView
    private lateinit var seekbar: SeekBar
    private var width = 0.0f
    private var height = 0.0f
    private var angle = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        init()
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glSurfaceView = view.findViewById(R.id.gl_surface_view)
        seekbar = view.findViewById(R.id.seek_bar)
        seekbar.setOnSeekBarChangeListener(this)

        glSurfaceView.setEGLContextClientVersion(2)
        glSurfaceView.setRenderer(this)
        glSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

    }

    private external fun drawTriangle(width: Float, height: Float, angle: Float)
    private external fun init()

    companion object {
        init {
            System.loadLibrary("gl2jni")
        }
    }


    override fun onDrawFrame(p0: GL10?) {
        p0?.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        drawTriangle(width, height, angle)
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        drawTriangle(p1.toFloat(), p2.toFloat(), 0.0f)
        width = p1.toFloat()
        height = p2.toFloat()
    }

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {

    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        angle = p1.toFloat()
        glSurfaceView.requestRender()

    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
    }

}