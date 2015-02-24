//
//  spaceship.c
//  grpahics-ex2
//
//  Created by James Peach on 19/02/2015.
//  Copyright (c) 2015 James Peach. All rights reserved.
//

#include "spaceship.h"

typedef enum bool_e {true,false} Bool;

void thrust(Bool, long);

static inline void vecAdd(struct vector3 *vec, struct vector3 this){
  vec->x += this.x;
  vec->y += this.y;
  vec->z += this.z;
}

static inline struct vector3 vecScale(struct vector3 vec,float factor){
  return (struct vector3) {vec.x * factor, vec.y * factor, vec.z * factor};
}

void initialiseSpaceship(){
  sp.forward  = (struct vector3) {0.0,0.0,-100.0};
  sp.pos      = (struct vector3) {0.0,0.0, 90000000.0};
  sp.up       = (struct vector3) {0.0,1.0, 0.0};

  sp.velocity = (struct vector3) {0.0,0.0, 0.0};
}

void setCameraSpaceship(){
  gluLookAt(sp.pos.x, sp.pos.y, sp.pos.z, sp.pos.x + sp.forward.x, sp.pos.y + sp.forward.y, sp.pos.z + sp.forward.z, sp.up.x, sp.up.y, sp.up.z);
}

void tickSpaceship(long millisScinceLastUpdate){
  vecAdd(&sp.pos,sp.velocity);
}

void updateViewSpaceship(ViewDirection direction,long millisScinceUpdate){
  switch (direction){
    case FORWARD:  thrust(true,  millisScinceUpdate); return;
    case BACKWARD: thrust(false, millisScinceUpdate); return;
    case LEFT:
      break;
    case RIGHT:
      break;
    case UP:
      vecAdd(&sp.forward, vecScale(sp.up, 0.001 * millisScinceUpdate));
//      vecAdd(&sp.up, vecScale(sp.forward, -0.001 * millisScinceUpdate));
      break;
    case DOWN:
      vecAdd(&sp.forward, vecScale(sp.up, -0.001 * millisScinceUpdate));
//      vecAdd(&sp.up, vecScale(sp.forward, 0.001 * millisScinceUpdate));
      break;
  }
}

void thrust(Bool forward, long millisScinceUpdate){
  struct vector3 acc = sp.forward;
  vecAdd(&sp.velocity,vecScale(acc,(float) (forward==true?millisScinceUpdate:-millisScinceUpdate)));
}