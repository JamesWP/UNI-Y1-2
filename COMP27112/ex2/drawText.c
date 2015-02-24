//
//  drawText.c
//  grpahics-ex2
//
//  Created by James Peach on 20/02/2015.
//  Copyright (c) 2015 James Peach. All rights reserved.
//


#include "drawText.h"

GLvoid *font_style = GLUT_BITMAP_HELVETICA_12;

void drawText (GLfloat x, GLfloat y, char* text)
{
  glRasterPos2f(x, y);
  // draw each char
  for (int i = 0; text[i] != '\0'; i++)
    glutBitmapCharacter(font_style, text[i]);
}
