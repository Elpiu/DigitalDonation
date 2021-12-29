package it.unisa.is.c09.digitaldonation.OrganizzazioneSeduteManagement;

import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.*;
import it.unisa.is.c09.digitaldonation.Model.Entity.Donatore;
import it.unisa.is.c09.digitaldonation.Model.Entity.Guest;
import it.unisa.is.c09.digitaldonation.Model.Entity.Seduta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface OrganizzazioneSeduteServiceInterface {

    public void feedbackDonatore(Donatore donatore, boolean feedback,Long idSeduta) throws CannotRelaseFeedbackException;

    public ArrayList<Object> monitoraggioSeduta(Long idSeduta) throws CannotLoadDataRepositoryException;

    public Guest inserimentoGuest(Long idSeduta, Guest guest) throws CannotSaveDataRepositoryException;

    public Seduta schedulazioneSeduta(Seduta seduta) throws CannotSaveDataRepositoryException;

    public Seduta modificaSeduta(Seduta seduta, Long idSeduta) throws CannotUpdateDataRepositoryException;

    public void eliminaSeduta(Long idSeduta) throws CannotDeleteDataRepositoryException;

    public Seduta visualizzaSeduta(Long idSeduta) throws CannotLoadDataRepositoryException;

    public List<Seduta> visualizzaElencoSedute() throws CannotLoadDataRepositoryException;
}
