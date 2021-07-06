#include "../includes/catalogo_business.h"
#include "../includes/view.h"

#define BUFF_SIZE 1000000

struct business {
    char *business_id;
    char *name;
    char *city;
    char *state;
    char *categories;
};


struct cat_bus {
    GHashTable * catalogo_bus;
};

C_bus init_business(C_bus bus){
    bus = malloc(sizeof(C_bus));
    bus -> catalogo_bus = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyBus,rmBus);
    return bus;
}

void free_business(C_bus bus){
    g_hash_table_remove_all (bus->catalogo_bus);
}

void load_catalogo_bus(C_bus bus, char * businesses){
    bus->catalogo_bus =  load_business(businesses);
}

//Funçao que dá free a um business
void freeBusiness(BUSINESS *bus){
    
    free(bus->business_id);
    free(bus->name);
    free(bus->city);
    free(bus->state);
    free(bus->categories);
    free(bus);
}

//Função que remove um business da HashTable

void rmBus(gpointer business){

    BUSINESS * bus = business ;
    freeBusiness(bus);

}

//Função que dá free a uma chave

void rmKeyBus(gpointer key){

    free((char *)key);

}

//Função que cria o catalogo de business apartir da leitura de um ficheiro
GHashTable * load_business(char * busFile){
    char buffer[BUFF_SIZE];
    BUSINESS * bus;
    FILE* f = fopen(busFile, "r"); 
	GHashTable * business  = g_hash_table_new_full(g_str_hash,g_str_equal,rmKeyBus,rmBus);
	char * key;
    
    if (f == NULL){
		printErroAbrirFicheiro(busFile);
        return NULL;
	}
	
    while(fgets(buffer,BUFF_SIZE,f)){
        bus = malloc(sizeof(BUSINESS));
        bus->business_id = strdup(strtok(buffer,";"));
        bus->name = strdup(strtok(NULL,";"));
        bus->city = strdup(strtok(NULL,";"));
        bus->state = strdup(strtok(NULL,";"));
        bus->categories = strdup(strtok(NULL,";"));
        key = strdup(bus->business_id);
        g_hash_table_insert(business, key , bus);      
    }   
    
	fclose(f);
    
    return business;
}


//FUNCOES DE GET PARA BUSINESS
char * getBusinessId (BUSINESS *b) {
    return (b->business_id);
}

char * getCategories (BUSINESS *b) {
    return (b->categories);
}

char * getCity (BUSINESS *b) {
    return (b->city);
}

char * getNameBusiness (BUSINESS *b) {
    return (b->name);
}

char * getState (BUSINESS *b) {
    return (b->state);
}

GHashTable * getCatalogoBus(C_bus cat){
    return cat->catalogo_bus;
}
