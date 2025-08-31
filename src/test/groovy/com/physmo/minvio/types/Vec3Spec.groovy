package com.physmo.minvio.types

import spock.lang.Specification
import spock.lang.Unroll

class Vec3Spec extends Specification {

    def "Vec3 should be initialized with correct coordinates"() {
        given:
          def vec = new Vec3(1.0, 2.0, 3.0)

        expect:
          vec.x == 1
          vec.y == 2
          vec.z == 3
    }

    def "Vec3 should be initialized from another Vec3"() {
        given:
          def originalVec = new Vec3(1.0, 2.0, 3.0)

        when:
          def newVec = new Vec3(originalVec)

        then:
          newVec.x == originalVec.x
          newVec.y == originalVec.y
          newVec.z == originalVec.z
    }

    def "Vec3 copy constructor should throw exception for null input"() {
        when:
          new Vec3(null)

        then:
          thrown(IllegalArgumentException)
    }

    def "set should update the coordinates of the vector"() {
        given:
          def vec = new Vec3(1.0, 2.0, 3.0)

        when:
          vec.set(4.0, 5.0, 6.0)

        then:
          vec.x == 4.0
          vec.y == 5.0
          vec.z == 6.0
    }

    @Unroll
    def "add(#vec1, #vec2) should return a new vector with the sum of coordinates"() {
        when:
          def result = vec1.add(vec2)

        then:
          result == expected
          // Ensure original vector is unchanged
          vec1 == new Vec3(1.0, 2.0, 3.0)

        where:
          vec1                    | vec2                       | expected
          new Vec3(1.0, 2.0, 3.0) | new Vec3(4.0, 5.0, 6.0)    | new Vec3(5.0, 7.0, 9.0)
          new Vec3(1.0, 2.0, 3.0) | new Vec3(-1.0, -2.0, -3.0) | new Vec3(0.0, 0.0, 0.0)
    }

    @Unroll
    def "addi(#vec1, #vec2) should modify the vector in-place"() {
        when:
          vec1.addi(vec2)

        then:
          vec1 == expected

        where:
          vec1                    | vec2                       | expected
          new Vec3(1.0, 2.0, 3.0) | new Vec3(4.0, 5.0, 6.0)    | new Vec3(5.0, 7.0, 9.0)
          new Vec3(1.0, 2.0, 3.0) | new Vec3(-1.0, -2.0, -3.0) | new Vec3(0.0, 0.0, 0.0)
    }

    def "scale should return a new scaled vector"() {
        given:
          def vec = new Vec3(1.0, 2.0, 3.0)
          double scalar = 2.5

        when:
          def result = vec.scale(scalar)

        then:
          result == new Vec3(2.5, 5.0, 7.5)
          // Ensure original vector is unchanged
          vec == new Vec3(1.0, 2.0, 3.0)
    }

    def "scalei should scale the vector in-place"() {
        given:
          def vec = new Vec3(1.0, 2.0, 3.0)
          double scalar = 2.0

        when:
          vec.scalei(scalar)

        then:
          vec == new Vec3(2.0, 4.0, 6.0)
    }

    @Unroll
    def "distance between #vec1 and #vec2 should be #expectedDistance"() {
        when:
          double dist = vec1.distance(vec2)

        then:
          Math.abs(dist - expectedDistance) < 1e-10

        where:
          vec1                       | vec2                    | expectedDistance
          new Vec3(0.0, 0.0, 0.0)    | new Vec3(3.0, 4.0, 0.0) | 5.0
          new Vec3(1.0, 1.0, 1.0)    | new Vec3(1.0, 1.0, 1.0) | 0.0
          new Vec3(-1.0, -1.0, -1.0) | new Vec3(1.0, 1.0, 1.0) | Math.sqrt(12.0)
    }

    def "normalise should normalise the vector and return original magnitude"() {
        given:
          def vec = new Vec3(3.0, 4.0, 0.0)

        when:
          double originalMagnitude = vec.normalise()
          double newMagnitude = Math.sqrt(vec.x * vec.x + vec.y * vec.y + vec.z * vec.z)

        then:
          originalMagnitude == 5.0
          Math.abs(newMagnitude - 1.0) < 1e-10
          vec == new Vec3(0.6, 0.8, 0.0)
    }

    def "equals and hashCode contract"() {
        given:
          def vec1 = new Vec3(1.0, 2.0, 3.0)
          def vec2 = new Vec3(1.0, 2.0, 3.0)
          def vec3 = new Vec3(1.0, 2.0, 3.00000000001) // within epsilon
          def vec4 = new Vec3(4.0, 5.0, 6.0)

        expect:
          vec1.equals(vec1)
          vec1.equals(vec2)
          vec2.equals(vec1)
          vec1.hashCode() == vec2.hashCode()

          vec1.equals(vec3)
          vec3.equals(vec1)
          vec1.hashCode() != vec3.hashCode()

          !vec1.equals(vec4)
          vec1.hashCode() != vec4.hashCode()
          !vec1.equals(null)
          !vec1.equals("a string")
    }

    def "toString should produce a formatted string"() {
        given:
          def vec = new Vec3(1.234, 5.678, 9.0)

        when:
          String str = vec.toString()

        then:
          str == "Vec3{x=1.23, y=5.68, z=9.00}"
    }

}
