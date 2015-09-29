#include <GL/glut.h>

void display (void) { 
/* Called when OpenGL needs to update the display */
   glClearColor(0.9,0.9,0.9,0.0); /* Set grey backbround */
   glClear (GL_COLOR_BUFFER_BIT); /* Clear the window */
   glLoadIdentity ();
   gluLookAt (0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
   glColor3f(1.0,0.0,0.0);
   glBegin (GL_LINE_LOOP); /* Draw a triangle */
      glVertex3f(-0.3, -0.3, 0.0);
      glVertex3f(0.0, 0.3, 0.0);
      glVertex3f(0.3, -0.3, 0.0);
   glEnd();
   glFlush(); /* Force update of screen */
}

void keyboard (unsigned char key, int x, int y) {
/* Called when a key is pressed */
  if (key == 27) exit (0); /* 27 is the Escape key */
} 

void reshape (int width, int height)
{ /* Called when the window is created, moved or resized */
   glViewport (0, 0, (GLsizei) width, (GLsizei) height); 
   glMatrixMode (GL_PROJECTION); /* Select the projection matrix */
   glLoadIdentity ();            /* Initialise it */
   glOrtho(-1.0,1.0, -1.0,1.0, -1.0,1.0); /* The unit cube */
   glMatrixMode (GL_MODELVIEW);  /* Select the modelview matrix */
}
 int main(int argc, char **argv) {
  glutInit (&argc, argv);      /* Initialise OpenGL */
  glutInitWindowSize (500, 500);     /* Set the window size */
  glutInitWindowPosition (100, 100); /* Set the window position */
  glutCreateWindow ("ex4");    /* Create the window */
  glutDisplayFunc (display);   /* Register the "display" function */
  glutReshapeFunc (reshape);   /* Register the "reshape" function */
  glutKeyboardFunc (keyboard); /* Register the "keyboard" function */
  glutMainLoop ();             /* Enter the OpenGL main loop */
  return 0;
}
