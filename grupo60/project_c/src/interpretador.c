#include "../includes/interpretador.h"

#define BUFF_SIZE 1024
enum OPERATOR{LT, EQ, GT, IV};

//Implementação de um strtok que funciona com delimitadores do tipo string


int countWords(char * str, char delimiter){

    int words = 0;
    int i = 0;
    int len =  strlen(str);
    
    while(str[i] != '\0'){
        
        
        if(str[i] == delimiter && (isalpha(str[i + 1]) || i + 2 == len))  words = words + 1;
        i++;
    }

return words;

}

int calculaPosDelimiter(char * str, char delimiter){

    int i = 0;
    int len =  strlen(str);
        
    while(str[i] != '\0'){
        
        
        if(str[i] == delimiter && (isalpha(str[i + 1]) || i + 2 == len)) return i + 1;
        i++;
    }
   
    return -1;
}



char **strToWordArray(char *str, char delimiter) {
  
    int i = 0, endIndex = 0;
    
    char **words;
   
    int nwords = countWords(str, delimiter);
  
    words = malloc(sizeof(char*) * (nwords + 1));

   
    for(i = 0; i < nwords; i++){
        
        endIndex = calculaPosDelimiter(str,delimiter);
        words[i] = malloc(sizeof(char) * endIndex + 1);
        strncpy(words[i], str, endIndex + 1);
        words[i][endIndex] = '\0';
        str = str + endIndex;

    }
   
    
   return words;
}



//Função que retorna o argumento x de determinado comando

char * getArg(int x, char * command){
    
    int i = 0, counter = 0, j = 0;
    char * args = malloc(sizeof(char)*50);
    char * argumento = malloc(sizeof(char)*20);
    for(i = 0; *(command + i) != '('; i++);
    args = command + i + 1;
    
    for(i = 0; args[i] != '\0'; i++){
        if(counter == x - 1 && args[i] != ' ' && args[i] != '"'){
            argumento[j] = args[i];
            j++;
        }
        if(args[i + 1] == ',' || args[i + 1] == ')') {
            counter++;
            i++;
        }
    }
    argumento[j] = '\0';
    return argumento;

}

// Função igual à de cima mas a contar com os espaços
char * getArgEspacos(int x, char * command){
    
    int i = 0, counter = 0, j = 0;
    char * args = malloc(sizeof(char)*50);
    char * argumento = malloc(sizeof(char)*20);
    for(i = 0; *(command + i) != '('; i++);
    args = command + i + 1;
    
    for(i = 0; args[i] != '\0'; i++){
        if(counter == x - 1 && args[i] != '"'){
            argumento[j] = args[i];
            j++;
        }
        if(args[i + 1] == ',' || args[i + 1] == ')') {
            counter++;
            i++;
        }
    }
    argumento[j] = '\0';
    return argumento;

}
TABLE fromCSV (char * filepath,char *newDelim){
    
    char buffer[BUFF_SIZE];
    char delim[3];
    strcpy(delim,newDelim);
    strcat(delim,"\n"); 
    FILE * f = fopen(filepath,"r");
    TABLE t = NULL;
    if (f==NULL){
        printErroAbrirFicheiro(filepath);
        return NULL;
    }

    LINHA l;
    char * * colunas; 
    char * aux = malloc(sizeof(char*));
    char * auxanterior = malloc(sizeof(char*));

    char bufferAux[BUFF_SIZE];
    int ncolunas = 0;
    int j = 0, i = 0;
    while (fgets(buffer,BUFF_SIZE,f)){
        i = 0;
         // para ler cabeçalho e contar quantas colunas preciso na table
        if (j == 0){
            strcpy(bufferAux,buffer);
            
            aux = (strtok(bufferAux,delim));
            auxanterior = NULL;

            // while para contar quantas colunas tem 
            while (aux && aux != auxanterior){
                ncolunas++;
                auxanterior = aux;
                aux = (strtok(NULL,delim));
            }

            t = initTable("Table copiada:",ncolunas);

            colunas = malloc(sizeof (char*) * ncolunas);
            aux = (strtok(buffer,delim));
            auxanterior = NULL;

            while (aux && aux != auxanterior){
                colunas[i] = strdup(aux);
                i++;
                auxanterior = aux;
                aux = strtok(NULL,delim);
            }
            l = criaLinha(colunas,ncolunas);
            t = adicionaCabecalho(l,t);
        }
        
        else {
            colunas = malloc(sizeof (char*) * ncolunas);
            aux = (strtok(buffer,delim));
            auxanterior = NULL;

            while (aux && aux != auxanterior){
                colunas[i] = strdup(aux);
                i++;
                auxanterior = aux;
                aux = (strtok(NULL,delim));
            }
            l = criaLinha(colunas,ncolunas);
            t = adicionaLinha(l,t);
        }
        j++;
    }

    return t;
}


