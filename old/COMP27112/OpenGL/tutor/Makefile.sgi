##!smake

include /usr/include/make/commondefs

TARGETS = transformation projection lightposition texture lightmaterial fog
LLDLIBS = -lglut -lGLU -lGL -lXmu -lXext -lX11 -lm
LCFLAGS = -fullwarn

default		: $(TARGETS)

include $(COMMONRULES)

$(TARGETS)	: $$@.o glm.o sgi.o
	$(CCF) -o $@ $@.o glm.o sgi.o $(LDFLAGS)
