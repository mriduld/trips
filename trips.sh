#!/usr/bin/env bash

set -eu
set -o pipefail

TRIPS_HOST=${TRIPS_HOST:-localhost}
TRIPS_PORT=${TRIPS_PORT:-8081}
TRIPS_VERSION=${TRIPS_VERSION:-"v1"}
JAR=${JAR:-"cli/target/trip-cli-0.1.0.jar"}

java -jar $JAR "$@" --trip-host $TRIPS_HOST --trip-port $TRIPS_PORT --trip-version $TRIPS_VERSION

exit $?