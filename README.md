# GraphicCatoms2DConfigGenerator
A Graphic Generator of 2D Catoms Configurations for the [VisibleSim simulator](http://projects.femto-st.fr/projet-visiblesim/).

### Dependency

GraphicCatoms2DConfigGenerator can be opened and launched using Processing 3.

### Usage

1. Open and execute the Processing sketch `build/GraphicCatoms2DConfigGenerator/GraphicCatoms2DConfigGenerator.pde`. At startup, It automatically imports the configuration descirbed in `build/GraphicCatoms2DConfigGenerator/config.xml`, if this file exists. Otherwise, the configuration is initially empty.

2. Click left on the application panel to add modules. Click right on the application panel to remove modules. See the keyboard shortcut list below to learn all the possibilites offered by GraphicCatoms2DConfigGenerator.

3. Once finished, save your configuration by pressing <kbd>s</kbd>.

Here are the different keyboard shortcuts:

* <kbd>i</kbd>: Go into "initial grid" mode. On click, modules will be added/removed to the initial shape. By default, "initial grid" mode is enabled.
* <kbd>t</kbd>: Go into "target grid" mode. On click, modules will be added/removed to the target shape.
* <kbd>p</kbd>: Save a picture of the current configuration in `build/GraphicCatoms2DConfigGenerator/picture.png`.
* <kbd>s</kbd>: Export the current configuration in `build/GraphicCatoms2DConfigGenerator/config.xml`.
* <kbd>n</kbd>: Print the number of modules in the initial/target grids.
* <kbd>z</kbd>: Go into "border"/"no-border" mode. By default, "border" mode is enable. In this mode, modules are drawn with two borders. The outermost one is green if the module belongs to the initial shape and white otherwise. The inner one is red if the module belongs to the target shape and white otherwise.
* Colors: Added modules will have this color. Moreover, clicking on a module will change its color to this color. By default, added modules are gray.
  - <kbd>w</kbd>: white.
  - <kbd>d</kbd>: black (dark).
  - <kbd>c</kbd>: gray (classic, default color).
  - <kbd>a</kbd>: aqua.
  - <kbd>r</kbd>: red.	
  - <kbd>o</kbd>: orange.
  - <kbd>y</kbd>: yellow.
  - <kbd>a</kbd>: aqua.
  - <kbd>b</kbd>: blue.
  - <kbd>g</kbd>: green.

### Example

Below are screenshots of GraphicCatoms2DConfigGenerator.

![](screenshots/car.png?raw=true)

*Screenshot of GraphicCatoms2DConfigGenerator, in "border" mode, loaded with `example/car.xml`.*

![](screenshots/car-noborder.png?raw=true)

*Screenshot of GraphicCatoms2DConfigGenerator, in "no-border" mode, loaded with `example/car.xml`.*
