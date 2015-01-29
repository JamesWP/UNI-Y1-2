/*
=====================================================================

File:        ex1.c
Description: COMP27112, Lab Exercise 1, skeleton
Authors:     Toby Howard

=====================================================================
*/

#include <GL/glut.h>
#include <math.h>
#include <stdlib.h>

/* Set to 0 or 1 for normal or reversed mouse Y direction */
#define INVERT_MOUSE 0

#define RUN_SPEED  0.15
#define TURN_ANGLE 4.0
#define DEG_TO_RAD 0.017453293

GLdouble lat,     lon;              /* View angles (degrees)    */
GLdouble mlat,    mlon;             /* Mouse look offset angles */   
GLfloat  eyex,    eyey,    eyez;    /* Eye point                */
GLfloat  centerx, centery, centerz; /* Look point               */
GLfloat  upx,     upy,     upz;     /* View up vector           */

GLfloat  ang= 0.0;                  /* Mystical object angle    */
GLint width= 640, height= 480;      /* size of window           */

GLfloat white_light[] =     { 1.0, 1.0, 1.0, 1.0 };
GLfloat light_position0[] = { -5.0, 7.0, -5.0, 0.0 };
GLfloat matSpecular[] =     { 1.0, 1.0, 1.0, 1.0 };
GLfloat matShininess[] =    { 50.0 };
GLfloat matSurface[] =      { 0.8, 0.5, 0.2, 0.1 };
GLfloat matEmissive[] =     { 0.0, 1.0, 0.0, 0.1 };

//////////////////////////////////////////////

float dist (float x, float y, float z) {
// returns distance to origin from point (x,y,z)
return(fabs(sqrt(x*x + y*y + z*z)));
} // dist()

//////////////////////////////////////////////

int within(float x, float y, float z, float inner, float outer) {
// check if (x,y,z) lies between "inner" and "outer"
float d;
d= dist(x,y,z);
if ((d > inner) && (d < outer)) {return(1);}
else {return(0);}
} // within()

//////////////////////////////////////////////

void energy(float ringMin, float ringMax, float r, float g, float b) {
// Draw a ring of energy, comprising a swarm of particles in random positions, but 
// bounded to lie withing ringMin-ringMax, the space between two concentric spheres
int i;
float R= 10.0; // to get rands in range 0-R
float x,y,z;

glDisable(GL_LIGHTING); // don't want lighting on
glEnable(GL_POINT_SMOOTH);
glPointSize(2.0);
glColor3f(r,g,b);
glBegin(GL_POINTS);
for (i= 0; i<10000; i++) {
   // get rand p in range -R/2 < p < +R/2
   x= R* rand() / (((double)RAND_MAX+1)) -R/2.0;
   y= R* rand() / (((double)RAND_MAX+1)) -R/2.0;
   z= 0.0; // to make it a flat ring
   if (within(x,y,z, ringMin, ringMax)) glVertex3f(x,y,z);
}
glEnd();
glEnable(GL_LIGHTING);
} // energy()

//////////////////////////////////////////////

void cylinder(int steps, float height){
// Draws a cylinder with "steps" steps around the axis, and height "height".
// The cylinder is centred on (0,0,0)
float PI= 3.141592654;
float a= 0.0;
float da= 2 * PI / steps;
int i;

glTranslatef(0.0, -height/2.0, 0.0);
for (i= 0; i < steps; i++) {
   glBegin(GL_QUADS);
    glNormal3f(cos(a), 0.0, sin(a));       glVertex3f(cos(a), 0.0, sin(a));
    glNormal3f(cos(a), 0.0, sin(a));       glVertex3f(cos(a), height, sin(a));
    glNormal3f(cos(a+da), 0.0, sin(a+da)); glVertex3f(cos(a+da), height, sin(a+da));
    glNormal3f(cos(a+da), 0.0, sin(a+da)); glVertex3f(cos(a+da), 0.0, sin(a+da));
   glEnd();
   a+= da;
}
} // cylinder()

//////////////////////////////////////////////

