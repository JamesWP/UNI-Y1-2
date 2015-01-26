INCDIR = /usr/include
LIBDIR = /usr/lib

XLIBS = -L/usr/X11/lib -L/usr/X11R6/lib -lX11 -lXext -lXmu -lXt -lXi
GL_LIBS = -L$(LIBDIR) -lglut -lGLU -lGL -lm $(XLIBS)
LIB_DEP = $(LIBDIR)/$(GL_LIB) $(LIBDIR)/$(GLU_LIB) $(LIBDIR)/$(GLUT_LIB)

CC = gcc

CFLAGS = -I${INCDIR} -I/usr/X11R6/include -O3 -fomit-frame-pointer -march=i486 -Wall -pipe -DFX -DXMESA
thegears:	thegears.o
	${CC} ${CFLAGS}  thegears.o -o thegears ${GL_LIBS}
