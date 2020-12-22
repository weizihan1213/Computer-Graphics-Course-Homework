@rem  Use the build_environment.cmd file to set the PATH variable
@rem  to point to your Java JDK location.
call build_environment.cmd
@rem
@rem  Because of the way Java packages work, notice the
@rem  difference in how we name the FrameBuffer class
@rem  when compiling it vs. when running it.
@rem
javac  framebuffer\Framebuffer.java
java   framebuffer.FrameBuffer
pause
