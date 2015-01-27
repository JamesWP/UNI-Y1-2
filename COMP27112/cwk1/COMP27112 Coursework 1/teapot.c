// COMP27112 Coursework Assignment 1. Toby Howard.

#include <stdlib.h>
#ifdef __APPLE__
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif

float rotX, rotY, rotZ;

////////////////////////////////////////////

void drawAxes (void) {

// Draws X Y and Z axis lines, of length LEN

   float LEN= 10.0;

   glLineWidth(5.0);

   glBegin(GL_LINES);
   glColor3f(1.0,0.0,0.0); // red
       glVertex3f(0.0, 0.0, 0.0);
       glVertex3f(LEN, 0.0, 0.0);

   glColor3f(0.0,1.0,0.0); // green
       glVertex3f(0.0, 0.0, 0.0);
       glVertex3f(0.0, LEN, 0.0);

   glColor3f(0.0,0.0,1.0); // blue
       glVertex3f(0.0, 0.0, 0.0);
       glVertex3f(0.0, 0.0, LEN);
   glEnd();

   glLineWidth(1.0);
}

////////////////////////////////////////////

void Display (void) {

// called every time GLUT wants to refresh the graphics

  glClearColor(0.9, 0.8, 0.0, 1.0); // yellow
  glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
  drawAxes ();

  glColor3f(0.8, 0.1, 0.8); // purple
  glPushMatrix();
    glRotatef(rotZ, 0.0, 0.0, 1.0);
    glRotatef(rotY, 0.0, 1.0, 0.0);
    glRotatef(rotX, 1.0, 0.0, 0.0);
    glutWireTeapot(3.0);
  glPopMatrix();

glutSwapBuffers();

}

////////////////////////////////////////////

void Reshape (int w, int h) {

// called on window creation, and subsequent resizes by the user

    glViewport(0, 0, (GLint)w, (GLint)h);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    gluPerspective (50, (GLfloat) w / (GLfloat) h, 0.1, 80.0);

    gluLookAt(8.0, 4.5, 0.0,  // camera position
              0.0,  0.0,  0.0,    // point to look at
              0.0,  1.0,  0.0);  // "upright" vector
}

////////////////////////////////////////////

void Key (unsigned char key, int x, int y) {

// called when a key-press event happens -- [XxYyZzr] keys

  switch (key) {
      case 'X': rotX-= 10.0; break;
      case 'x':	rotX+= 10.0; break;
      case 'Y':	rotY-= 10.0; break;
      case 'y':	rotY+= 10.0; break;	 
      case 'Z':	rotZ-= 10.0; break;
      case 'z':	rotZ+= 10.0; break;
      case 'r':	rotX= rotY= rotZ = 0.0; break; 

      case  27: exit(0); // '27' is ASCII code for the escape key

    }

    glutPostRedisplay(); // tell GLUT that a call to glutDisplayFunc() is needed

}

////////////////////////////////////////////

int main(int argc, char **argv) {

    rotX= rotY= rotZ= 0.0; // no rotation

    glutInit(&argc, argv);
    glutInitDisplayMode (GLUT_RGB | GLUT_DEPTH | GLUT_DOUBLE);
    glutInitWindowSize (800, 800);
    glutCreateWindow("Coursework Assignment 1: OpenGL");
    glutReshapeFunc(Reshape);
    glutKeyboardFunc(Key);
    glutDisplayFunc(Display);
    glutMainLoop();

    exit (1);
}



////////////////////////////////////////////
