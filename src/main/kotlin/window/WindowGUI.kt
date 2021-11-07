package window

import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import javax.swing.JFrame
import kotlin.math.abs

/**Creates a GUI window with resolution width by height, name of window: name*/
class WindowGUI(width: Int, height: Int, name: String) : IWindow, Canvas() {
    private var _width : Int
    private var _height : Int

    private val frame : JFrame
    private val bufferedImage : BufferedImage
    private val pixels : IntArray

    private val bs : BufferStrategy
    private val g : Graphics

    init {
        _width = width
        _height = height

        bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        pixels = (bufferedImage.raster.dataBuffer as DataBufferInt).data

        frame = JFrame(name)
        preferredSize = Dimension(width, height)
        minimumSize = Dimension(width, height)
        maximumSize = Dimension(width, height)

        frame.add(this)
        frame.isResizable = false
        frame.pack()
        frame.isVisible = true

        createBufferStrategy(3)
        bs = bufferStrategy
        g = bs.drawGraphics
    }

    /**Set screen pixel width*/
    override fun setWidth(w: Int) {
        _width = w
        updateCanvasDim()
    }

    /**Get screen pixel width*/
    override fun getWidth(): Int {
        return _width
    }

    /**Set screen pixel height*/
    override fun setHeight(h: Int) {
        _height = h
        updateCanvasDim()
    }

    /**Get screen pixel height*/
    override fun getHeight(): Int {
        return _height
    }

    /**
     * Draw line from (x0,y0) to (x1, y1)
     * Uses Brensenham algorithm.
     * */
    @Suppress("DuplicatedCode")
    override fun drawLine(x0: Int, y0: Int, x1: Int, y1: Int, c: Int) {
        fun plot(x: Int, y: Int){
            val index = y*_width + x
            if (index < _width*_height) pixels[index] = c
        }
        fun plotLineLow(x0: Int, y0: Int, x1: Int, y1: Int){
            val dx = x1 - x0
            var dy = y1 - y0
            var yi = 1
            if (dy < 0) {
                yi = -1
                dy = -dy
            }
            var d = 2*dy - dx
            var y = y0

            for (x in x0..x1){
                plot(x, y)
                if (d > 0) {
                    y += yi
                    d += 2 * (dy - dx)
                } else {
                    d += 2 * dy
                }
            }
        }
        fun plotLineHigh(x0: Int, y0: Int, x1: Int, y1: Int) {
            var dx = x1 - x0
            val dy = y1 - y0
            var xi = 1
            if (dx < 0) {
                xi = -1
                dx = -dx
            }
            var d = 2*dx - dy
            var x = x0

            for (y in y0..y1) {
                plot(x, y)
                if (d>0) {
                    x += xi
                    d += 2 * (dx - dy)
                } else {
                    d += 2*dx
                }
            }
        }

        if (abs(y1 - y0) < abs(x1 - x0)) {
            if (x0 > x1) plotLineLow(x1 ,y1, x0, y0)
            else plotLineLow(x0, y0, x1, y1)
        } else {
            if (y0 > y1) plotLineHigh(x1, y1, x0, y0)
            else plotLineHigh(x0, y0, x1, y1)
        }
    }

    /**
     * Draw circle at (x,y) with radius r
     * Uses Brensenham algorithm.
     * */
    override fun drawCircle(x: Int, y: Int, r: Int, c: Int) {
        fun plot(xOff: Int, yOff: Int) {
            val index = (y+yOff)*_width + (x + xOff)
            if(index < _width*_height) pixels[index] = c
        }

        plotCircle(x, y, r, c, ::plot)
    }

    /**Draw rectangle at (x0,y0) with width w and height h*/
    override fun drawRect(x0: Int, y0: Int, w: Int, h: Int, c: Int) {
        drawLine(x0, y0, x0+w-1, y0, c)
        drawLine(x0, y0+h-1, x0+w-1, y0+h-1, c)
        drawLine(x0, y0, x0, y0+h-1, c)
        drawLine(x0+w-1, y0, x0+w-1, y0+h-1, c)
    }

    /**Draw triangle with given corners*/
    override fun drawTri(x0: Int, y0: Int, x1: Int, y1: Int, x2: Int, y2: Int, c: Int) {
        drawLine(x0, y0, x1, y1, c)
        drawLine(x1, y1, x2, y2, c)
        drawLine(x2, y2, x0, y0, c)
    }

    /**
     * Fill circle at (x,y) with radius r
     * Uses Brensenham algorithm.
     * */
    override fun fillCircle(x: Int, y: Int, r: Int, c: Int) {
        fun plot(xOff: Int, yOff: Int){
            drawLine(x, y, xOff, yOff, c)
        }

        plotCircle(x, y, r, c, ::plot)
    }

    /**
     * Fill triangle with given corners.
     * Uses Brensenham algorithm.
     * */
    override fun fillTri(x0: Int, y0: Int, x1: Int, y1: Int, x2: Int, y2: Int, c: Int) {
        fun plot(x: Int, y: Int){
            val index = y*_width + x
            if (index < _width*_height) pixels[index] = c
        }

        val dx = abs(x1 - x0)
        val sx = if(x0 < x1) 1 else -1
        val dy = -abs(y1 - y0)
        val sy = if(y0 < y1) 1 else -1
        var err = dx + dy
        var x = x0
        var y = y0
        while(true) {
            plot(x, y)
            drawLine(x, y, x2, y2, c)
            if (x == x1 && y == y1) break
            val e2 = 2*err
            if (e2 >= dy) {
                err += dy
                x += sx
            }
            if (e2 <= dx) {
                err += dx
                y += sy
            }
        }
    }

    /**Fill rectangle at (x0,y0) with width w and height h*/
    override fun fillRect(x0: Int, y0: Int, w: Int, h: Int, c: Int) {
        for(x in x0 until x0+w){
            drawLine(x, y, x, y+h-1, c)
        }
    }

    /**Draws buffer to screen*/
    override fun flush() {
        g.drawImage(bufferedImage, 0, 0, _width, _height, null)
    }

    private fun updateCanvasDim(){
        frame.isVisible = false
        frame.remove(this)
        preferredSize = Dimension(_width, _height)
        minimumSize = Dimension(_width, _height)
        maximumSize = Dimension(_width, _height)
        frame.add(this)
        frame.isVisible = true
    }

    private fun plotCircle(x: Int, y: Int, r: Int, c: Int, plot: (xOff: Int, yOff: Int) -> Unit) {
        var xOff : Int = 0
        var yOff : Int = r
        var f : Float = (5f/4f) - r.toFloat()

        plot(0, r)
        plot(r, 0)
        plot(0, -r)
        plot(-r, 0)

        while (xOff < yOff) {
            if (f < 0) f += 2 * xOff + 1
            else {
                f += 2*xOff - 2*yOff + 2
                yOff -= 1
            }
            xOff += 1
            plot(xOff, yOff)
            plot(-xOff, yOff)
            plot(-yOff, xOff)
            plot(-yOff, -xOff)
            plot(yOff, xOff)
            plot(yOff, -xOff)
            plot(xOff, -yOff)
            plot(-xOff, -yOff)
        }
    }
}