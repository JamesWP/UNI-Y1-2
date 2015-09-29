#include <GL/glut.h>

float r= 0.0;
int hidden= 1, flat= 1;

void init(void) {
   glClearColor (0.0, 0.0, 0.0, 0.0);
   glEnable (GL_LIGHTING);
   glEnable (GL_LIGHT0);
}

void spin (void) {
   r+= 1.0;
   glutPostRedisplay();
}

void display(void) {
   glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
   if (hidden) glEnable(GL_DEPTH_TEST);
   else glDisable(GL_DEPTH_TEST);
   if (flat) glShadeModel (GL_FLAT);
   else glShadeModel (GL_SMOOTH);
   glLoadIdentity ();
   gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
     
   glColor3f (1.0, 0.0, 0.0); 
   glutSolidCube(1.0);          /* Red cube */
   glRotatef(r*2.0, 0, 1, 0);   /* Orbit angle */
   glTranslatef(0.0, 0.0, 1.0); /* Orbit radius */
   glRotatef(r, 1, 0, 0);       /* Tumble in x,y,z */
   glRotatef(r, 0, 1, 0);
   glRotatef(r, 0, 0, 1);
   glColor3f (0.0, 1.0, 0.0);
   glutSolidSphere(0.5, 20, 15); /* Green sphere */
   glutSwapBuffers();
}

void reshape (int w, int h) {
   glViewport (0, 0, (GLsizei) w, (GLsizei) h); 
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   gluPerspective (60, (GLfloat) w / (GLfloat) h, 1.0, 100.0);
   glMatrixMode (GL_MODELVIEW);
}
 void keyboard(unsigned char key, int x, int y) {
  if (key == 27) { exit (0); } /* escape key */
  if (key == 'h') hidden= !hidden;
  if (key == 's') flat= !flat;
}

int main(int argc, char **argv) {
   glutInit(&argc, argv);
   glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
   glutInitWindowSize (500, 500); 
   glutInitWindowPosition (100, 100);
   glutCreateWindow ("ex9");
   init ();
   glutDisplayFunc (display); 
   glutReshapeFunc (reshape);
   glutKeyboardFunc (keyboard);
   glutIdleFunc (spin);
   glutMainLoop ();
   return 0;
}
