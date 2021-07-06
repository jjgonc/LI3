FLAGS= -std=c99 -Wall -O2 -D_GNU_SOURCE
PKG = `pkg-config --cflags --libs glib-2.0`
COMPILER=gcc
INCLUDES=includes
SRC=src
OBJ=obj

program:  catalogo_business catalogo_users catalogo_reviews view sgr interpretador table main allprog
	
view: $(SRC)/view.c $(INCLUDES)/view.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/view.o $(PKG) -c $(SRC)/view.c

catalogo_business: $(SRC)/catalogo_business.c $(INCLUDES)/catalogo_business.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogo_business.o $(PKG) -c $(SRC)/catalogo_business.c

catalogo_users: $(SRC)/catalogo_users.c $(INCLUDES)/catalogo_users.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogo_users.o $(PKG) -c $(SRC)/catalogo_users.c

catalogo_reviews: $(SRC)/catalogo_reviews.c $(INCLUDES)/catalogo_reviews.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/catalogo_reviews.o $(PKG) -c $(SRC)/catalogo_reviews.c

table: $(SRC)/table.c $(INCLUDES)/table.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/table.o $(PKG) -c $(SRC)/table.c

sgr: $(SRC)/sgr.c $(INCLUDES)/sgr.h
	$(COMPILER) $(FLAGS) -o $(OBJ)/sgr.o $(PKG) -c $(SRC)/sgr.c

interpretador: $(SRC)/interpretador.c $(INCLUDES)/interpretador.h
	     $(COMPILER) $(FLAGS) -o $(OBJ)/interpretador.o $(PKG) -c $(SRC)/interpretador.c


main: $(SRC)/main.c $(OBJ)/sgr.o
	$(COMPILER) $(FLAGS) -o $(OBJ)/main.o $(PKG) -c $(SRC)/main.c	


allprog: $(SRC)/main.c $(OBJ)/main.o $(OBJ)/sgr.o 
	$(COMPILER) $(FLAGS)  $(OBJ)/catalogo_reviews.o $(OBJ)/catalogo_users.o $(OBJ)/catalogo_business.o $(OBJ)/view.o $(OBJ)/sgr.o $(OBJ)/table.o $(OBJ)/interpretador.o $(OBJ)/main.o  $(PKG) -o program


memcheck:
	valgrind --leak-check=yes ./program

clean:
	rm -rf $(OBJ)/*.o program



