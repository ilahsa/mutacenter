#!/usr/bin/env bash
mvn package
mv /git.ifengidc.com/ifeng_adx/mutacenter/target/config $BUILD_ROOT/
mv /git.ifengidc.com/ifeng_adx/mutacenter/target/lib $BUILD_ROOT/
mv /git.ifengidc.com/ifeng_adx/mutacenter/target/mutacenter.jar $BUILD_ROOT/