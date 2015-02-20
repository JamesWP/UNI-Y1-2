//
//  spaceship.h
//  grpahics-ex2
//
//  Created by James Peach on 19/02/2015.
//  Copyright (c) 2015 James Peach. All rights reserved.
//

#ifndef __grpahics_ex2__spaceship__
#define __grpahics_ex2__spaceship__

#include <stdio.h>

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif


struct vector3 { GLfloat x,y,z; };
typedef struct spaceship_s {
  struct vector3 pos;      // position vector
  struct vector3 forward;  // forward vector
  struct vector3 up;       // up vector

  struct vector3 velocity;
} Spaceship;
typedef enum viewdirection_e {UP,DOWN,LEFT,RIGHT,FORWARD,BACKWARD} ViewDirection;

Spaceship sp;

/**
 * sets the camera's viewpoint from the spaceship
 */
void setCameraSpaceship();

void initialiseSpaceship();

void tickSpaceship(long millisScinceLastUpdate);

void updateViewSpaceship(ViewDirection direction,long millisScinceUpdate);

#endif /* defined(__grpahics_ex2__spaceship__) */
