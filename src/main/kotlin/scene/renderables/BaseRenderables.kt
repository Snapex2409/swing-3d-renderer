package scene.renderables

import math.Mesh
import math.Vec3
import window.Window

object BaseRenderables {

    object Empty : Renderable() {
        override val mesh = Mesh.EMPTY
        override fun render(window: Window, origin: Vec3) {}
    }
}