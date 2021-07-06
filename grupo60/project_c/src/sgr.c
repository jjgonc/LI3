#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include "../includes/sgr.h"

struct sgr
{
	C_users  users;
	C_reviews reviews;
	C_bus businesses;
};

typedef struct dataq2{
		TABLE t;
		GSList *names;
		int counter;
		char letter;

} data2;

typedef struct dataq3{

	char * business_id;
	char * nome;
	char * cidade;
	char * estado;
	float stars;
	int count_reviews;	


} data3;


SGR init_sgr(){
	SGR nSGR = malloc(sizeof(struct sgr));
	nSGR->users =  init_users(nSGR->users);
	nSGR->businesses = init_business(nSGR->businesses);
	nSGR->reviews =  init_reviews(nSGR->reviews);
	return nSGR;
}

void free_sgr(SGR sgr){
	free_users(sgr->users);
	free_business(sgr->businesses);
	free_reviews(sgr->reviews);
}

//Query 1. Dado o caminho para os 3 ficheiros (Users, Businesses, e Reviews), ler o seu
//conteúdo e carregar as estruturas de dados correspondentes. Durante a interação com o
//utilizador (no menu da aplicação), este poderá ter a opção de introduzir os paths
//manualmente ou, opcionalmente, assumir um caminho por omissão. Note-se que qualquer
//nova leitura destes ficheiros deverá de imediato reiniciar e refazer as estruturas de dados
//em memória como se de uma reinicialização se tratasse.

SGR load_sgr(char *usFile, char *busFile, char *revFile){

	SGR sgr = init_sgr();
	load_catalogo_users(sgr->users,usFile);
	load_catalogo_bus(sgr->businesses,busFile);
	load_catalogo_reviews(sgr->reviews, revFile);
	return sgr;

}


//Query 2. Determinar a lista de nomes de negócios e o número total de negócios cujo nome
//inicia por uma dada letra. Note que a procura não deverá ser case sensitive

void query2aux (gpointer key, gpointer value, gpointer user_data){
	
	BUSINESS * bus = value;
	data2 * data = user_data;
	char * name = getNameBusiness(bus);

	if(toupper(name[0]) == data ->letter){
		data->counter++;
		char** colunas = malloc(sizeof(char*));
		colunas[0] = name;
		LINHA l = criaLinha(colunas,1);
		data->t = adicionaLinha(l,data->t);
	}
}

TABLE businesses_started_by_letter(SGR sgr, char letter){

	data2 data;
	char tableName [40];
	sprintf(tableName, "Nomes de negocio com a letra %c",letter);
	data.t = initTable(tableName,1);
	data.counter = 0;
	data.letter = toupper(letter);
	gpointer p = &data;

	g_hash_table_foreach (getCatalogoBus(sgr->businesses),query2aux,p);
	
	char** colunas = malloc(sizeof(char*));
	char * counter = malloc(sizeof(char) * 100);
	sprintf(counter, "Número total de negocios: %d",data.counter);
	colunas[0] = counter;
	LINHA l = criaLinha(colunas,1);
	data.t = adicionaLinha(l,data.t);
	char ** cabecalho = malloc(sizeof(char));
	cabecalho = malloc(sizeof(char*));
	cabecalho[0] = strdup("Business Name");
	LINHA c = criaLinha(cabecalho,1);
	data.t = adicionaCabecalho(c,data.t);
	return data.t;

}

//Query 3. Dado um id de negócio, determinar a sua informação: nome, cidade, estado, stars,
//e número total reviews.

void query3aux (gpointer key, gpointer value, gpointer user_data){

	data3 * d = user_data;
	review * rev = value;
	if(strcmp(getBusinessID(rev), d->business_id) == 0){
		d->count_reviews = d->count_reviews + 1;
		d->stars = d->stars + getStars(rev);
	}
}

