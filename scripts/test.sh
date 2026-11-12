#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p out
find src/main/java src/test/java -name '*.java' -print > out/all-sources.txt
javac -encoding UTF-8 -d out @out/all-sources.txt
java -cp out kz.restordering.RestaurantTests
