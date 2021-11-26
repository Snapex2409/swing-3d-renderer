package renderer

import math.Vec3
import scene.renderables.Renderable

class RenderEntry(
    var origin : Vec3,
    var renderable: Renderable
)