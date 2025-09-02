package com.physmo.minvio.types

import spock.lang.Specification
import spock.lang.Unroll

class PointIntSpec extends Specification {

    def "constructor should initialize point with correct integer coordinates"() {
        when:
          def p = new PointInt(10, -20)

        then:
          p.x == 10
          p.y == -20
    }

    def "add with another PointInt should modify the point in-place"() {
        given:
          def p1 = new PointInt(5, 10)
          def p2 = new PointInt(3, -4)

        when:
          p1.add(p2)

        then:
          p1.x == 8
          p1.y == 6
    }

    def "add with integer coordinates should modify the point in-place"() {
        given:
          def p = new PointInt(10, 20)

        when:
          p.add(5, -15)

        then:
          p.x == 15
          p.y == 5
    }

    @Unroll
    def "static distance between #p1 and #p2 should be #expectedDistance"() {
        when:
          def dist = PointInt.distance(p1, p2)

        then:
          // Use a small tolerance for floating point comparison
          Math.abs(dist - expectedDistance) < 1e-10

        where:
          p1                   | p2                   | expectedDistance
          new PointInt(0, 0)   | new PointInt(3, 4)   | 5.0
          new PointInt(10, 10) | new PointInt(10, 10) | 0.0
          new PointInt(-1, -2) | new PointInt(2, 2)   | 5.0 // dx=3, dy=4
    }

    def "toString should produce a correctly formatted string"() {
        given:
          def p = new PointInt(5, 42)

        when:
          def str = p.toString()

        then:
          str == "[5,42]"
    }
}
