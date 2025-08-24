![Image Minvio example CubeWave](docs/wiki/appLogo.png)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.nickd3000/minvio/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.nickd3000/minvio)
![GitHub](https://img.shields.io/github/license/nickd3000/minvio)

![Image Palette Example](docs/IQPalette.png)

Minvio is a Lightweight Java framework for developing graphical applications.

Minvio handles creating the application window and timed draw loop and exposes a host of drawing and input
functionality.

Great for Programmatic Art, Experiments, POC's, Algorithm Development, Toys, Learning.

Find examples here: https://github.com/nickd3000/minvio-examples
open
Add maven dependency:

``` xml
<dependency>
    <groupId>io.github.nickd3000</groupId>
    <artifactId>minvio</artifactId>
    <version>1.20</version>
</dependency>
```

**Minimal example**

``` java
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class SimpleExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        app.start(200, 200, "Simple Example", 60);
    }

    @Override
    public void draw(double delta) {
        cls(Palette.SLATE);
        setDrawColor(Palette.AMBER);
        
        drawFilledRect(50, 50, 40, 40);
        drawFilledCircle(120, 70, 20);
        drawCircle(120, 120, 20);
        drawRect(50, 100, 40, 40);

        drawText("X:" + getMouseX() + " Y:" + getMouseY(), 10, 190);

    }
}
```

![Image Simple Example](docs/wiki/simpleExample.png)

### Changelist

###### Version 1.20 - Aug 2025

* Added Screen resizing functionality
* Added Initial GUI layout support
* Added Palette Class with predefined colours
* Updated to Java 17
* Added Array class for an efficient List style container that avoids reallocation.

###### Version 1.10 - September 2024

* Gui: Added Label, Slider and text button.
* Gui: Added more color components to GuiContext.
* Cleanup: Moved structure objects to types package.

###### Version 1.08 - August 2024

* Simplified app startup
* Added pass-through methods in MinvioApp to drawing context

###### Version 1.07 - May 2023

* Removed all examples - They are now in a separate project - minvio-examples

###### Version 1.06 - December 23 2021

* Matrix drawer changes
* Added QuickRandom

###### Version 1.05 - December 13 2021

* **Added Entity-Component system**
* Added Entity-Component example
* Added Vec3 object
* Added bucket list utility and gravity particle example
* Added Ribbons to gallery projects
* Added getMousePointNormalised

###### Version 1.04 - October 17 2021

* Added Perlin Noise utility and examples.
* Added colour gradient system and examples.
* Added Matrix and point list rendering helpers with examples.

###### Version 1.03 - October 10 2021

* Added anchor system.
* Rearranged example file folders.
* Added lerp functions for several types.
* Added FindClosestPointInList helper function.
* Changes to image loading.

http://www.coolbubble.com/cc/ - coolcompare