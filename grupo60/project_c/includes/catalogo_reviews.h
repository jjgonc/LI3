#ifndef CATALOGO_REVIEWS_H
#define CATALOGO_REVIEWS_H
#include<glib.h>



typedef struct review  review;

typedef struct cat_reviews * C_reviews;

C_reviews init_reviews(C_reviews revs);

void free_reviews(C_reviews revs);

void rmKeyRev(gpointer key);

void rmRev(gpointer revie);

GHashTable * load_review(char * revFile);

void load_catalogo_reviews(C_reviews revs, char * revfile);

char * getReviewID (review *rev);

char * getUserID (review *rev);

char * getBusinessID (review *rev);

float getStars (review *rev);

int getUseful (review *rev);

int getFunny (review *rev);

int getCool (review *rev);

char * getDate (review *rev);

char * getText (review *rev);

GHashTable * getCatalogoReviews (C_reviews revs);


#endif // CATALOGO_REVIEWS_H