TABLE business_info(SGR sgr, char *business_id){

	BUSINESS * bus;
	GHashTable * businesses = getCatalogoBus(sgr->businesses);
	bus = g_hash_table_lookup(businesses, business_id);
	TABLE t;
	char nomeTabela[100];
	sprintf(nomeTabela, "Informação sobre o negócio: ");
	t = initTable(nomeTabela,6);
	
	if(bus == NULL) return t;
	//Recolhe a informação
	
	data3 d;
	d.nome = getNameBusiness(bus);
	d.cidade = getCity(bus);
	d.estado = getState(bus);
	d.business_id = business_id;
	d.stars = 0;
	d.count_reviews = 0;
	g_hash_table_foreach (getCatalogoReviews(sgr->reviews),query3aux,&d);
	d.stars = d.stars/d.count_reviews;
	
	//Adiciona à table

	
	//Cabeçalho
	char **colunas1 = malloc(sizeof(char*)*6);
	colunas1[0] = strdup("BusinessID");
	colunas1[1] = strdup("Nome");
	colunas1[2] = strdup("Cidade");
	colunas1[3] = strdup("Estado");
	colunas1[4] = strdup("Stars");
	colunas1[5] = strdup("Numero Total de Reviews");
	
	//Informação
	char **colunas2 = malloc(sizeof(char*)*5);
	colunas2[0] = d.business_id ;
	colunas2[1] = d.nome;
	colunas2[2] = d.cidade;
	colunas2[3] = d.estado;
	char * estrelas  =  malloc(sizeof(char)*100);
	sprintf(estrelas, "%f",d.stars);
	colunas2[4] = estrelas ;
	char * revCounter  =  malloc(sizeof(char)*12);
	sprintf(revCounter, "%d",d.count_reviews);
	colunas2[5] = revCounter;
	LINHA l1 = criaLinha(colunas1,6);
	LINHA l2 = criaLinha(colunas2,6);
	t = adicionaCabecalho(l1,t);
	t = adicionaLinha(l2,t);

return t;
}


//Query 4. Dado um id de utilizador, determinar a lista de negócios aos quais fez review.
// A informaçao associada a cada negocio deve ser o id e o nome.

typedef struct query_aux {
	TABLE table;
	char * user_id;
	char * bus_id;
    GSList *llist_BId;
	GSList *llist_names;
    GHashTable * hashTable;
} query4aux;



//função auxiliar para devolver uma Singly Linked List com os business id's que um determinado user fez review
void bus_by_user (gpointer key, gpointer value, gpointer userdata) {		
    query4aux * q = userdata;
   	//GSList* g = q->llist_BId;
    review * rev = value;
    if (g_str_equal((gconstpointer) getUserID(rev), (gconstpointer) q->user_id)) {
        q->llist_BId = g_slist_append (q->llist_BId, getBusinessID(rev));
    }
}


GSList * get_bus_by_user (SGR sgr, char * id, query4aux q) {
    GSList * sl = g_slist_alloc ();
	sl = g_slist_next (sl);
	q.hashTable = getCatalogoReviews(sgr->reviews);
    q.llist_BId = sl;
    q.user_id = id;
    gpointer p = &q;
    
    g_hash_table_foreach(q.hashTable, bus_by_user, p);
    return (q.llist_BId);
}


//Funções para devolver uma Singly Linked List com os nomes dos negocios correspondetes a um determinado business id
static void lookup_BId (gpointer data, gpointer user_data) {
	query4aux * q = user_data;
	BUSINESS * bus;
	bus = g_hash_table_lookup (q->hashTable, data);	//retorna o value associado a uma key (neste caso a key é o business_id e o value é a struct business)
	if (bus != NULL) {
		q->llist_names = g_slist_append (q->llist_names, getNameBusiness(bus));
	}
}

GSList * get_businessname (SGR sgr, query4aux q) {
	GSList * sl_names = g_slist_alloc();
	sl_names = g_slist_next (sl_names);	
	q.hashTable = getCatalogoBus(sgr->businesses);
	q.llist_names = sl_names;
	gpointer p = &q;
	g_slist_foreach (q.llist_BId, lookup_BId, p);

	return (q.llist_names);
}


//função auxiliar para adicionar elementos vindos de uma Singly Linked list a uma table
TABLE add_records (TABLE t, GSList *ids, GSList *names) {
	LINHA l;
	int size = (int) g_slist_length(ids);
	for (int i=0; i < size; i++) {	//À partida, ambas as slist's tem que ter o mesmo tamanho, portanto podemos usar o tamanho de qualquer uma delas
		char** colunas = malloc(sizeof (char*) *2);
		colunas[0] = (char *) g_slist_nth_data (names, i);
		colunas[1] = (char *) g_slist_nth_data (ids, i);
		l = criaLinha (colunas, 2);
		t = adicionaLinha (l, t);
	} 
	return t;
}