void filterLinha(TABLE y,LINHA l,int indiceCol,char * value,enum OPERATOR oper){
    int verifica = 0;
    if (oper == LT){
        if (strcmp (getColunasIndice(l,indiceCol),value) < 0)
            verifica = 1;
    }
    else if (oper == EQ){
            if (strcmp (getColunasIndice(l,indiceCol),value) == 0)
                verifica = 1; 
        }
        else {
            if (strcmp (getColunasIndice(l,indiceCol),value) > 0)
                verifica = 1; 
        }
    
    if (verifica == 1){
        y = adicionaLinha (l,y);
    }
    
}

TABLE filter(TABLE x,char * column_name,char * value ,enum OPERATOR oper){
    TABLE y = initTable(getNomeTabela(x),getNColunasTable(x));
    adicionaCabecalho(getCabecalho(x),y);

    LINHA cabecalho = getCabecalho(x);
    int i = 0,indiceColName = -1;
    while (i <  getNColunasTable(x) && indiceColName == -1){
        if (strcmp(column_name, getColunasIndice(cabecalho,i)) == 0)
            indiceColName = i;
        i++;
    }
    if (indiceColName == -1)
        printErroNEncontrouColName(column_name);
    else {
        i = 0;
        while (i < getNLinhas(x)){
            filterLinha(y,getLinhasIndice(x,i),indiceColName,value,oper);
            i++;
        }
    }
    return y;
}

//identificador 1 se for cabecalho
void projLinha(TABLE y, LINHA l, int cols[], int length,int identificador){
    int i = 0;
    LINHA nova;
    char ** colunas = malloc(sizeof(char *) * length);
    while (i < length){
        colunas[i] = getColunasIndice(l,cols[i]-1);
        i++;    
    }
    nova = criaLinha(colunas,length);
    if (identificador == 1) 
        y = adicionaCabecalho(nova,y);
    else 
        y = adicionaLinha(nova,y);
}

TABLE proj (TABLE x,int cols[], int length){
    TABLE y = initTable(getNomeTabela(x),length);
    projLinha(y,getCabecalho(x),cols,length,1);
    int i = 0;
    while (i < getNLinhas(x)){
        projLinha(y,getLinhasIndice(x,i),cols,length,0);
        i++;
    }
    return y;
}

char * indexacao (TABLE x,int linha,int coluna){
    LINHA l = getLinhasIndice(x,linha-1);
    return getColunasIndice(l,coluna-1);

}

TABLE interpretaComando(SGR sgr, char * comando, TABLE t,TABLE x,TABLE y){

     char * querrys[11] = {"businesses_started_by_letter","business_info","businesses_reviewed",
                        "businesses_with_stars_and_city","top_businesses_by_city","international_users",
                        "top_businesses_with_category","reviews_with_word","fromCSV","filter","proj"};
    
        
        if(strstr(comando,querrys[0])){
            char letter = *getArg(2,comando);
            t = businesses_started_by_letter(sgr,letter); 
        }

        if(strstr(comando,querrys[1])){

            char * business_id = getArg(2,comando);  
           t = business_info(sgr,business_id);

        }
     
        if(strstr(comando,querrys[2])){

            char * user_id =(getArg(2,comando));
            t = businesses_reviewed(sgr,user_id);
      
        }
    
        if(strstr(comando,querrys[3])){

            char * city = getArg(3,comando);
            char * starsString = getArg(2, comando);
            float stars = atol(starsString);
            t = businesses_with_stars_and_city(sgr,stars, city);
        }

        if(strstr(comando,querrys[4])){
            int top = atoi(getArg(2,comando));    
            t = top_businesses_by_city(sgr, top);
        }

        if(strstr(comando,querrys[5])){
            
            t = international_users(sgr);
        }
    
        if(strstr(comando,querrys[6])){
            
            char * topString = getArg(2,comando);
            int top = atoi(topString);
            char * category = getArg(3,comando);
            t = top_businesses_with_category(sgr, top, category);
        }
    
        if(strstr(comando,querrys[7])){

         char * word = getArg(3,comando);
         t = reviews_with_word(sgr, 42, word);

        }

        if(strstr(comando,querrys[8])){
            char * filepath = getArg(1,comando);
            char * newDelim = getArg(2,comando);
            t = fromCSV(filepath,newDelim);
        }

        if(strstr(comando,querrys[9])){
            char variavel = *getArg(1,comando);
            char * column_name = getArgEspacos(2,comando);
            char * value = getArgEspacos(3,comando);
            char * oper = getArg(4,comando);
                
            enum OPERATOR operator;
            if (strcmp(oper,"LT") == 0){
                operator = LT;
            }
            else if (strcmp(oper,"EQ") == 0){
                operator = EQ;
                }
                else if (strcmp(oper,"GT") == 0){
                         operator = GT;
                    } else 
                        operator = IV;
                

            if (operator == IV)
                printErroOperatorInvalido(oper);
            else{
                if (variavel == 'x')
                    t = filter (x,column_name,value,operator);
                else 
                    t = filter (y,column_name,value,operator);
            } 
        }

        if(strstr(comando,querrys[10])){
            char variavel = *getArg(1,comando);
            int nColunas;
            if (variavel == 'x')
                nColunas = getNColunasTable(x);
            else 
                nColunas =  getNColunasTable(y);

            int * array = malloc (sizeof(int)*nColunas);
            int arg = 2, length = 0, verifica = 0;
 
            while (*getArg(arg,comando)!=';' && length <= nColunas && verifica == 0){
                if (atoi(getArg(arg,comando)) <= 0 || atoi(getArg(arg,comando))> nColunas)
                    verifica = 1;
                else{
                    array[arg-2] = atoi(getArg(arg,comando));
                    length++;
                    arg++;
                }
                
            }
            if (verifica == 1){
                printErroColunaInvalida(atoi(getArg(arg,comando)));
                return NULL;
            }

            if (length > nColunas)
                printErroMaisColunas(length,nColunas);
            else {
                if (variavel == 'x')
                    t = proj(x,array,length);
                else 
                    t = proj(y,array,length);
            }
        
        }

    return t;
}




