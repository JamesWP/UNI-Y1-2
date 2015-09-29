/*
 ==========================================================================
 File:        ex2.c (skeleton)
 Authors:     Toby Howard
 ==========================================================================
 */

/* The following ratios are not to scale: */
/* Moon orbit : planet orbit */
/* Orbit radius : body radius */
/* Sun radius : planet radius */

#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>

#include "drawText.h"

#define MAX_BODIES 20
#define TOP_VIEW 1
#define ECLIPTIC_VIEW 2
#define SHIP_VIEW 3
#define EARTH_VIEW 4
#define PI 3.14159
#define TWOPI 6.28318
#define DEG_TO_RAD 0.017453293
#define ORBIT_POLY_SIDES 40
#define TIME_STEP 0.5   /* days per frame */
#define EARTH 3
#define PARENT(n) (bodies[bodies[n].orbits_body])

typedef struct {
  char    name[20];       /* name */
  GLfloat r, g, b;        /* colour */
  GLfloat orbital_radius; /* distance to parent body (km) */
  GLfloat orbital_tilt;   /* angle of orbit wrt ecliptic (deg) */
  GLfloat orbital_period; /* time taken to orbit (days) */
  GLfloat radius;         /* radius of body (km) */
  GLfloat axis_tilt;      /* tilt of axis wrt body's orbital plane (deg) */
  GLfloat rot_period;     /* body's period of rotation (days) */
  GLint   orbits_body;    /* identifier of parent body */
  GLfloat spin;           /* current spin value (deg) */
  GLfloat orbit;          /* current orbit value (deg) */
} body;

body  bodies[MAX_BODIES];
int   numBodies, current_view, draw_labels, draw_orbits, draw_starfield;


/*****************************/

unsigned long long lastUpdateMillis;
unsigned long long getCurTime();

GLfloat lat,lon;
GLfloat posx,posy,posz;

typedef struct star_s{
  GLfloat x,y,z;
} Star;
#define NUM_STARS 100000

Star stars[NUM_STARS];
void initStars(void)
{
  for(int i=0;i<NUM_STARS;i++){
    double r = random()*100+1000;
    double lat = random()*TWOPI - PI;
    double lon = random()*TWOPI;
    stars[i].x = cosf(lat) * sinf(lon) * r;
    stars[i].y = sinf(lat) * r;
    stars[i].z = cosf(lat) * cosf(lon) * r;
  }
}

void drawStarfield (void)
{
  glPointSize(1);
//  glBegin(GL_LINE_LOOP);
  glBegin(GL_POINTS);
  glColor3f(1.0, 1.0, 1.0);
  for(int i=0;i<NUM_STARS;i++){
    glVertex3f(stars[i].x, stars[i].y, stars[i].z);
  }
  glEnd();
}

float myRand (void)
{
  return (float) rand() / RAND_MAX;
}

/*****************************/

void readSystem(void)
{
  /* reads in the description of the solar system */

  FILE *f;
  int i;

  f= fopen("sys", "r");
  if (f == NULL) {
    printf("ex2.c: Couldn't open the datafile 'sys'\n");
    printf("To get this file, use the following command:\n");
    printf("  cp /opt/info/courses/COMP27112/ex2/sys .\n");
    exit(0);
  }
  fscanf(f, "%d", &numBodies);
  for (i= 0; i < numBodies; i++)
  {
    fscanf(f, "%s %f %f %f %f %f %f %f %f %f %d",
           bodies[i].name,
           &bodies[i].r, &bodies[i].g, &bodies[i].b,
           &bodies[i].orbital_radius,
           &bodies[i].orbital_tilt,
           &bodies[i].orbital_period,
           &bodies[i].radius,
           &bodies[i].axis_tilt,
           &bodies[i].rot_period,
           &bodies[i].orbits_body);

    /* Initialise the body's state */
    bodies[i].spin= 0.0;
    bodies[i].orbit= myRand() * 360.0; /* Start each body's orbit at a
                                        random angle */
    bodies[i].radius*= 1000.0; /* Magnify the radii to make them visible */
  }
  fclose(f);
}

#define MAPRANGE(inputstart,inputend,outputstart,outputend,input) (outputstart + ((outputend - outputstart) / (inputend - inputstart)) * (input - inputstart))
#define MIN(a,b) (a<b?a:b)
#define MAX(a,b) (a>b?a:b)
#define CLAMP(min,max,val) (MIN(MAX(val,min),max))


