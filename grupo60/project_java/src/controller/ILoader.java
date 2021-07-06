package controller;

import model.IGestReviews;
import model.business.IBusinessCat;
import model.estatisticas.IModuloEstatisticas;
import model.users.IUserCat;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface para o loader.
 */
public interface ILoader {

    public void loadBusinesses (IBusinessCat model,IModuloEstatisticas me, String file) throws IOException, FileNotFoundException;
    public void loadUsers (IUserCat model, IModuloEstatisticas me, String file,int incluir_amigos)  throws IOException, FileNotFoundException;
    public void loadReviews(IGestReviews model, String file)  throws IOException, FileNotFoundException;
    public IGestReviews binaryLoader(String fileName, IGestReviews model) throws IOException, ClassNotFoundException;


}
