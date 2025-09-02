package com.physmo.minvio.types

import spock.lang.Specification
import spock.lang.Unroll

class PointSpec extends Specification {

    def "default constructor should initialize point at origin"() {
        when:
          def p = new Point()

        then:
          p.x == 0d
          p.y == 0d
    }

    def "constructor with coordinates should initialize point correctly"() {
        when:
          def p = new Point(10.5d, -5.5d)

        then:
          p.x == 10.5d
          p.y == -5.5d
    }

    def "copy constructor should create a copy of the point"() {
        given:
          def original = new Point(3.0d, 4.0d)

        when:
          def copy = new Point(original)

        then:
          copy.x == original.x
          copy.y == original.y
          !copy.is(original) // Ensure it's a new instance
    }

    def "copy constructor should throw exception for null input"() {
        when:
          new Point(null as Point)

        then:
          thrown(IllegalArgumentException)
    }

    def "add with another point should modify the point in-place"() {
        given:
          def p1 = new Point(1.0d, 2.0d)
          def p2 = new Point(3.0d, 4.0d)

        when:
          p1.add(p2)

        then:
          p1.x == 4.0d
          p1.y == 6.0d
    }

    def "add with null point should throw exception"() {
        given:
          def p = new Point(1.0d, 2.0d)

        when:
          p.add(null as Point)

        then:
          thrown(IllegalArgumentException)
    }

    def "add with coordinates should modify the point in-place"() {
        given:
          def p = new Point(1.0, 2.0)

        when:
          p.add(5.5, -1.5)

        then:
          p.x == 6.5d
          p.y == 0.5d
    }

    def "multiply should scale the point in-place"() {
        given:
          def p = new Point(3.0, -4.0)

        when:
          p.multiply(2.5d)

        then:
          p.x == 7.5d
          p.y == -10.0d
    }

    @Unroll
    def "static distance between #p1 and #p2 should be #expected"() {
        when:
          def dist = Point.distance(p1, p2)

        then:
          Math.abs(dist - expected) < 1e-10

        where:
          p1                | p2              | expected
          new Point(0, 0)   | new Point(3, 4) | 5.0
          new Point(1, 1)   | new Point(1, 1) | 0.0
          new Point(-1, -1) | new Point(1, 1) | Math.sqrt(8.0)
    }

    def "instance distance to another point should be calculated correctly"() {
        given:
          def p1 = new Point(3, 0)
          def p2 = new Point(0, 4)

        when:
          def dist = p1.distance(p2)

        then:
          Math.abs(dist - 5.0) < 1e-10
    }

    def "equals and hashCode contract"() {
        given:
          def p1 = new Point(1.1, 2.2)
          def p2 = new Point(1.1, 2.2)
          def p3 = new Point(3.3, 4.4)

        expect:
          p1 == p1
          p1 == p2
          p2 == p1
          p1.hashCode() == p2.hashCode()

          p1 != p3
          p1.hashCode() != p3.hashCode()
          p1 != null
          p1 != "a string"
    }

    def "toString should produce a formatted string"() {
        given:
          def p = new Point(1.234, 5.678)

        when:
          String str = p.toString()

        then:
          str == "Point{x=1.23, y=5.68}"
    }

}
