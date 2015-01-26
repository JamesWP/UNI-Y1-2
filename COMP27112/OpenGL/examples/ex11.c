#ifdef MACOSX
#include <GLUT/glut.h>
#else
#include <GL/glut.h>
#endif
#include "bitmap.c"

GLfloat white_light[]=     { 1.0, 1.0, 1.0, 1.0 };
GLfloat light_position0[]= { 5.0, 5.0, 5.0, 0.0 };
GLfloat matSpecular[]=     { 1.0, 1.0, 1.0, 1.0 };
GLfloat matShininess[]=    { 50.0 };
GLfloat matSurface[]=      { 0.0, 1.0, 0.0, 0.0 };

BITMAPINFO *TexInfo; // Texture bitmap information
GLubyte    *TexBits; // Texture bitmap pixel bits
int texName;

void initialise(void) {
   TexBits = LoadDIBitmap("coyote.bmp", &TexInfo);
   glGenTextures(1, &texName);
   glBindTexture(GL_TEXTURE_2D, texName);

   glLightfv(GL_LIGHT0, GL_POSITION, light_position0);
   glLightfv(GL_LIGHT0, GL_DIFFUSE, white_light);
   glLightfv(GL_LIGHT0, GL_SPECULAR, white_light);
   glEnable(GL_LIGHTING);
   glEnable(GL_LIGHT0);
   glEnable(GL_DEPTH_TEST);
   glShadeModel(GL_SMOOTH);
}

void display(void) {  
  glClearColor(0.9, 0.9, 0.9, 0.0);
   glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

   // define material properties
   glMaterialfv(GL_FRONT, GL_SPECULAR,  matSpecular);
   glMaterialfv(GL_FRONT, GL_SHININESS, matShininess);
   glMaterialfv(GL_FRONT, GL_AMBIENT,   matSurface);

   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
   glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
   glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, TexInfo->bmiHeader.biWidth,
                TexInfo->bmiHeader.biHeight, 0, GL_RGB,
                GL_UNSIGNED_BYTE, TexBits);
   glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_REPLACE);
   glEnable(GL_TEXTURE_2D);

   float sz= 1.2;
   glBegin(GL_QUADS);
   glTexCoord2f(0.0, 1.0); glVertex3f(-sz,-sz,0.0);
   glTexCoord2f(1.0, 1.0); glVertex3f(sz,-sz,0.0);
   glTexCoord2f(1.0, 0.0); glVertex3f(sz,sz,0.0);
   glTexCoord2f(0.0, 0.0); glVertex3f(-sz,sz,0.0);
   glEnd();

   glutSwapBuffers();
}

void reshape(int w, int h) {
   glViewport(0, 0,(GLsizei) w,(GLsizei) h);
   glMatrixMode(GL_PROJECTION);
   glLoadIdentity();
   gluPerspective(17.0, (GLfloat)w/(GLfloat)h, 1.0, 20.0);
   glMatrixMode(GL_MODELVIEW);
   glLoadIdentity();
   gluLookAt(0.0,0.0,10.0, 0.0,0.0,0.0, 0.0,1.0,0.0);
}

int main(int argc, char** argv) {
   glutInit(&argc, argv);
   glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
   glutInitWindowSize(800, 800); 
   glutCreateWindow("ex11 - Textured quad");
   glutDisplayFunc(display); 
   glutReshapeFunc(reshape);
   initialise();
   glutMainLoop();
   return 0;
}
