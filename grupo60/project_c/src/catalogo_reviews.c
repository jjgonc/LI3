
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "../includes/catalogo_reviews.h"
#include "../includes/view.h"

#define BUFF_SIZE 1000000

struct review {
    char *review_id;
    char *user_id;
    char *business_id;
    float stars;
    int useful;
    int funny;
    int cool;
    char * date;
    char *text;
};


struct cat_reviews {
    GHashTable * catalogo_rev;
};

C_reviews init_reviews(C_reviews revs){
    revs = malloc(sizeof(C_reviews));
    revs -> catalogo_rev = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyRev,rmRev);
    return revs;
}

void free_reviews(C_reviews revs){
    g_hash_table_remove_all (revs->catalogo_rev);
}

void load_catalogo_reviews(C_reviews revs, char * revfile){
    revs->catalogo_rev = load_review(revfile);
}


//Função que dá free a uma chave

void rmKeyRev(gpointer key){

    free((char *)key);

}
//Funçao que dá free a um business

void freeReview(review * rev){
    
    free(rev->review_id);
    free(rev->user_id);
    free(rev->business_id);
    free(rev->date);
    free(rev->text);
    free(rev);

}

//Função que remove um review da HashTable

void rmRev(gpointer revie){

    review * rev = revie ;
    freeReview(rev);

}

//Função que cria o catalogo de review apartir da leitura de um ficheiro
GHashTable * load_review(char * revFile){
    char buffer[BUFF_SIZE];
    review * rev;
    FILE* f = fopen(revFile, "r"); 
    GHashTable * revs  = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyRev,rmRev);
    char * key;

    if (f == NULL){
		printErroAbrirFicheiro(revFile);
        return NULL;
	}
	
    while(fgets(buffer,BUFF_SIZE,f)){
        rev = g_malloc0(sizeof(review));
        rev->review_id = g_strdup(strtok(buffer,";"));
        rev->user_id = g_strdup(strtok(NULL,";"));
        rev->business_id = g_strdup(strtok(NULL,";"));
        rev->stars = atoll(strtok(NULL,";"));
        rev->useful = atoi(strtok(NULL,";"));
        rev->funny = atoi(strtok(NULL,";"));
        rev->cool = atoi(strtok(NULL,";"));
        rev->date = g_strdup(strtok(NULL,";"));
        rev->text = g_strdup(strtok(NULL,";"));        
        key = strdup(rev->review_id);
        g_hash_table_insert(revs, key ,rev);
    }       
	fclose(f);
    
    
    return revs;
}

// Funções para Get :

char * getReviewID (review *rev){
    return rev->review_id;
}

char * getUserID (review *rev){
    return rev->user_id;
}

char * getBusinessID (review *rev){
    return rev->business_id;
}

float getStars (review *rev){
    return rev->stars;
}

int getUseful (review *rev){
    return rev->useful;
}
int getFunny (review *rev){
    return rev->funny;
}

int getCool (review *rev){
    return rev->cool;
}

char * getDate (review *rev){
    return rev->date;
}

char * getText (review *rev){
    return rev->text;
}

GHashTable * getCatalogoReviews (C_reviews revs){
    return revs->catalogo_rev;
}