TABLE businesses_reviewed (SGR sgr, char *user_id) {
	TABLE table = initTable("Nome e ID's dos negocios que o user fez review",2);
	query4aux q;
	q.hashTable = getCatalogoBus(sgr->businesses);
	q.llist_BId = g_slist_alloc ();
	q.llist_names = g_slist_alloc ();
	q.llist_BId = get_bus_by_user (sgr, user_id, q); //procura no Catalogo cada um dos business id's que o user com o user_id fez review
	q.llist_names  = get_businessname(sgr, q); //procura no Catalogo cada um dos business com o business id da slist de business ID's e coloca-os na llist_names

	table = add_records (table, q.llist_BId, q.llist_names);	//pq ambas tem o mesmo tamanho e no mesmo indice esta o id e o nome correspondentes

	// cabeçalho
	char ** cabecalho = malloc(sizeof(char*) * 2);
	cabecalho[0] = "Business Name";
	cabecalho[1] = "Business ID";
	LINHA l1 = criaLinha(cabecalho,2);
	table = adicionaCabecalho(l1,table);

	return table;
}


//Query 5. Dado um número n de stars e uma cidade, determinar a lista de negócios com n
//ou mais stars na dada cidade. A informação associada a cada negócio deve ser o seu id e
//nome.

typedef struct query5aux {
	TABLE t;
	GHashTable * hashTable;
	GSList * llist_BId;
	float stars;
	char * city;
} query5aux;



typedef struct q5_final {
	TABLE table;
	GHashTable * hash;
	char * city;
} q5_final;



//função que dado um nº de stars devolve um Slingly linked list com os business id's dos negocios com tantas ou mais estrelas.
void bus_by_stars (gpointer key, gpointer value, gpointer userdata) {		
    query5aux * q = userdata;
    review * rev = value;
    if (getStars(rev) >= q->stars) {
        q->llist_BId = g_slist_prepend (q->llist_BId, getBusinessID(rev));
    }
}


GSList * get_bus_by_stars (SGR sgr, float stars, query5aux q) {

	q.hashTable = getCatalogoReviews(sgr->reviews);
    gpointer p = &q;
	g_hash_table_foreach(q.hashTable, bus_by_stars, p);
    return (q.llist_BId);
}


//funçao que dada uma cidade, devolve uma Singly linked list com os business id's dos negocios daquela cidade.
void bus_by_city (gpointer data, gpointer userdata) {
	q5_final * q = userdata;
    BUSINESS * bus;
	bus = g_hash_table_lookup (q->hash, data);	//retorna o value associado a uma key (neste caso a key é o business_id e o value é a struct business)
	if (bus != NULL) {
		if ( (strcmp (getCity(bus), q->city)) == 0) {
			char** colunas = malloc(sizeof (char*) *2);
			colunas[0] = (char *) data;
			colunas[1] = getNameBusiness(bus);
			LINHA l = criaLinha (colunas, 2);
			q->table = adicionaLinha (l, q->table);
    	}
	}    
}


//função que procura os bus id's de uma cidade preenche diretamente a TABLE
TABLE get_bus_by_city (SGR sgr, char * city, query5aux q) {
	
	q5_final b;
	char * tableName = malloc(sizeof(char)*100);
	sprintf(tableName,"Lista de negócios com %f ou mais stars na dada cidade",q.stars);
	b.table = initTable(tableName,2);
	b.hash = getCatalogoBus (sgr->businesses);
	q.hashTable = getCatalogoBus(sgr->businesses);
    q.city = city;
	b.city = city;
    g_slist_foreach(q.llist_BId, bus_by_city, &b);

	return b.table;
}


TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city) {
	query5aux q;
	q.stars = stars;
	q.city = city;
	q.hashTable = getCatalogoReviews (sgr->reviews);
	q.llist_BId = g_slist_alloc();
	q.llist_BId = g_slist_next(q.llist_BId);
	q.llist_BId = get_bus_by_stars (sgr, stars, q);
	q.t = get_bus_by_city (sgr, city, q);

	// cabeçalho
	char ** cabecalho = malloc(sizeof(char *) * 2);
	cabecalho[0] = strdup("Business ID");
	cabecalho[1] = strdup("Business Name");
	LINHA l1 = criaLinha(cabecalho,2);
	q.t = adicionaCabecalho(l1,q.t);

	return q.t;
}




