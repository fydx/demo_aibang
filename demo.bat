@echo off
setlocal enableextensions enabledelayedexpansion
set out_dir=bin
set cp=%out_dir%
for %%f in (lib/*.jar) do set cp=!cp!;lib/%%f
java -classpath "%cp%" "com.aibang.open.client.demo.JavaDemo" %*