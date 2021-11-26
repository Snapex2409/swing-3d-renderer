package scene.renderables

import math.Mesh
import math.Vec3
import window.Window

/**
 * Renderable object. Used in singletons.
 * Each instance will be passed as a part of a render entry to the renderer.
 * Renderer will call specified render method with origin information.
 * */
abstract class Renderable {
    /**
     * Mesh data of this renderable.
     * */
    abstract val mesh : Mesh

    /**
     * Renders itself into window.
     * */
    abstract fun render(window: Window, origin: Vec3)
}