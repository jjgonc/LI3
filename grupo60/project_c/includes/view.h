#ifndef VIEW_H
#define VIEW_H

#include <stdio.h>
#include <stdlib.h>
#include "table.h"

#define RED   "\x1B[31m"
#define GRN   "\x1B[32m"
#define YEL   "\x1B[33m"
#define BLU   "\x1B[34m"
#define MAG   "\x1B[35m"
#define CYN   "\x1B[36m"
#define WHT   "\x1B[37m"
#define RESET "\x1B[0m"

void printInstrucoesInterpretador();

void printErroLerCaminho();

void printErroAbrirFicheiro (char * file);

void printErroNEncontrouColName(char * column_name);

void printErroMaisColunas(int lenght,int nColunas);

void printErroOperatorInvalido(char * oper);

void printErroColunaInvalida(int x);

void printErroComandoInvalido();

void printSgrPredefinido();

void printUsersLoad();

void printBusinessesLoad();

void printReviewsLoad();

void printString (char * s);

void printLinha (TABLE t, LINHA l, int nColunas);

void printTable (TABLE t, int linhas);

void toCSV (TABLE x,char * delim,char * filepath);

void printNavegacaoTable();


#endif // VIEW_H