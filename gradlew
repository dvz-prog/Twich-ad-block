#!/bin/sh
exec "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" "$@" 2>/dev/null || \
java -jar "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" \
  -cp "$(dirname "$0")/gradle/wrapper/gradle-wrapper.jar" \
  org.gradle.wrapper.GradleWrapperMain "$@"
