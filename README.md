# Asdfgh

A [libgdx](https://libgdx.com/) game written in [Scala](https://www.scala-lang.org/),
the premier programming language for contemporary mobile and game development.

```
  ./gradlew desktop:clean
  ./gradlew desktop:dist
  # remove the signature or it won't run
  zip ./desktop/build/libs/desktop-1.3.jar -d META-INF/SIGNINGC.SF
  java -XstartOnFirstThread -jar ./desktop/build/libs/desktop-1.3.jar
```
