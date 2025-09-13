package com.physmo.minvio.types

import spock.lang.Specification


class ArraySpec extends Specification {

    def "adding elements increases size and elements can be retrieved"() {
        given:
          Array<String> array = new Array<>(10)

        when:
          array.add("a")
          array.add("b")
          array.add("c")

        then:
          array.size() == 3
          array.get(0) == "a"
          array.get(1) == "b"
          array.get(2) == "c"
    }

    def "clear removes all elements"() {
        given:
          Array<String> array = new Array<>(10)
          array.add("a")
          array.add("b")

        when:
          array.clear()

        then:
          array.size() == 0
          array.isEmpty()
    }

    def "removeIf removes elements based on predicate"() {
        given:
          Array<Integer> array = new Array<>(10)
          array.add(1)
          array.add(2)
          array.add(3)
          array.add(4)

        when:
          boolean removed = array.removeIf { it % 2 == 0 }

        then:
          removed
          array.size() == 2
          array.get(0) == 1
          array.get(1) == 3
    }

    def "array resizes when capacity is reached"() {
        given:
          Array<String> array = new Array<>(2)

        when:
          array.add("a")
          array.add("b")
          array.add("c")

        then:
          array.size() == 3
          array.getCapacity() == 4
          array.get(0) == "a"
          array.get(1) == "b"
          array.get(2) == "c"
    }

    def "sort should order elements correctly"() {
        given:
          Array<Integer> array = new Array<>(5)
          array.add(3)
          array.add(1)
          array.add(4)
          array.add(2)
          array.add(5)

        when:
          array.sort(Comparator.naturalOrder())

        then:
          array.get(0) == 1
          array.get(1) == 2
          array.get(2) == 3
          array.get(3) == 4
          array.get(4) == 5
    }

    def "addAll should add all elements from a list"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")
          def list = ["b", "c"]

        when:
          array.addAll(list)

        then:
          array.size() == 3
          array.get(0) == "a"
          array.get(1) == "b"
          array.get(2) == "c"
    }

    def "iterator is functional"() {
        given:
          Array<String> array = new Array<>(5)
          array.addAll(["a", "b", "c"])


        when:
          List<String> list = new ArrayList<>();
          for (String s in array) {
              list.add(s)
          }

        then:
          list.size() == array.size()
          list.get(0) == array.get(0)
          list.get(1) == array.get(1)
          list.get(2) == array.get(2)
    }

    def "addAll with Array should add all elements"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")
          Array<String> otherArray = new Array<>(2)
          otherArray.add("b")
          otherArray.add("c")

        when:
          array.addAll(otherArray)

        then:
          array.size() == 3
          array.get(0) == "a"
          array.get(1) == "b"
          array.get(2) == "c"
    }

    def "contains should return correct boolean value"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")
          array.add("b")

        expect:
          array.contains(element) == expected

        where:
          element | expected
          "a"     | true
          "b"     | true
          "c"     | false
    }

    def "indexOf should return correct index or -1"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")
          array.add("b")

        expect:
          array.indexOf(element) == expected

        where:
          element | expected
          "a"     | 0
          "b"     | 1
          "c"     | -1
    }

    def "setAt should update element at given index"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")
          array.add("b")

        when:
          array.setAt(1, "c")

        then:
          array.size() == 2
          array.get(0) == "a"
          array.get(1) == "c"
    }

    def "setAt should throw exceptions for invalid arguments"() {
        given:
          Array<String> array = new Array<>(5)
          array.add("a")

        when:
          array.setAt(index, element)

        then:
          thrown(exception)

        where:
          index | element | exception
          1     | "c"     | ArrayIndexOutOfBoundsException.class
          -1    | "c"     | ArrayIndexOutOfBoundsException.class
          0     | null    | NullPointerException.class
    }

    def "removeIf returns false when no elements are removed"() {
        given:
          Array<Integer> array = new Array<>(10)
          array.add(1)
          array.add(3)

        when:
          boolean removed = array.removeIf { it % 2 == 0 }

        then:
          !removed
          array.size() == 2
    }

    def "iterator next() returns null when no more elements"() {
        given:
          Array<String> array = new Array<>(1)
          array.add("a")
          def iterator = array.iterator()

        when:
          iterator.next() // consume "a"
          def result = iterator.next() // should be null

        then:
          !iterator.hasNext()
          result == null
    }
}