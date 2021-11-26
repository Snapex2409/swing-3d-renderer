package renderer

import math.Vec3
import scene.renderables.BaseRenderables

/**
 * Provides RenderEntry instances.
 * */
object RenderEntryPool {
    private val pool = mutableListOf<RenderEntry>()

    /**
     * Returns an RenderEntry.
     * */
    fun getEntry() : RenderEntry {
        return if (pool.isEmpty()) {
            RenderEntry(Vec3.ZERO, BaseRenderables.Empty)
        } else {
            pool.removeFirst()
        }
    }

    fun releaseEntry(entry: RenderEntry) {
        pool.add(entry)
    }
}