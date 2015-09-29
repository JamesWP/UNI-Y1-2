#include <GL/glut.h>

GLfloat angle= 0.0;

void spin (void) {
   angle+= 1.0;
   glutPostRedisplay();
}

void display(void) {
   glClearColor(0.9,0.9,0.9,0.0); /* Set grey backbround */
   glClear (GL_COLOR_BUFFER_BIT);
   glLoadIdentity ();
   gluLookAt (0.0, 0.0, 5.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
   glRotatef(angle, 1, 0, 0);
   glRotatef(angle, 0, 1, 0);
   glRotatef(angle, 0, 0, 1);
   glColor3f(1.0,0.0,0.0);
   glutWireCube(2.0);
   glFlush();            /* Force update of screen */
}

void reshape (int w, int h) {
   glViewport (0, 0, (GLsizei)w, (GLsizei)h); 
   glMatrixMode (GL_PROJECTION);
   glLoadIdentity ();
   gluPerspective (60, (GLfloat) w / (GLfloat) h, 1.0, 100.0);
   glMatrixMode (GL_MODELVIEW);
}

void keyboard(unsigned char key, int x, int y) {
  if (key == 27)  exit (0);                 /* escape key */
} 

int main(int argc, char **argv) {
   glutInit(&argc, argv);
   glutInitWindowSize (500, 500); 
   glutInitWindowPosition (100, 100);
   glutCreateWindow ("ex6: A rotating cube.");
   glutDisplayFunc(display); 
   glutReshapeFunc(reshape);
   glutKeyboardFunc(keyboard);
   glutIdleFunc(spin);          /* Register the "idle" function */
   glutMainLoop();
   return 0;
}
