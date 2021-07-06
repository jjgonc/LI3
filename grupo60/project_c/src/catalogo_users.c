#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "../includes/catalogo_users.h"
#include "../includes/view.h"


#define BUFF_SIZE 1000000

struct users {
    char *user_id;
    char *name;
    char *friends;
};


struct cat_users {
    GHashTable * catalogo_us;
};

C_users init_users(C_users us){
    us = malloc(sizeof(C_users));
    us -> catalogo_us = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyUser,rmUser);
    return us;
}

void free_users(C_users us){
    g_hash_table_remove_all (us->catalogo_us);
}

void load_catalogo_users(C_users us, char * users){
    us->catalogo_us = load_users(users);
}

//Funçao que dá free a um user

void freeUser(user * us){
        
    free(us->user_id);
    free(us->name);
    free(us->friends);
    free(us);

}

//Função que remove um user da hashTable
void rmUser(gpointer usr){

    user * us = usr;
    freeUser(us);

}

//Função que dá free a uma chave

void rmKeyUser(gpointer key){

    free((char *)key);

}


//Função que cria o catalogo de users apartir da leitura de um ficheiro

GHashTable * load_users(char * usFile){
    char buffer[BUFF_SIZE];
    user * us;
    FILE* f = fopen(usFile, "r"); 
    GHashTable * users  = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyUser,rmUser);
    char * key;

    if (f == NULL){
		printErroAbrirFicheiro(usFile);
        return NULL;
	}

    while(fgets(buffer,BUFF_SIZE,f)){
        us = malloc(sizeof(user));
        us->user_id = strdup(strtok(buffer,";"));
        us->name = strdup(strtok(NULL,";"));
        us->friends = strdup(strtok(NULL,";"));
        key = strdup(us -> user_id);
        g_hash_table_insert(users, key ,us);
    }       
	fclose(f);
    
    
    return users;
}

// Funções para get :

char * getUserId(user * us){
    return us -> user_id;
}

char * getName(user * us){
    return us->name;
}

char * getFriends(user * us){
    return us-> friends;
}

void adiciona_user (C_users catalogo, user * user) {
    g_hash_table_insert (catalogo->catalogo_us, user -> user_id, user);
}

GHashTable * get_catalogoUsers (C_users cUs) {
    return (cUs->catalogo_us);
}