//Query 6. Dado um número inteiro n, determinar a lista dos top n negócios (tendo em conta
//o número médio de stars) em cada cidade. A informação associada a cada negócio deve
//ser o seu id, nome, e número de estrelas.



typedef struct query6aux {
	GHashTable * bus_by_city;
	GHashTable * bus_with_stars;
	int top_N;
	TABLE t;
} query6aux;


typedef struct bus_aux {
	char * name;
	float media_stars;
	int nVezes;
	float soma_stars;
	char * bus_ID;
} bus_aux;




//funçao para adicionar as informaçoes do num. medio de stars na struct aux_bus e colocar na hashtable
void add_busWithStars (gpointer key, gpointer value, gpointer userdata) {
	review * rev = value;
	query6aux * q = userdata;
	bus_aux * b_struct;
	gboolean t = g_hash_table_contains(q->bus_with_stars, getBusinessID(rev));
	if (t) {
		b_struct = g_hash_table_lookup (q->bus_with_stars, getBusinessID(rev));
		b_struct->nVezes ++;
		b_struct->soma_stars += getStars(rev);
		b_struct->media_stars = (b_struct->soma_stars)/((float) b_struct->nVezes);
	}

	else {
		b_struct = malloc (sizeof (struct bus_aux));
		b_struct->nVezes = 1;
		b_struct->soma_stars = getStars(rev);
		b_struct->media_stars = (b_struct->soma_stars)/((float) b_struct->nVezes);
		b_struct->bus_ID = getBusinessID(rev);
		g_hash_table_insert (q->bus_with_stars, getBusinessID(rev), b_struct);
	}
}



//função que adiciona um negocio a uma lista ligada (na hashtable cuja key é a cidade e o value é uma lligada)
void addBusByCity (gpointer key, gpointer value, gpointer userdata) {
	query6aux * q = userdata;
	BUSINESS * bus = value;
	bus_aux * b_struct;
	GSList * list_bus;
	gboolean t = g_hash_table_contains(q->bus_by_city, getCity(bus));
	if (t) {
		if (g_hash_table_contains(q->bus_with_stars, getBusinessId(bus))) {
			list_bus = g_hash_table_lookup (q->bus_by_city, getCity(bus));
			b_struct = g_hash_table_lookup (q->bus_with_stars, getBusinessId(bus));
			b_struct->name = getNameBusiness(bus);
			list_bus = g_slist_append (list_bus, b_struct);
		}
	} 

	else {
		if (g_hash_table_contains (q->bus_with_stars, getBusinessId(bus))) {
			b_struct = malloc (sizeof (struct bus_aux));
			list_bus = NULL;
			list_bus = g_slist_alloc();
			list_bus = g_slist_next (list_bus);
			b_struct = g_hash_table_lookup (q->bus_with_stars, getBusinessId(bus));
			
			b_struct->name = strdup (getNameBusiness(bus));
			list_bus = g_slist_append (list_bus, b_struct);
			g_hash_table_insert (q->bus_by_city, getCity(bus), list_bus);
		}
		
	}	
}



//2 funções para ordenar uma lista pelas stars (principal chama a auxiliar)
int top_melhores_comparator (gconstpointer a, gconstpointer b) {
	bus_aux * na = (bus_aux *) a;
	bus_aux * nb = (bus_aux *) b;
	
	if (na->media_stars < nb->media_stars) return 1;
	else if (na->media_stars > nb->media_stars) return -1;
	else return 0;
}

void ordenaByStars (gpointer key, gpointer value, gpointer userdata) {
	GSList * lista = value;
	lista = g_slist_sort(lista, top_melhores_comparator);
}


//função para adicionar a uma tabela as informaçoes pretendidas
void adicionaTab (gpointer key, gpointer value, gpointer userdata) {
    query6aux * q = userdata;
    GSList * list = value;
    LINHA l;
    char ** colunas = malloc(sizeof (char*) * q->top_N);
    bus_aux * b;
    int i = 0;
	int top1 = q->top_N;
    while (q->top_N > 0) {
		if (i < (int) g_slist_length(list)) {
			colunas[i] = malloc(sizeof(char)*1000000);
			b = g_slist_nth_data(list, i);
			sprintf (colunas[i], "%s;%s;%f", b->bus_ID, b->name, b->media_stars);
        }
		else {
			colunas[i] = malloc(sizeof (char) * 10);
			colunas[i] = "Empty";
		}
        
		i++;
        q->top_N--;
    } 
	q->top_N = top1;
    l = criaLinha(colunas, q->top_N);
    q->t = adicionaLinha (l, q->t);
}




