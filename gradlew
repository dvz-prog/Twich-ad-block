#!/bin/sh
GRADLE_VERSION="8.1.1"
GRADLE_HOME="$HOME/.gradle/wrapper/dists/gradle-${GRADLE_VERSION}-bin"
GRADLE_ZIP="gradle-${GRADLE_VERSION}-bin.zip"
GRADLE_URL="https://services.gradle.org/distributions/${GRADLE_ZIP}"

if [ ! -f "$GRADLE_HOME/gradle-${GRADLE_VERSION}/bin/gradle" ]; then
    mkdir -p "$GRADLE_HOME"
    echo "Baixando Gradle ${GRADLE_VERSION}..."
    curl -L "$GRADLE_URL" -o "/tmp/${GRADLE_ZIP}"
    unzip -q "/tmp/${GRADLE_ZIP}" -d "$GRADLE_HOME"
fi

exec "$GRADLE_HOME/gradle-${GRADLE_VERSION}/bin/gradle" "$@"
