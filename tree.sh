#!/usr/bin/env bash
tree -P 'README.md|*.java|.properties' -I 'gradle|build|static|target|http-curl'