TABLE top_businesses_by_city(SGR sgr, int top) {
	query6aux q;
	q.top_N = top;
	char * tableName = malloc(sizeof(char)*100);
	sprintf(tableName,"Top %d negócios por cidade",q.top_N);
	q.t = initTable (tableName, top);
	q.bus_with_stars = g_hash_table_new(g_str_hash,g_str_equal);
	q.bus_by_city = g_hash_table_new(g_str_hash,g_str_equal);
	g_hash_table_foreach (getCatalogoReviews(sgr->reviews), add_busWithStars, &q);
	
	g_hash_table_foreach (getCatalogoBus(sgr->businesses), addBusByCity, &q);
	
	g_hash_table_foreach(q.bus_by_city, ordenaByStars, &q);
	
	g_hash_table_foreach (q.bus_by_city, adicionaTab, &q);

	// cabeçalho
	char ** cabecalho = malloc(sizeof(char *) * top);
	for (int x = 0; x<=top; x++) {
		cabecalho[x] = malloc(sizeof (char) * 20);
		sprintf(cabecalho[x], "TOP %d", x+1);
	}
	LINHA l1 = criaLinha(cabecalho,top);
	q.t = adicionaCabecalho(l1,q.t);
	
	return q.t;
}





//Query 7. Determinar a lista de ids de utilizadores e o número 
//	total de utilizadores que tenham visitado mais de um estado, 
//	i.e. que tenham feito reviews em negócios de diferentes estados.

// estruturas auxiliares para a query 7
typedef struct query7aux{
	char * user_id;
	GSList * list_businessID;
} * query7;

typedef struct ListCat{
	TABLE  table;
	GHashTable * business;
	int counter; 
	char * state1;
	int verificaSD;
	 // states diferentes
} data7;



// Função que adiciona a uma Lista todos os businessIDs associados a um userID e coloca isto numa hashtable 
// se ainda nao tiver este userID
static void get_businessID (gpointer key , gpointer value, gpointer userdata){
	GHashTable * ht7 = userdata;
	review  *rev = value;
	
	char * keyT = strdup(getUserID(rev));
	query7 q7 = malloc(sizeof(query7));
	gpointer p;
	char * busID;
	q7->list_businessID = g_slist_alloc();
	q7->list_businessID = g_slist_next(q7->list_businessID);
	gboolean t = g_hash_table_contains(ht7,keyT);

	// se este user ainda nao tiver hashtable
	if (!t){ 
		q7->user_id = strdup(getUserID(rev));
		busID = strdup (getBusinessID(rev));
		q7->list_businessID = g_slist_append(q7->list_businessID,busID);
		g_hash_table_insert (ht7,keyT,q7);
	}

	// se este user ja estiver na hashtable 
	else{ 
		p = g_hash_table_lookup (ht7,keyT);
		q7 = p;
		busID = strdup (getBusinessID(rev));
		q7->list_businessID = g_slist_append(q7->list_businessID,busID);
	}
}

// Função para remover Users com apenas uma review
gboolean remove_user1Rev (gpointer key , gpointer value, gpointer userdata){
	query7 q7 = value;
	int length = (int) g_slist_length(q7->list_businessID);
	if (length <= 1) return 1;
	return 0; 
}


// Função que verifica se o estado é diferente 
void getStates (gpointer data, gpointer userdata){
	data7 * d7  = userdata;
	char * businessID = strdup((char *) data);
	BUSINESS * bus;
	char * state;
	if (businessID != NULL) {
		bus = g_hash_table_lookup(d7->business,businessID);
		state = strdup(getState(bus));
		if (strcmp(state,d7->state1)!=0)
			d7->verificaSD = 1;	
	}
}

// Função que adiciona à table o userID se tiver reviews de business de estados diferentes
void adicionaTable (gpointer key,gpointer value , gpointer userdata){
	query7 q7 = value;
	data7 * d7 = userdata;
	d7->verificaSD =  0;
	
	d7->state1 = getState(g_hash_table_lookup (d7->business, g_slist_nth_data (q7->list_businessID,1)));
	
	g_slist_foreach ( q7->list_businessID , getStates, d7 );
	
	char ** colunas;
	LINHA l;
	// se tiver encontrado um state diferente adiciona à table
	if (d7->verificaSD == 1) {
		d7->counter++;
		colunas = malloc(sizeof(char*));
		colunas[0] = q7->user_id;
		l = criaLinha(colunas,1);
		d7->table = adicionaLinha(l,d7->table);

	}
	
}

