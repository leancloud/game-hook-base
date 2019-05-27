#!/bin/bash
BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $BASE_DIR

# generate protobuf codes
protoc3 --proto_path=./proto --java_out=../src/main/java/ ./proto/generic_collection.proto