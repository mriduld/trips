#!/usr/bin/env bash

set -eu

TRIPS_HOST=${TRIPS_HOST:-localhost}
TRIPS_PORT=${TRIPS_PORT:-8081}
TRIPS_VERSION=${TRIPS_VERSION:-"v1"}
BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
JAR=${JAR:-"${BIN_DIR}/cli/target/trip-cli-0.1.0.jar"}

java -jar $JAR "$@" --trip-host $TRIPS_HOST --trip-port $TRIPS_PORT --trip-version $TRIPS_VERSION

exit $?