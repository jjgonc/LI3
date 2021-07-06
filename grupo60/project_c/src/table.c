
#include "../includes/table.h"


struct linha{
	char* * colunas;
	int * tamColunas;
	int nColunas;
};

struct table{
	LINHA * linhas;
	char* nomeTabela;
	LINHA cabecalho; 
	int * maxTamColuna;
	int nLinhas;
	int nColunas;
	int size;
	int maiorLinha;
} ;

size_t utf8len(char *s){
    size_t len = 0;
    for (; *s; ++s) if ((*s & 0xC0) != 0x80) ++len;
    return len;
}

// n_colunas = numero de parametros 
// função que inicializa Table
TABLE initTable  (char *nomeTabela, int nColunas){
	
	int i;
	TABLE t = malloc (sizeof (struct table));	
	t->linhas = malloc (sizeof(LINHA) * 100);		//100 como valor inicial
	t->nLinhas = 0;
	t->size = 100;
	t->nomeTabela = strdup(nomeTabela);
	t->maxTamColuna = malloc(sizeof(int)*nColunas);
	t->maiorLinha = 0;
	t->nColunas = nColunas;
	t->cabecalho = malloc(sizeof(LINHA));

	for(i = 0; i < nColunas; i++) t->maxTamColuna[i] = 0 ;
		
	return t;
}


TABLE adicionaLinha (LINHA l, TABLE t) {
	
	int i;
	if(t -> size == t->nLinhas){
		t->size = 2 * t->size;
		t->linhas = realloc(t->linhas, sizeof(LINHA) * t->size);

	}

	t->linhas [t->nLinhas++] = l;
	
	for(i = 0; i < l->nColunas; i++){
		
		if(l->tamColunas[i] > t->maxTamColuna[i]) t->maxTamColuna[i] = l->tamColunas[i];
	
	}
	return t;
}

TABLE adicionaCabecalho(LINHA l, TABLE t){
	
	int i;
	
	t->cabecalho = l;
	
	for(i = 0; i < l->nColunas; i++){
		
		if(l->tamColunas[i] > t->maxTamColuna[i]) t->maxTamColuna[i] = l->tamColunas[i];
			

	}
	return t;
}


LINHA criaLinha(char** colunas,int numColunas){
	
	int i;
	LINHA l = malloc (sizeof (struct linha));
	l->colunas = malloc (sizeof (l->colunas)*numColunas);
	l->colunas = colunas;
	l->tamColunas = malloc(sizeof(int)*numColunas);
	l->nColunas = numColunas;
	for(i = 0; i < numColunas; i++){
		l->tamColunas[i] = utf8len(colunas[i]);
	}

	return l;
}

// gets da LINHA

char ** getColunas (LINHA l){
	return l->colunas;
}

char * getColunasIndice (LINHA l,int i){
	char ** res = l->colunas;
	return res[i];
}

int * getTamColuna (LINHA l){
	return l->tamColunas;
}

int getTamColunaIndice (LINHA l,int i){
	int * res = l->tamColunas;
	return res[i];
}

int getNColunasLinha (LINHA l){
	return l->nColunas;
}




// GETS DA TABLE
LINHA * getLinhas (TABLE t){
	return t->linhas;
}

LINHA getLinhasIndice (TABLE t,int i){
	LINHA * res = t->linhas;
	return res[i];
}

char * getNomeTabela (TABLE t){
	return t->nomeTabela;
}

int * getMaxTamColuna (TABLE t){
	return t->maxTamColuna;
}

int getMaxTamColunaIndice (TABLE t,int i){
	int * res = t->maxTamColuna;
	return res[i];
}

int getNLinhas (TABLE t){
	return t->nLinhas;
}

int getNColunasTable (TABLE t){
	return t->nColunas;
}

int getSize (TABLE t){
	return t->size;
}

int getMaiorLinha (TABLE t){
	return t->maiorLinha;
}
LINHA getCabecalho(TABLE t){
	return t->cabecalho;
}

//sets

void setMaiorLinha(int x, TABLE t){

	t->maiorLinha = x;
}