void shipLookAt(){
  float lonRad = (lon) * DEG_TO_RAD;
  float latRad = (lat) * DEG_TO_RAD;

  GLfloat centery,centerx,centerz;
  //latitude -> centery

  centery = sinf(latRad)*1000000 + posy;
  centerz = cosf(lonRad)*cosf(latRad)*1000000 + posz;
  centerx = sinf(lonRad)*cosf(latRad)*1000000 + posx;

  gluLookAt(posx, posy, posz, centerx, centery, centerz, 0, 1.0, 0);
}

/*****************************/


void setView (void) {
  glMatrixMode(GL_MODELVIEW);
  glLoadIdentity();
  switch (current_view) {
    case TOP_VIEW:
      gluLookAt(0, 600000000.0, 0, 0, 0, 0, 0, 0, -1.0);
      break;
    case ECLIPTIC_VIEW:
      gluLookAt(600000000.0, 0, 0, 0, 0, 0, 0, 1.0, 0);
      break;
    case SHIP_VIEW:
      shipLookAt();
      break;
    case EARTH_VIEW:
      gluLookAt(0, 50000000, 100000000, 0, 0, 0, 0, 1.0, 0);

      glTranslatef(bodies[EARTH].orbital_radius * sin((bodies[EARTH].orbit+180)*DEG_TO_RAD), 0.0, bodies[EARTH].orbital_radius * cos((bodies[EARTH].orbit+180)*DEG_TO_RAD));

      // orbit tilt
      glRotatef(bodies[EARTH].orbital_tilt, 0, 0, -1.0);



      break;
  }
}

/*****************************/

void menu (int menuentry) {
  switch (menuentry) {
    case 1: current_view= TOP_VIEW;
      break;
    case 2: current_view= ECLIPTIC_VIEW;
      break;
    case 3:
      current_view= SHIP_VIEW;

      lat = lon = 0;
      posx = posy = posz = 0;

      break;
    case 4: current_view= EARTH_VIEW;
      break;
    case 5: draw_labels= !draw_labels;
      break;
    case 6: draw_orbits= !draw_orbits;
      break;
    case 7: draw_starfield= !draw_starfield;
      break;
    case 8: exit(0);
  }
}

/*****************************/

void init(void)
{
  /* Define background colour */
  glClearColor(0.0, 0.0, 0.0, 0.0);

  glutCreateMenu (menu);
  glutAddMenuEntry ("Top view", 1);
  glutAddMenuEntry ("Ecliptic view", 2);
  glutAddMenuEntry ("Spaceship view", 3);
  glutAddMenuEntry ("Earth view", 4);
  glutAddMenuEntry ("", 999);
  glutAddMenuEntry ("Toggle labels", 5);
  glutAddMenuEntry ("Toggle orbits", 6);
  glutAddMenuEntry ("Toggle starfield", 7);
  glutAddMenuEntry ("", 999);
  glutAddMenuEntry ("Quit", 8);
  glutAttachMenu (GLUT_RIGHT_BUTTON);

  glEnable(GL_DEPTH_TEST);

  current_view= TOP_VIEW;
  draw_labels= 1;
  draw_orbits= 1;
  draw_starfield= 1;

  initStars();
  lastUpdateMillis = getCurTime();

  //glEnable(GL_BLEND);
  //glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
}

/*****************************/

unsigned long long getCurTime(){
  struct timeval tv;

  gettimeofday(&tv, NULL);

  unsigned long long millisecondsSinceEpoch =
  (unsigned long long)(tv.tv_sec) * 1000 +
  (unsigned long long)(tv.tv_usec) / 1000;
  return millisecondsSinceEpoch;
}

void animate(void)
{
  unsigned long long newTime = getCurTime();
//  unsigned long long delta = newTime - lastUpdateMillis;

  int i;

  for (i= 0; i < numBodies; i++)  {
    bodies[i].spin += 360.0 * TIME_STEP / bodies[i].rot_period;
    bodies[i].orbit += 360.0 * TIME_STEP / bodies[i].orbital_period;
  }
  glutPostRedisplay();

  lastUpdateMillis = newTime;
}

/*****************************/

void setBodyColor(int n){
  glColor3f(bodies[n].r, bodies[n].g, bodies[n].b);
}

#define TOTAL_POINTS 100
void drawOrbit(float radius){
  glBegin(GL_LINE_LOOP);
  float x,z,delta,angle;
  delta = TWOPI / (float)TOTAL_POINTS;
  angle = 0.0;

  for(int i=0;i<TOTAL_POINTS;i++){
    x = sinf(angle) * radius;
    z = cosf(angle) * radius;
    glVertex3f(x, 0, z);
    angle += delta;
  }

  glEnd();
}


