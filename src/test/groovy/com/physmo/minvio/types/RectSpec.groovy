package com.physmo.minvio.types

import spock.lang.Specification
import spock.lang.Unroll

class RectSpec extends Specification {

    def "constructor should initialize rect with correct values"() {
        when:
          def rect = new Rect(10, 20, 100, 200)

        then:
          rect.x == 10
          rect.y == 20
          rect.w == 100
          rect.h == 200
    }

    def "copy constructor should create a new equal instance"() {
        given:
          def original = new Rect(10, 20, 100, 200)

        when:
          def copy = new Rect(original)

        then:
          copy.x == original.x
          copy.y == original.y
          copy.w == original.w
          copy.h == original.h
          copy == original
          !copy.is(original)
    }

    @Unroll
    def "isPointInside(#px, #py) in #rect should be #expected"() {
        expect:
          rect.isPointInside(px, py) == expected

        where:
          rect                      | px  | py | expected
          new Rect(10, 20, 100, 50) | 50  | 50 | true      // Inside
          new Rect(10, 20, 100, 50) | 10  | 20 | true      // Top-left corner
          new Rect(10, 20, 100, 50) | 110 | 70 | true      // Bottom-right corner
          new Rect(10, 20, 100, 50) | 10  | 50 | true      // Left edge
          new Rect(10, 20, 100, 50) | 50  | 20 | true      // Top edge
          new Rect(10, 20, 100, 50) | 9   | 50 | false     // Outside left
          new Rect(10, 20, 100, 50) | 111 | 50 | false     // Outside right
          new Rect(10, 20, 100, 50) | 50  | 19 | false     // Outside top
          new Rect(10, 20, 100, 50) | 50  | 71 | false     // Outside bottom
    }

    @Unroll
    def "rect1 #rect1 intersects rect2 #rect2 should be #expected"() {
        expect:
          rect1.intersects(rect2) == expected
          rect2.intersects(rect1) == expected // Should be commutative

        where:
          rect1                  | rect2                    | expected
          new Rect(0, 0, 10, 10) | new Rect(5, 5, 10, 10)   | true      // Overlap
          new Rect(0, 0, 10, 10) | new Rect(10, 0, 10, 10)  | true      // Touching edge
          new Rect(0, 0, 10, 10) | new Rect(10, 10, 10, 10) | true      // Touching corner
          new Rect(0, 0, 10, 10) | new Rect(11, 0, 10, 10)  | false     // Separate X
          new Rect(0, 0, 10, 10) | new Rect(0, 11, 10, 10)  | false     // Separate Y
          new Rect(0, 0, 10, 10) | new Rect(2, 2, 6, 6)     | true      // Contained
          new Rect(2, 2, 6, 6)   | new Rect(0, 0, 10, 10)   | true      // Contains
          new Rect(0, 0, 10, 10) | new Rect(0, 0, 10, 10)   | true      // Identical
    }

    def "copy method should create a new equal instance"() {
        given:
          def original = new Rect(5, 5, 50, 50)

        when:
          def copied = original.copy()

        then:
          copied == original
          !copied.is(original)
    }

    def "getArea should return correct area"() {
        given:
          def rect = new Rect(0, 0, 10, 20)

        expect:
          rect.getArea() == 200
    }

    @Unroll
    def "rect #container contains rect #other should be #expected"() {
        expect:
          container.contains(other) == expected

        where:
          container                | other                      | expected
          new Rect(0, 0, 100, 100) | new Rect(10, 10, 80, 80)   | true      // Fully contains
          new Rect(0, 0, 100, 100) | new Rect(0, 0, 100, 100)   | true      // Identical
          new Rect(0, 0, 100, 100) | new Rect(-1, 10, 80, 80)   | false     // Overlap left
          new Rect(0, 0, 100, 100) | new Rect(90, 90, 20, 20)   | false     // Overlap bottom-right
          new Rect(0, 0, 100, 100) | new Rect(110, 110, 10, 10) | false     // Completely outside
    }

    def "getCenterX and getCenterY should return the correct center point"() {
        given:
          def rectEven = new Rect(10, 20, 100, 50)
          def rectOdd = new Rect(10, 20, 101, 51)

        expect:
          rectEven.getCenterX() == 60 // 10 + 100/2
          rectEven.getCenterY() == 45 // 20 + 50/2

        and: "Check integer division for odd sizes"
          rectOdd.getCenterX() == 60 // 10 + 101/2 (10 + 50)
          rectOdd.getCenterY() == 45 // 20 + 51/2 (20 + 25)
    }

    def "set with values should update the rect"() {
        given:
          def rect = new Rect(0, 0, 0, 0)

        when:
          rect.set(5, 15, 55, 155)

        then:
          rect.x == 5
          rect.y == 15
          rect.w == 55
          rect.h == 155
    }

    def "set with another rect should update the rect"() {
        given:
          def rectToChange = new Rect(0, 0, 0, 0)
          def sourceRect = new Rect(5, 15, 55, 155)

        when:
          rectToChange.set(sourceRect)

        then:
          rectToChange.x == 5
          rectToChange.y == 15
          rectToChange.w == 55
          rectToChange.h == 155
    }

    def "toString should produce a formatted string"() {
        given:
          def rect = new Rect(1, 2, 3, 4)

        expect:
          rect.toString() == "Rect{x=1, y=2, w=3, h=4}"
    }

    def "equals and hashCode contract"() {
        given:
          def rect1 = new Rect(10, 20, 30, 40)
          def rect2 = new Rect(10, 20, 30, 40)
          def rect3 = new Rect(99, 20, 30, 40)

        expect:
          // equals
          rect1.equals(rect1)
          rect1.equals(rect2)
          rect2.equals(rect1)
          !rect1.equals(rect3)
          !rect1.equals(null)
          !rect1.equals("a string")

        and: "hashCode"
          rect1.hashCode() == rect2.hashCode()
          rect1.hashCode() != rect3.hashCode()
    }
}
