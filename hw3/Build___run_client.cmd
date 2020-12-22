@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
javac -g -Xlint -Xdiags:verbose  -cp .;renderer_2.jar  %1
java                             -cp .;renderer_2.jar  %~n1
pause
