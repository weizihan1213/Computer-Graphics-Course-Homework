pushd  C:\ImageMagick-7.0.8-64-portable-Q16-x64
set PATH=%CD%;%PATH%
popd
convert.exe  -delay 1x30  -loop 0  PPM_*.ppm  animation1.gif
pause
