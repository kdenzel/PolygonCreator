# PolygonCreator
A simple tool for the libgdx Polygon class. You can draw a polygon around a sprite and export it as a float array.

[Download .jar file](https://github.com/kdenzel/PolygonCreator/releases/download/v1.1/PolygonCreator.jar)

![No image found](https://imagr.eu/up/8Mw5r_screenshot.png)

## For gradle
Step 1. Add the JitPack repository to your build file
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Step 2. Add the dependency
```
dependencies {
        implementation 'com.github.kdenzel:PolygonCreator:v1.1'
}
```

## For maven
Step 1. Add the JitPack repository to your build file
```
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Step 2. Add the dependency

```
<dependency>
    <groupId>com.github.kdenzel</groupId>
    <artifactId>PolygonCreator</artifactId>
    <version>v1.1</version>
</dependency>
```