TABLE international_users(SGR sgr){
	GHashTable * ht7 = g_hash_table_new(g_str_hash,g_str_equal);
	gpointer p = ht7;
	
	// coloca na lista todos os businessID para cada user 
	g_hash_table_foreach (getCatalogoReviews(sgr->reviews), get_businessID, p);
	
	// remove da HashTable users so com review de 1 business
	g_hash_table_foreach_steal(ht7,remove_user1Rev,NULL);
	
	data7 d;
	d.table = initTable("Lista de IDs dos Users Internacionais: ",1);
	d.business = getCatalogoBus(sgr->businesses);
	d.counter = 0;

	// adiciona à table todos os userIDs que tenham feito reviews em negócios de diferentes estados
	g_hash_table_foreach(ht7,adicionaTable,&d);
	
	// adiciona à table na ultima linha o número total de utilizadores internacionais
	char ** colunas = malloc(sizeof(char*));
	char * numero = malloc(sizeof(char)*100);
	sprintf(numero, "O número total de utilizadores internacionais é: %d",d.counter);
	colunas[0] = numero;
	LINHA l = criaLinha(colunas,1);
	d.table = adicionaLinha(l,d.table);	

	// cabeçalho
	char ** cabecalho = malloc(sizeof(char));
	cabecalho = malloc(sizeof(char)*10);
	cabecalho[0] = "UserID";
	LINHA l1 = criaLinha(cabecalho,1);
	d.table = adicionaCabecalho(l1,d.table);
	return d.table;
}





// Query 8. Dado um número inteiro n e uma categoria, determinar a lista dos top n negócios (tendo em 
// conta o número médio de stars) que pertencem a uma determinada categoria. A informação associada a
// cada negócio deve ser o seu id, nome, e número de estrelas.

typedef struct query8{
	TABLE table;
	char * categoria;
	int top1;
	GHashTable * bus_by_categorie;
} query8;

typedef struct encontraStars{
	char * business_id;
	char * nome;
	float stars;
	int n_reviews;
	float media;
} * data8;


// Função que preenche as stars da HashTable que tem os businessID da categoria dada
void preencheStars (gpointer key, gpointer value, gpointer userdata){
	query8 *q8 = userdata;
	review * rev = value;
	data8 d8;
	float n;
	if (g_hash_table_contains((q8->bus_by_categorie),getBusinessID(rev))){
		d8 = g_hash_table_lookup(q8->bus_by_categorie,getBusinessID(rev));
		d8->n_reviews = d8->n_reviews + 1;
		d8->stars = d8->stars + getStars(rev);
		n =(float) d8->n_reviews ;
		d8->media = d8->stars / n;
	}
}


// Função que verifica se o business pertence à categoria. Se sim adiciona à table
void verificaCategoria (gpointer key,gpointer value , gpointer userdata){
	query8 * q8 = userdata;
	BUSINESS * bus =value;
	int verifica = 0; 

	char * categories = getCategories(bus);	
	char * categorie = strdup(strtok(categories,","));
	char categoria_dada_fim[200] ;
	strcpy(categoria_dada_fim,q8->categoria);
	strcat(categoria_dada_fim,"\n");
	
	// verifica se este alguma das categorias da lista de categorias é igual à categoria dada
	while (categorie != NULL ){
		if ((strcasecmp(categorie,q8->categoria) == 0) || strcasecmp(categorie,categoria_dada_fim) == 0)
			verifica = 1;
		categorie = strtok(NULL,",");	
	}
	
	data8 d8;
	char * keyT;
	// se pertence à categoria 
	if (verifica == 1) {
		d8 = malloc(sizeof(struct encontraStars));
		d8->business_id = getBusinessId(bus);
		d8->nome = getNameBusiness(bus);
		d8->n_reviews = 0;
		d8->stars = 0.0;
		d8->media = 0.0;
		keyT = d8->business_id;
		g_hash_table_insert(q8->bus_by_categorie,keyT,d8);
	}
}

// Função utilizada para ordenar a lista de data8's segundo o critério das starts
int sort_stars(gconstpointer a, gconstpointer b){
	data8 da = (data8) a;
	data8 db = (data8) b;
	if (da->media < db->media) return 1;
	else if (da->media > db->media) return -1;
		else return 0;
}

