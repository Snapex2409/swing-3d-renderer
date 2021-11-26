package math

import kotlin.math.sqrt

data class Vec3(
    val x: Double,
    val y: Double,
    val z: Double
) {
    companion object{
        val ZERO = Vec3(0.0, 0.0, 0.0)
    }

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
        return Vec3(y*other.z - other.y*z, z*other.x - other.z*x, x*other.y - other.x*y)
    }

    /**Dot Product*/
    operator fun times(other: Vec3) : Double {
        return x*other.x + y*other.y + z*other.z
    }

    /**Returns scaled vector by factor num*/
    operator fun times(num: Double) : Vec3 {
        return Vec3(num*x, num*y, num*z)
    }

    /**Returns length of this vector*/
    fun length() : Double {
        return sqrt(x*x + y*y + z*z)
    }

    /**Returns normalized vector of this vector.*/
    fun normalize() : Vec3 {
        val len = length()
        return this * (1.0/len)
    }
}