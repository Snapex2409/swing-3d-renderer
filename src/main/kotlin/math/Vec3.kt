package math

data class Vec3(
    val x: Double,
    val y: Double,
    val z: Double
) {

    /**Plus*/
    operator fun plus(other: Vec3) : Vec3{
        return Vec3(x+other.x, y+other.y, z+other.z)
    }

    /**Minus*/
    operator fun minus(other: Vec3) : Vec3{
        return Vec3(x+other.x, y+other.y, z+other.z)
    }

    /**Cross Product*/
    operator fun rem(other: Vec3) : Vec3 {
        TODO()
    }

    /**Dot Product*/
    operator fun times(other: Vec3) : Double {
        TODO()
    }
}