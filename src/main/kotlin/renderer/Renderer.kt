package renderer

import math.Vec3
import scene.renderables.Renderable
import window.Window

/**
 * Renders all provided render entries.
 * */
abstract class Renderer(
    protected val window: Window,
    val frameRate: Double
) {
    private val renderList = mutableListOf<RenderEntry>()

    /**Renders all entries of the render list. Will depend on renderer implementation.*/
    abstract fun render(renderList: MutableList<RenderEntry>)

    fun start() {

    }
}