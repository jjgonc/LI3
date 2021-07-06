#include "../includes/view.h"
#include"../includes/sgr.h"

#define BUFF_SIZE 10000
void printErroAbrirFicheiro (char * file){
    printf ("Erro ao abrir o ficheiro '%s' \n",file);
}

void printErroLerCaminho (){
    printf ("Erro ao ler o caminho do ficheiro \n");
}


void printErroNEncontrouColName(char * column_name){
    printf("Erro, não encontrou nome da coluna '%s'\n",column_name);
}

void printErroOperatorInvalido(char * oper){
    printf("Erro, operator inválido ('%s')\n",oper);
}

void printErroMaisColunas(int lenght,int nColunas){
    printf("Erro, está a tentar obter um subconjunto de colunas maior do que a tabela contém.\n");
    printf("Tabela: %d colunas, Subconjunto: %d colunas",nColunas,lenght);
}

void printErroComandoInvalido(){
    printf("Comando Inválido\n");
}

void printUsersLoad(){
    printf("Por favor introduza o caminho do ficheiro users: ");
}

void printBusinessesLoad(){
    printf("Por favor introduza o caminho do ficheiro businesses: ");
}

void printReviewsLoad(){
    printf("Por favor introduza o caminho do ficheiro reviews: ");
}

void printSgrPredefinido(){
    printf("Introduza p casa prefira usar os ficheiros predefinidos\n");
    printf("Introduza m caso pretenda usar os seus ficheiros\n");
}

void printString (char * s){
    printf("%s\n",s);
}

void printErroColunaInvalida(int x){
    printf("Erro, coluna '%d' inválida",x);
}

void printLinha (TABLE t, LINHA l, int nColunas) {
	int i, j, nEspacos = 0;
	for (i = 0; i < nColunas; i++) {
			nEspacos = getMaxTamColunaIndice(t,i) - getTamColunaIndice(l,i); 
			printf(GRN"|"RESET);
			printf(" %s", getColunasIndice(l,i));
			for(j = 0; j < nEspacos; j++) putchar(' ');
			printf(GRN" |"RESET);
	}

}

void printTableAux (TABLE t, int inicio, int fim, int pagina) {
	
    int i;
	int nColunas = getNColunasTable(t);
	
	printf(GRN"\n%s\n"RESET,getNomeTabela(t));

	for(i = 0; i < getMaiorLinha(t) + nColunas; i++){
		printf(GRN"-"RESET);
	}
    putchar('\n');
	
    printLinha(t, getCabecalho(t),nColunas);

    putchar('\n');

	for (i = inicio; i < fim; i++) {
		printLinha(t,getLinhasIndice(t,i), nColunas);
		printf ("\n");
	}

	for(i = 0; i < getMaiorLinha(t) + nColunas; i++){
		printf(GRN"-"RESET);
	}

    putchar('\n');
    printf(GRN"Página numero: %d"RESET,pagina);
	putchar('\n');
}



void printTable(TABLE t, int linhas){

    char c;
    int pagina = 1;
    int inicio = 0;
    int fim = linhas;
    int nLinhas = getNLinhas(t);
    int stop = 0;

    if(linhas > getNLinhas(t)) fim = getNLinhas(t);
    
    int maiorLinha = 0;
    for(int i = 0; i < getNColunasTable(t); i++){
        
        maiorLinha += getMaxTamColunaIndice(t,i);
    }
    
    maiorLinha += 3*getNColunasTable(t);

    setMaiorLinha(maiorLinha,t);

    while(inicio < nLinhas && !stop){
        printTableAux(t,inicio,fim,pagina);
        inicio = fim;
        fim = fim + linhas;
        if(fim > nLinhas) fim = nLinhas;
        pagina++;
        printNavegacaoTable();
        if(scanf("%c", &c) == 1){
            
            if(c == 'q' || c == 'Q') stop = 1;
        }
    
    }

    getchar();

}


void adicionaLinhatoCSV (LINHA l,FILE * f,char * delim){
    int i = 0;
    char * aux;
    while (i < getNColunasLinha(l)){
        aux = getColunasIndice(l,i);
        fprintf(f,"%s",aux);
        if (i != getNColunasLinha(l)-1) fprintf(f,"%s",delim);
        i++;
    }
    fprintf(f,"\n");
}


void toCSV (TABLE x,char * delim,char * filepath){
    FILE * f = fopen (filepath, "w"); 
    adicionaLinhatoCSV(getCabecalho(x),f,delim);
    int i = 0;
    while (i < getNLinhas(x)){
        adicionaLinhatoCSV (getLinhasIndice(x,i),f,delim);
        i++;
    }
    fclose (f);
}


void printNavegacaoTable(){
    printf("Pressione ENTER para avançar na página\n");
    printf("Pressione q para terminar a visualização\n");
}


void printInstrucoesInterpretador(){

    printf("\n\n");
    printf(GRN"|--------------------------------------------------------------------------------------|\n"RESET);
    printf(GRN"|"RESET);
    printf(YEL" Para manipular a Base de Dados os comandos necessáricos são os seguintes:            "RESET);
    printf(GRN"|\n"RESET);
    printf(GRN"|                                                                                      |\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE businesses_started_by_letter(SGR sgr, char letter);                   ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE business_info(SGR sgr, char *business_id);                            ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE businesses_reviewed (SGR sgr, char *user_id);                         ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE businesses_with_stars_and_city(SGR sgr, float stars, char *city);     ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE top_businesses_by_city(SGR sgr, int top);                             ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE international_users(SGR sgr)                                          ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE top_businesses_with_category(SGR sgr, int top, char *category);       ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE reviews_with_word(SGR sgr, int top, char *word);                      ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 VOID toCSV (TABLE x,char * delim,char * filepath);                          ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE fromCSV (char * filepath, char * delim);                              ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE filter(TABLE x,char * column_name,char * value ,enum OPERATOR oper);  ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 TABLE proj (TABLE x,int cols[], int length);                                ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 x/y[col][line] --> Indexação                                                ");
    printf(GRN"|\n"RESET);
    printf(GRN"|"RESET);
    printf("\t \u2022 quit;                                                                       ");
    printf(GRN"|\n"RESET);
    printf(GRN"|--------------------------------------------------------------------------------------|\n"RESET);
    printf("\n\n");


}









