# MinVio FAQ

Answers to the most common questions about using the MinVio library.

If you can’t find what you need here, please open an issue on GitHub: https://github.com/nickd3000/minvio/issues

---

## What is MinVio?

MinVio is a lightweight Java framework for quickly building graphical applications. It creates the window, runs a timed
draw loop, and provides simple drawing and input functions so you can focus on ideas instead of boilerplate.

- Project repo: https://github.com/nickd3000/minvio
- Examples: https://github.com/nickd3000/minvio-examples
- Intro blog post: pages/blog001.md

## Who is MinVio for?

Anyone who wants to sketch visual ideas in Java with minimal setup: programmatic artists, educators, students, and
developers prototyping algorithms or visualizations.

## How do I install MinVio?

Add the dependency from Maven Central.

Maven (pom.xml):

```xml

<dependency>
    <groupId>io.github.nickd3000</groupId>
    <artifactId>minvio</artifactId>
    <version>1.21</version>
</dependency>
```

Gradle (Kotlin DSL):

```kotlin
dependencies {
    implementation("io.github.nickd3000:minvio:1.21")
}
```

Gradle (Groovy DSL):

```groovy
dependencies {
    implementation 'io.github.nickd3000:minvio:1.21'
}
```

MinVio requires Java 17 or newer.

## What’s the smallest possible program?

```java
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class SimpleExample extends MinvioApp {
    public static void main(String... args) {
        new SimpleExample().start(200, 200, "Simple Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(new Color(207, 198, 179));
        setDrawColor(new Color(17, 52, 69, 215));
        drawFilledRect(50, 50, 40, 40);
        drawText("X:" + getMouseX() + " Y:" + getMouseY(), 10, 190);
    }
}
```

## How does the draw loop work?

- Call `start(width, height, title, targetFps)` to create the window and begin the loop.
- MinVio calls your `draw(double delta)` each frame.
- `delta` is the elapsed time in seconds since the last frame (useful for time-based animation).
- You can show the current FPS via `setDisplayFps(true)` if supported by your version.

## What coordinate system does MinVio use?

- Origin (0,0) is the top-left corner of the window.
- X increases to the right, Y increases downward.
- Distances and sizes are in pixels.

## Can I resize the window?

Yes. As of v1.20, window resizing is supported. Your drawing code should render to the current size each frame. Query
the size via your display/context if needed.

## Which platforms are supported?

MinVio uses standard Java desktop APIs (AWT/Swing), so it works on Windows, macOS, and Linux where a compatible Java 17+
runtime is available.

## How do I draw shapes, text, and images?

Common methods available from `MinvioApp` include:

- `cls(Color)` / `cls()`
- `setDrawColor(Color)`
- `drawPoint`, `drawLine`, `drawRect`, `drawFilledRect`
- `drawCircle`, `drawFilledCircle`
- `drawText`
- `drawImage`

See the README and examples for more:

- README: ../README.md
- Examples: https://github.com/nickd3000/minvio-examples

## Is there a color palette utility?

Yes. A `Palette` class with predefined colors is available (v1.20+). You can still use standard `java.awt.Color`
anywhere.

## How do I read input?

MinVio provides simple mouse helpers like `getMouseX()` and `getMouseY()`. Keyboard and more advanced input options are
available through the underlying Java desktop APIs; see the examples and source for patterns.

## How do I control performance and smoothness?

- Prefer time-based motion using `delta` over per-frame constants.
- Avoid creating lots of temporary objects inside `draw()`.
- Use a reasonable target FPS (e.g., 60) in `start(...)`.
- Optionally display FPS to gauge performance.

## What Java version do I need?

Java 17 or newer.

## Where can I find API docs?

You can generate Javadoc locally with Maven: `mvn javadoc:javadoc` (output under `target/apidocs`). Example projects
also showcase API usage.

## What license does MinVio use?

See LICENCE.TXT in the repository root for full details.

## I added the dependency but Maven/Gradle can’t resolve it.

- Ensure you have Maven Central enabled in your build.
- Double-check the group/artifact/version: `io.github.nickd3000:minvio:1.21`.
- Refresh your IDE/build (e.g., "Reload All Maven Projects" in IntelliJ, or `./gradlew --refresh-dependencies`).

## My program runs but I see a black/blank window.

- Make sure your `draw(double delta)` is being called (add a `drawText` of the time or FPS).
- Call `cls(...)` or draw something each frame; don’t forget to set a draw color if drawing shapes.
- Verify alpha values in `Color` are not fully transparent.

## The window doesn’t appear when I run my program.

- Ensure you call `start(width, height, title, fps)` from `main`.
- Run on a desktop (non-headless) Java runtime.
- Check for exceptions in the console; fix any NoClassDefFoundError by confirming dependency setup.

## How do I include MinVio in a Gradle/Maven multi-module project?

Add the dependency to the module that contains your application code, not just the root project. Ensure that module is
configured to produce/run a desktop application.

## Where can I report bugs or request features?

Use GitHub issues: https://github.com/nickd3000/minvio/issues. When reporting, include:

- OS, Java version, MinVio version
- Minimal code sample
- Steps to reproduce and expected vs actual behavior

## Where can I learn more?

- README for quick start and changelog: ../README.md
- First blog post: pages/blog001.md
- More examples: https://github.com/nickd3000/minvio-examples
