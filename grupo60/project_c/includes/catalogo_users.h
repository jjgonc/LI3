#ifndef CATALOGO_USERS_H
#define CATALOGO_USERS_H

#include<glib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>


typedef struct users  user;

typedef struct cat_users * C_users;

C_users init_users(C_users us);

void free_users(C_users us);

void rmKeyUser(gpointer key);

void rmUser(gpointer usr);

GHashTable * load_users(char * usFile);

void load_catalogo_users(C_users us, char * users);

char * getUserId(user * us);

char * getName(user * us);

char * getFriends(user * us);

GHashTable * get_catalogoUsers (C_users cUs) ; 

#endif //CATALOGO_USERS_H
