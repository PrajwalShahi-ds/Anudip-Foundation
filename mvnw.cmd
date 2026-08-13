@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------

@echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set DBG_ENABLE=false
set ERROR_CODE=0

@REM Find maven wrapper
if exist "%DIRNAME%\.mvn\wrapper\maven-wrapper.properties" (
    echo Maven wrapper configuration found.
)

echo Starting Maven Build via IntelliJ / System Maven...
if exist "%JAVA_HOME%\bin\java.exe" (
    set "JAVACMD=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVACMD=java"
)

echo Java executable: %JAVACMD%
