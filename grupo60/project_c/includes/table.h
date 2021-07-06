#ifndef TABLE_H
#define TABLE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define RED   "\x1B[31m"
#define GRN   "\x1B[32m"
#define YEL   "\x1B[33m"
#define BLU   "\x1B[34m"
#define MAG   "\x1B[35m"
#define CYN   "\x1B[36m"
#define WHT   "\x1B[37m"
#define RESET "\x1B[0m"

typedef struct linha *LINHA;

typedef  struct table * TABLE;

TABLE initTable (char *nomeTabela, int colunas);

TABLE adicionaLinha (LINHA l, TABLE t);

TABLE adicionaCabecalho(LINHA l, TABLE t);

LINHA initLinha (int numColunas);

LINHA criaLinha(char** colunas, int numColunas);  

// gets da LINHA

char ** getColunas (LINHA l);

char * getColunasIndice (LINHA l,int i);

int * getTamColuna (LINHA l);

int getTamColunaIndice (LINHA l,int i);

int getNColunasLinha (LINHA l);

int getTamLinha (LINHA l);


// GETS DA TABLE
LINHA * getLinhas (TABLE t);

LINHA getLinhasIndice (TABLE t,int i);

char * getNomeTabela (TABLE t);

int * getMaxTamColuna (TABLE t);

int getMaxTamColunaIndice (TABLE t,int i);

int getNLinhas (TABLE t);

int getNColunasTable (TABLE t);

int getSize (TABLE t);

int getMaiorLinha (TABLE t);

LINHA getCabecalho(TABLE t);

void setMaiorLinha(int x, TABLE t);

#endif // TABLE_H
