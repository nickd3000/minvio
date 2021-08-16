# Minvio
Minvio is a small Java library that offers very simple window, graphics and input support.  It's useful when you want to rapidly prototype an idea or add some simple graphics to a program without having to involve heavy weight libraries.

http://www.coolbubble.com/projects/minvio

Add maven dependency:

    <dependency>
        <groupId>io.github.nickd3000</groupId>
        <artifactId>minvio</artifactId>
        <version>1.02</version>
    </dependency>

![Image Minvio example CubeWave](docs/cubeWave.png)
![Image Minvio example MouseExample](docs/mouseExample.png)

http://cc.coolbubble.com:8099

**Minimal example**

```java
import com.physmo.minvio.BasicDisplay;
import com.physmo.minvio.BasicDisplayAwt;
import com.physmo.minvio.MinvioApp;

import java.awt.Color;

class SimpleExample extends MinvioApp {

    public static void main(String... args) {
        MinvioApp app = new SimpleExample();
        app.start(new BasicDisplayAwt(200, 200), "Simple Example", 60);
    }

    @Override
    public void draw(BasicDisplay bd, double delta) {
        bd.cls(Color.LIGHT_GRAY);
        bd.setDrawColor(Color.WHITE);
        bd.drawFilledRect(75, 75, 50, 50);
        bd.setDrawColor(Color.BLUE);
        bd.drawCircle(100, 100, 70);
        bd.drawText("X:" + bd.getMouseX() + " Y:" + bd.getMouseY(), 10, 190);
    }
}
```
