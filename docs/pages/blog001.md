# Introduction to MinVio: Getting Started with Lightweight Java Graphics

## What is MinVio and Why it Was Created

MinVio is a lightweight Java framework designed for developing graphical applications with minimal setup and complexity.
Created to fill the need for a simple yet powerful graphics solution, MinVio handles the common challenges of creating
visual applications such as window management, drawing loops, and providing intuitive drawing functions.

The library was developed with several key goals in mind:

- Simplicity: allowing developers to focus on their ideas rather than boilerplate code
- Lightweight: avoiding unnecessary dependencies and complexity
- Versatility: suitable for programmatic art, experiments, algorithm visualization, educational tools, and rapid
  prototyping

## Installation and Setup

Adding MinVio to your Java project is straightforward using Maven. Simply add the following dependency to your `pom.xml`
file:

```xml

<dependency>
    <groupId>io.github.nickd3000</groupId>
    <artifactId>minvio</artifactId>
    <version>1.21</version>
</dependency>
```

MinVio requires Java 17 or later, as indicated in its Maven configuration.

## Your First MinVio Application

Let's create a simple application that displays a window with some basic shapes and text. The MinVio architecture makes
this remarkably easy:

```java
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

public class SimpleExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        app.start(600, 300, "Hello MinVio", 60);
    }

    @Override
    public void draw(double delta) {
        // Clear the screen with a light beige color
        cls(new Color(207, 198, 179));

        // Set drawing color to a semi-transparent dark blue
        setDrawColor(new Color(17, 52, 69, 215));

        // Draw some text that moves across the screen
        int x = (int) (System.currentTimeMillis() / 20) % 200;
        drawText("Hello, MinVio World!", x, 100);

        // Draw some simple shapes
        drawFilledRect(50, 150, 40, 40);
        drawFilledCircle(150, 170, 20);
        drawCircle(250, 170, 20);

        // Show mouse coordinates
        drawText("Mouse: X:" + getMouseX() + " Y:" + getMouseY(), 10, 270);
    }
}
```

When you run this code, you'll see a window appear with the title "Hello MinVio", displaying moving text, some shapes,
and tracking your mouse position. This simple example demonstrates several key features of MinVio.

## Basic Drawing Functions Explained

MinVio provides a comprehensive set of drawing functions that make it easy to create visual elements:

- **`cls(Color)` / `cls()`**: Clears the screen with a specified color or the default background color
- **`setDrawColor(Color)`**: Sets the color for subsequent drawing operations
- **`drawPoint(x, y)`**: Draws a single pixel at the specified coordinates
- **`drawLine(x1, y1, x2, y2)`**: Draws a line between two points
- **`drawRect(x, y, width, height)`**: Draws an outline rectangle
- **`drawFilledRect(x, y, width, height)`**: Draws a filled rectangle
- **`drawCircle(x, y, radius)`**: Draws an outline circle
- **`drawFilledCircle(x, y, radius)`**: Draws a filled circle
- **`drawText(text, x, y)`**: Renders text at the specified position
- **`drawImage(image, x, y)`**: Draws an image at the specified position

All these methods are conveniently accessible directly from your `MinvioApp` subclass, making your code clean and
readable.

## Window Management and the Application Loop

One of MinVio's greatest strengths is how it handles the complexities of window management and the application loop for
you. When you call `start()` with your preferred dimensions, title, and target frame rate, MinVio:

1. Creates a window of the specified size with the given title
2. Sets up an efficient drawing loop that targets your specified frame rate
3. Calls your overridden `draw()` method on each frame
4. Automatically manages refreshing the display

The `delta` parameter passed to your `draw()` method provides the time elapsed since the last frame in seconds, allowing
you to create smooth, time-based animations regardless of actual frame rate.

MinVio also provides other useful methods:

- `init(BasicDisplay)`: Called once before the draw loop begins, ideal for setup
- `update(BasicDisplay, delta)`: Can be overridden for additional logic between frames
- `getMouseX()` and `getMouseY()`: Get the current mouse coordinates
- `getFps()`: Returns the current frames per second
- `setDisplayFps(boolean)`: Toggle display of the current FPS on screen

## Conclusion

MinVio provides a perfect balance between simplicity and capability for Java developers looking to create visual
applications. Whether you're creating algorithmic art, educational tools, or just experimenting with graphics
programming, MinVio's straightforward API and efficient implementation make it an excellent choice.

The lightweight nature of the library means you can quickly get started without a steep learning curve or heavy
dependencies, while still having access to a rich set of drawing capabilities. As you grow more comfortable with the
basics, MinVio offers more advanced features like image manipulation, color gradients, and utility classes to support
more complex applications.

Ready to get started? Add the MinVio dependency to your project, create a subclass of `MinvioApp`, override the `draw()`
method, and start bringing your visual ideas to life!

---

In the next post in this series, we'll explore more advanced MinVio features, including animation techniques, keyboard
input handling, and using color gradients to create visually stunning effects. Stay tuned!