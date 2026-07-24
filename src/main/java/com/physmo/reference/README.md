# Minvio Reference Examples

Updated: 2026-07-24 00:56 BST

These examples are compiled with the project but excluded from the published
binary and Javadoc artifacts. They are intended as runnable reference material
for learning Minvio, exploring the API, and testing ideas.

## Recommended Style

Use `MinvioApp` for normal examples and sketches:

1. Extend `MinvioApp`.
2. Override `init`, `update`, and/or `draw`.
3. Call `start(width, height, title, fps)`.

The `lowlevel` package shows direct `BasicDisplay` usage for cases where you
want to own the display, drawing context, input polling, and repaint loop
yourself.

## Package Guide

| Package       | Purpose                                                                                                                   |
|---------------|---------------------------------------------------------------------------------------------------------------------------|
| `beginner`    | Java novice examples. These use extra comments and teach one language concept at a time.                                  |
| `basics`      | Core Minvio usage: simple app setup, images, text, resize, screenshots, and timing.                                       |
| `input`       | Keyboard, mouse, mouse buttons, mouse listeners, and direct interaction examples.                                         |
| `drawing`     | Drawing primitives, style state, gradients, helper utilities, Java2D shape interop, and pixel sampling.                   |
| `concepts`    | Creative-coding concepts such as particles, forces, collision, interpolation, noise, transforms, and generative patterns. |
| `lowlevel`    | Focused direct `BasicDisplay` examples. Keep this package small.                                                          |
| `gallery`     | More polished visual sketches that combine several concepts.                                                              |
| `gui`         | GUI controls, styling, and layout examples.                                                                               |
| `ecs`         | Entity/component examples and supporting components.                                                                      |
| `experiments` | Larger exploratory tools, workbench programs, and less tutorial-like experiments.                                         |
| `wiki`        | Examples tied to wiki/docs pages.                                                                                         |
| `rigs`        | Small development harnesses.                                                                                              |

## Suggested Learning Path

1. Start with `beginner` if you are learning Java syntax.
2. Use `basics` to learn the Minvio application model.
3. Move to `input` and `drawing` for common interactive graphics work.
4. Explore `concepts` for particles, motion, color, collision, and generative
   techniques.
5. Look at `gallery`, `gui`, and `ecs` for larger examples.
6. Use `lowlevel` only when you need direct display-loop control.

## Beginner Example Style

Beginner examples intentionally have more comments than the rest of the codebase.
Those comments should explain the Java idea and the visual effect on screen.
Short `Java note` comments are useful when they clarify common mistakes or
surprising behavior directly related to the code.

Avoid broad historical trivia or unrelated background inside runnable examples;
put that material in docs or tutorials instead.