void draw_scene(void) {
  // Draws all the elements in the scene
  int x, z;
  int L= 20;
  int j;
  GLfloat matTeapot[] =      { 1.0, 0.0, 0.0, 0.0 };
  
  /* Draw ground */
  glDisable(GL_LIGHTING);
  // the ground quad and the grid lines are co-planar, which would lead to horrible Z-fighting, 
  // so we resort to 2 hacks. First, fiddle with the Z-buffer depth range, using glDepthRange(), 
  // and second, draw the lines 0.01 higher in Y than the ground plane
  glDepthRange (0.1, 1.0);
  glColor3f(0.4, 0.4, 0.4);
  glBegin(GL_QUADS);
  glVertex3f(-L, 0, -L);
  glVertex3f(L, 0, -L);
  glVertex3f(L, 0, L);
  glVertex3f(-L, 0, L);
  glEnd();
  
  glDepthRange (0.0, 0.9); 
  glColor3f(0.2, 0.2, 0.2);
  glLineWidth(1.0);
  glBegin(GL_LINES);
  for (x= -L; x <= L; x++)  {
    glVertex3f((GLfloat) x, 0.01, -L);
    glVertex3f((GLfloat) x, 0.01,  L);
  }
  for (z= -L; z <= L; z++)  {
    glVertex3f(-L, 0.01, (GLfloat) z);
    glVertex3f( L, 0.01, (GLfloat) z);
  }
  glEnd();

  glEnable(GL_LIGHTING);
  /* Draw pillars */
  glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, matSurface);
  for (x= -2; x <= 2; x+=4)
    for (z= -4; z <= 4; z+=4) {
        glPushMatrix();
          glTranslatef((GLfloat) x, 1.7, (GLfloat) z);
          glScalef(0.2, 3.0, 0.2); 
	  cylinder(10, 1.0);
        glPopMatrix();

        glPushMatrix();
          glTranslatef((GLfloat) x, 0.1, (GLfloat) z);
          glScalef(0.5, 0.2, 0.5); 
          glutSolidCube(1.0); /* Base */
        glPopMatrix();

        glPushMatrix();
          glTranslatef((GLfloat) x, 3.3, (GLfloat) z);
          glScalef(0.5, 0.2, 0.5); 
          glutSolidCube(1.0); /* Top */
        glPopMatrix();
    } // for

  //Draw roof base
  glPushMatrix();
    glTranslatef(0.0, 3.5, 0.0);
    glScalef(5.0, 0.2, 9.0); 
    glutSolidCube(1.0);
  glPopMatrix();
  
  // Draw the roof
  glBegin(GL_QUADS);
   glVertex3f(2.5, 3.6, -4.5);
   glVertex3f(2.5, 3.6, 4.5);
   glVertex3f(0.0, 4.5, 4.5);
   glVertex3f(0.0, 4.5, -4.5);
   // other side
   glVertex3f(-2.5, 3.6, -4.5);
   glVertex3f(-2.5, 3.6, 4.5);
   glVertex3f(0.0, 4.5, 4.5);
   glVertex3f(0.0, 4.5, -4.5);
  glEnd();
  
  /* Draw the mystical object */
  glPushMatrix();
    glTranslatef(0.0, 1.5, 0.0);
    glRotatef(ang, 0.0, 1.0, 0.0); 
    glScalef(0.5, 0.5, 0.5); 
    glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, matTeapot);
    glutSolidTeapot(1.0);
    for (j=0; j<2; j++) {
        glPushMatrix();
	glRotatef((j*2+1)*45.0, 1.0, 0.0, 0.0); 
	energy(1.5, 1.7,  1.0,1.0,0.0);
	energy(1.7, 1.9,  1.0,0.0,0.0);
        glPopMatrix();
	}
   glPopMatrix();

} // draw_scene()

//////////////////////////////////////////////

void calculate_lookpoint(void) { /* Given an eyepoint and latitude and longitude angles, will
     compute a look point one unit away */

  /* To be completed */

} // calculate_lookpoint()

//////////////////////////////////////////////

void display(void) {	
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
   glMaterialfv(GL_FRONT, GL_SPECULAR,  matSpecular);
   glMaterialfv(GL_FRONT, GL_SHININESS, matShininess);
   glMaterialfv(GL_FRONT_AND_BACK, GL_DIFFUSE, matSurface);
   glLoadIdentity();
   calculate_lookpoint(); /* Compute the centre of interest   */
   gluLookAt(eyex, eyey, eyez, centerx, centery, centerz, upx, upy, upz);
   glLightfv(GL_LIGHT0, GL_POSITION, light_position0);
   draw_scene();
   glutSwapBuffers();
} // display()

//////////////////////////////////////////////

void spin(void) {
  // Spin the mystical object
  ang= ang + 1.0; if (ang > 360.0) ang= ang - 360.0;

  /* To be completed */
 
  glutPostRedisplay();
} //spin()

//////////////////////////////////////////////

void reshape(int w, int h) {
  glViewport (0, 0, (GLsizei) w, (GLsizei) h); 
  glMatrixMode (GL_PROJECTION);
  glLoadIdentity ();
  gluPerspective (50, (GLfloat) w / (GLfloat) h, 0.1, 80.0);
  glMatrixMode (GL_MODELVIEW);
  width= w;   /* Record the new width and height */
  height= h;
} //reshape()
  
//////////////////////////////////////////////

void mouse_motion(int x, int y) {

  /* To be completed */

} // mouse_motion()

//////////////////////////////////////////////
  
void keyboard(unsigned char key, int x, int y) {
  switch (key) {
    case 27:  /* Escape key */
      exit(0);
      break;
 
      /* To be completed */

   }
} // keyboard()

//////////////////////////////////////////////

void cursor_keys(int key, int x, int y) {
  switch (key) {

      /* To be completed */
  }
} // cursor_keys()
  
//////////////////////////////////////////////

void init(void) {
  glClearColor(0.1, 0.1, 0.8, 0.0);   /* Define background colour */
   
  /* Set initial view parameters */
  eyex=  0.0; /* Set eyepoint at eye height within the scene */
  eyey=  1.7;
  eyez= -10.0;

  upx= 0.0;   /* Set up direction to the +Y axis  */
  upy= 1.0;
  upz= 0.0;

  lat= 0.0;   /* Look horizontally ...  */
  lon= 0.0;   /* ... along the +Z axis  */

  mlat= 0.0;  /* Zero mouse look angles */
  mlon= 0.0;

  /* set up lighting */
  glLightfv(GL_LIGHT0, GL_DIFFUSE, white_light);
  glLightfv(GL_LIGHT0, GL_SPECULAR, white_light);
  glEnable(GL_LIGHTING);
  glEnable(GL_LIGHT0);
  glEnable(GL_DEPTH_TEST);
  glShadeModel(GL_SMOOTH);
  glEnable(GL_NORMALIZE);
} // init()
  
//////////////////////////////////////////////

int main(int argc, char** argv) {
  glutInit (&argc, argv);
  glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH );
  glutInitWindowSize (width, height); 
  glutCreateWindow ("COMP27112 Lab Exercise 1");
  init();
  glutDisplayFunc (display); 
  glutIdleFunc (spin); 
  glutReshapeFunc (reshape);
  glutKeyboardFunc (keyboard);
  glutSpecialFunc (cursor_keys);
  glutPassiveMotionFunc (mouse_motion);
  glutMainLoop ();
  return 0;
}

/* end of ex1.c */
