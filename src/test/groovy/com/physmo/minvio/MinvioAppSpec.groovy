package com.physmo.minvio


import spock.lang.Specification

class MinvioAppSpec extends Specification {

    def "Basic startup test"() {
        given:
          MinvioApp app = new MinvioApp() {
              int counter = 0;

              @Override
              void draw(double delta) {
                  println "hello"
                  counter++;
                  if (counter == 5) {
                      stop()
                  }
              }
          }

          app.start(200, 200, "Simple Example", 60);
        expect:
          1 == 1
    }

    def "lines"() {
        given:
          MinvioApp app = new MinvioApp() {
              @Override
              void draw(double delta) {
                  DrawingContextTests.testAll(getDrawingContext())
                  DrawingContextTests.testAll(this)
                  stop()
              }
          }

          app.start(200, 200, "Simple Example", 60);
        expect:
          1 == 1
    }
}