#include <GL/glut.h>

void display (void) { 
/* Called when OpenGL needs to update the display */
    glClearColor(0.9,0.9,0.9,0.0); /* Set grey backbround */
    glClear (GL_COLOR_BUFFER_BIT); /* Clear the window */
    glFlush();                     /* Force update of screen */
}

void keyboard (unsigned char key, int x, int y) {
/* Called when a key is pressed */
  if (key == 27) exit (0);    /* 27 is the Escape key */
} 

int main(int argc, char **argv) {
  glutInit (&argc, argv);      /* Initialise OpenGL */
  glutInitWindowSize (500, 500);     /* Set the window size */
  glutInitWindowPosition (100, 100); /* Set the window position */
  glutCreateWindow ("ex3");    /* Create the window */
  glutDisplayFunc (display);   /* Register the "display" function */
  glutKeyboardFunc (keyboard); /* Register the "keyboard" function */
  glutMainLoop ();             /* Enter the OpenGL main loop */
  return 0;
}
