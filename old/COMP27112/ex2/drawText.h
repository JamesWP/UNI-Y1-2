//
//  drawText.h
//  grpahics-ex2
//
//  Created by James Peach on 20/02/2015.
//  Copyright (c) 2015 James Peach. All rights reserved.
//

#ifndef grpahics_ex2_drawText_h
#define grpahics_ex2_drawText_h

#include <stdlib.h>
#include <stdarg.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

void drawText (GLfloat x, GLfloat y,char* text);

#endif