void drawBody(int n)
{
  body b = bodies[n];

  setBodyColor(n);

  glPushMatrix();
  {
    if(n!=0){
      // orbit tilt
      glRotatef(b.orbital_tilt, 0, 0, 1.0);

      drawOrbit(b.orbital_radius);

      glTranslatef(b.orbital_radius * sin(b.orbit*DEG_TO_RAD), 0.0, b.orbital_radius * cos(b.orbit*DEG_TO_RAD));

      if(draw_labels)
        drawText(b.radius,b.radius,&b.name[0]);

      // axit tilt
      glRotatef(b.axis_tilt, 0, 0, -1.0);

      // draw axis
      glLineWidth(3.0);
      glColor3f(1.0, 1.0, 1.0);
      glBegin(GL_LINES);
      glVertex3f(0, b.radius*4, 0);
      glVertex3f(0, -b.radius*4, 0);
      glEnd();
    }

    setBodyColor(n);

    glPushMatrix();
    {
      // spin
      glRotatef(b.spin, 0, 1.0, 0);
      glRotatef(90.0, 1.0, 0, 0);

      glLineWidth(1.0);
      glutWireSphere(b.radius, 15, 15);
      //    glutSolidSphere(b.radius, 10, 10);
    }
    glPopMatrix();

    for(int i=1;i<numBodies;i++){
      if(bodies[i].orbits_body==n){
        drawBody(i);
      }
    }
  }
  glPopMatrix();
}

/*****************************/

void drawAxis(void){
  glBegin(GL_LINES);
  {
    glColor3f(1.0, 0, 0);
    glVertex3f(0.0    , 0.0, 0.0);
    glVertex3f(100000000.0, 0.0, 0.0);

    glColor3f(0, 1.0, 0);
    glVertex3f(0.0, 0.0    , 0.0);
    glVertex3f(0.0, 100000000.0, 0.0);

    glColor3f(0, 0, 1.0);
    glVertex3f(0.0, 0.0, 0.0    );
    glVertex3f(0.0, 0.0, 100000000.0);
  }
  glEnd();
}

void display(void)
{

  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

  /* set the camera */
  setView();

  drawAxis();

  if (draw_starfield)
    drawStarfield();

  drawBody(0);

  glutSwapBuffers();
}

/*****************************/
int width,height;
void reshape(int w, int h)
{
  glViewport(0, 0, (GLsizei) w, (GLsizei) h);
  glMatrixMode(GL_PROJECTION);
  glLoadIdentity();
  gluPerspective (48.0, (GLfloat) w/(GLfloat) h, 100.0, 800000000.0);
  width = w;
  height = h;
}

#define FLY_SPEED 500000
void calculate_movement(float relativeAngle) {
  /* Given a relative angle to the curent facing angle, move the players eyez and eyex */
  float lonRad = (lon+relativeAngle) * DEG_TO_RAD;
  posz += cosf(lonRad)*FLY_SPEED;
  posx += sinf(lonRad)*FLY_SPEED;
}

/*****************************/

void keyboard(unsigned char key, int x, int y)
{
  switch (key)
  {
    case 'w':
      calculate_movement(0);
      break;
    case 's':
      calculate_movement(180);
      break;
    case 'a':
      draw_starfield = 1 - draw_starfield;
      break;
    case 27:  /* Escape key */
      exit(0);
  }
}
void keyboardSpecial(int key,int x,int y){
  switch (key){

  }
}
/*****************************/

void mouse_motion(int x, int y) {

  /* To be completed */
  float xperc = ((float)x/width)*2.0 -1.0;
  float yperc = ((float)y/height)*2.0 -1.0;
  lon = xperc * 50.0;
  lat = yperc * 50.0;

} // mouse_motion()


int main(int argc, char** argv)
{
  glutInit (&argc, argv);
  glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB);
  glutCreateWindow ("COMP27112 Exercise 2");
 // glutFullScreen();
  init ();
  glutDisplayFunc (display);
  glutReshapeFunc (reshape);
  glutKeyboardFunc (keyboard);
  glutPassiveMotionFunc (mouse_motion);
  glutSpecialFunc(keyboardSpecial);
  glutIdleFunc (animate);
  readSystem();
  glutMainLoop ();
  return 0;
}
/* end of ex2.c */