int interpreter(){
    
    int stop = 0;
    TABLE x, y;
        x = initTable("x",0);
        y = initTable("y",0);

    char * z = NULL;
    char c;
    SGR sgr = NULL;
    char users_file[100];
    char business_file[100];
    char reviews_file[100]; 
    int erro1, erro2,erro3;
    int exec = 0;
    
    printSgrPredefinido();
    
    if(scanf("%c", &c)){
        
        if (c == 'm' || c == 'M'){
                        
                        printUsersLoad();
                        erro1 = scanf("%s",users_file);
                        
                        printBusinessesLoad();
                        erro2 = scanf("%s",business_file);

                        printReviewsLoad();
                        erro3 = scanf("%s",reviews_file);

                        if(erro1 & erro2 & erro3) load_sgr(users_file,business_file,reviews_file);
                        else printErroLerCaminho();
                        
                        sgr = load_sgr(users_file,business_file,reviews_file);
        
        }else if( c == 'p' || c == 'P') sgr = load_sgr("input_files/users_full.csv","input_files/business_full.csv", "input_files/reviews_1M.csv");
              
        getchar();
    }



    while(!stop){
        
        printInstrucoesInterpretador();
        
        int j = 0;

        char buffer[BUFF_SIZE];
        
        if(fgets(buffer, BUFF_SIZE, stdin) == NULL) return 1;
        
        if(strlen(buffer) != 0 && strstr(buffer,";")){
        
        char delimiter = ';';

        char ** comandos = strToWordArray(buffer,delimiter);

        int nComandos = countWords(buffer, ';');

        for(j = 0; j < nComandos; j++) {

            char  variavel;
            char * funcao = malloc(sizeof(char)*BUFF_SIZE);

            if(comandos[j][0] == 'x' || comandos[j][0] == 'y'){
                sscanf(buffer,"%c %*[ =] %[^\t\n]",&variavel,funcao);
                if(variavel == 'x') x = interpretaComando(sgr,funcao,x,x,y);
                else y = interpretaComando(sgr,funcao,y,x,y);
                exec = 1;
            }

            if(comandos[j][0] == 'z'){
                 sscanf(buffer,"%c %*[ =] %[^\t\n]",&variavel,funcao);
                 if (strstr(funcao,"x[")){
                     z = indexacao(x,atoi(&funcao[2]), atoi(&funcao[5]));
                 }
                 exec = 1;
            }
        
            if(strstr(comandos[j],"quit")){
               stop = 1; 
               free(sgr);
               exec = 1;
            } 

            if(strstr(comandos[j],"show")){
                variavel = *getArg(1,comandos[j]);
                if(variavel == 'x') printTable(x,100);
                else if (variavel == 'y') printTable(y,100);
                    else printString(z);
                exec = 1;

            }

            if(strstr(comandos[j],"toCSV")){
               variavel = *getArg(1,comandos[j]);
               char * delim = getArg(2,comandos[j]);
               char * filepath = strdup(getArg(3,comandos[j]));
              
               if(variavel == 'x') toCSV(x,delim,filepath);
               else toCSV(y,delim,filepath);
               exec = 1;
            }
        

           if(exec == 0) printErroComandoInvalido();

        }
    
        }else{

            printErroComandoInvalido();
        }

    }
    return 0;
}


