package window

/**
 * General functionality to draw. Uses absolute coordinates on screen.
 * */
interface IWindow {
    /**Set screen pixel width*/
    fun setWidth(w: Int)
    /**Get screen pixel width*/
    fun getWidth() : Int
    /**Set screen pixel height*/
    fun setHeight(h: Int)
    /**Get screen pixel height*/
    fun getHeight() : Int

    /**Draw line from (x0,y0) to (x1, y1)*/
    fun drawLine(x0: Int, y0: Int, x1: Int, y1: Int, c: Int)
    /**Draw circle at (x,y) with radius r*/
    fun drawCircle(x: Int, y: Int, r: Int, c: Int)
    /**Draw rectangle at (x0,y0) with width w and height h*/
    fun drawRect(x0: Int, y0: Int, w: Int, h: Int, c: Int)
    /**Draw triangle with given corners*/
    fun drawTri(x0: Int, y0: Int, x1: Int, y1: Int, x2: Int, y2: Int, c: Int)
    /**Fill circle at (x,y) with radius r*/
    fun fillCircle(x: Int, y: Int, r: Int, c: Int)
    /**Fill triangle with given corners*/
    fun fillTri(x0: Int, y0: Int, x1: Int, y1: Int, x2: Int, y2: Int, c: Int)
    /**Fill rectangle at (x0,y0) with width w and height h*/
    fun fillRect(x0: Int, y0: Int, w: Int, h: Int, c: Int)

    /**Draws buffer to screen*/
    fun flush()
}