// Função que adiciona à Table se ainda faltar elementos para completar o top n
void adicionaTabela (gpointer data,gpointer user_data){
	data8 d8 = data;
	query8 *q8 = user_data;
	
	char ** colunas ;
	LINHA l;
	char * media ;

	if (q8->top1 > 0){
		colunas = malloc(sizeof(char*)*3);
		colunas[0] = d8->business_id;
		colunas[1] = d8->nome;

		media = malloc(sizeof(char)*50);
		sprintf(media,"%f",d8->media);
		colunas[2] = media;
		
		l = criaLinha(colunas,3);
		q8->table = adicionaLinha(l,q8->table);
		q8->top1 = q8->top1 - 1;
	}
}

TABLE top_businesses_with_category(SGR sgr, int top, char *category){
	query8 q8;
	q8.categoria = category;
	q8.top1 = top;
	q8.bus_by_categorie = g_hash_table_new(g_str_hash,g_str_equal);

	GHashTable * business = getCatalogoBus(sgr->businesses);
	g_hash_table_foreach (business, verificaCategoria ,&q8);

	g_hash_table_foreach(getCatalogoReviews(sgr->reviews),preencheStars,&q8);
	
	GList * list;
	list = g_hash_table_get_values(q8.bus_by_categorie);
	list = 	g_list_sort (list,sort_stars);

	char * tableName = malloc(sizeof(char)*100);
	sprintf(tableName,"Top %d negócios que pertencem à categoria dada:",top);
	q8.table = initTable(tableName,3);
	g_list_foreach(list,adicionaTabela,&q8);

	//cabeçalho 
	char ** cabecalho = malloc(sizeof(char));
	cabecalho = malloc(sizeof(char*)*3);
	cabecalho[0] = strdup("BusinessID");
	cabecalho[1] = strdup("Business_Name");
	cabecalho[2] = strdup("Número_Stars");
	LINHA l1 = criaLinha(cabecalho,3);
	q8.table = adicionaCabecalho(l1,q8.table);
	

	return q8.table;
}


// Query 9. Dada uma palavra, determinar a lista de ids de reviews que a referem 
// no campo text. Note que deverá ter em conta os vários possíveis símbolos de pontuação
// para delimitar cada palavra que aparece no texto. Nota: função ispunct da biblioteca ctype.h.

typedef struct query9aux{
	char * word1;
	TABLE table;	
}query9;

// Função que verifica se a word está no campo text. Se sim adiciona a table o reviewID
void verificaWord (gpointer key, gpointer value,gpointer userdata){
	review * rev = value;
	query9 * q9 = userdata;
	
	int tamanho = strlen(q9->word1);
	char * text = strdup(getText(rev));
	
	int verifica = 0;

	// strcasestr retorna um apontador para o inicio da word in text se a word for encontrada
	char * test = malloc(sizeof(char ) * 100000000);
	test = (strcasestr(text, q9->word1));
	
	while (test && verifica == 0){
		if (( ispunct(*(test-1)) != 0 || *(test-1) == ' ') && 
		    (ispunct(test[tamanho]) != 0 || test[tamanho] == ' ' || test[tamanho] == '\n' )){
			verifica = 1;
		}
		else 
			test = strcasestr(test+tamanho,q9->word1);
	}

	char ** colunas;
	LINHA l;
	if (verifica) {
		colunas = malloc(sizeof(char*))	;
		colunas[0] = getReviewID(rev);
		l = criaLinha(colunas,1);
		q9->table = adicionaLinha(l,q9->table);
	}
}

TABLE reviews_with_word(SGR sgr, int top, char *word){
	query9 q9;
	q9.word1 = word;

	char * tableName = malloc(sizeof(char)*100);
	sprintf(tableName,"Lista de ids de reviews que referem a palavra %s no seu campo text:",word);
	q9.table= initTable(tableName,1);

	g_hash_table_foreach(getCatalogoReviews(sgr->reviews),verificaWord,&q9);

	// cabeçalho
	char ** cabecalho = malloc(sizeof(char));
	cabecalho = malloc(sizeof(char)*10);
	cabecalho[0] = "ReviewID";
	LINHA l1 = criaLinha(cabecalho,1);
	q9.table = adicionaCabecalho(l1,q9.table);

	return q9.table;
}
