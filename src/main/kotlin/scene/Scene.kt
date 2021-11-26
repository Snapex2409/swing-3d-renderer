package scene

import scene.renderables.Renderable

/**
 * Renderable Scene.
 * */
abstract class Scene {
    abstract fun addObject(renderable: Renderable)
    abstract fun getObjects() : List<Renderable>

    object EmptyScene : Scene() {
        override fun addObject(renderable: Renderable) {}

        override fun getObjects(): List<Renderable> {
            return emptyList()
        }
    }
}