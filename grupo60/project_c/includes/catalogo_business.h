#ifndef CATALOGO_BUSINESS_H
#define CATALOGO_BUSINESS_H
#include<glib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>


typedef struct business BUSINESS;

typedef struct cat_bus * C_bus;

C_bus init_business(C_bus bus);

void free_business(C_bus bus);

void rmBus(gpointer business);

void rmKeyBus(gpointer key);

GHashTable * load_business(char * busFile);

void load_catalogo_bus(C_bus bus, char * businesses);

//FUNCOES DE GET PARA BUSINESS
char * getBusinessId (BUSINESS *b) ;

char * getCategories (BUSINESS *b) ;

char * getCity (BUSINESS *b) ;

char * getNameBusiness (BUSINESS *b) ;

char * getState (BUSINESS *b) ;

GHashTable * getCatalogoBus(C_bus cat);


#endif //CATALOGO_BUSINESS_H
