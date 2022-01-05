package it.unisa.is.c09.digitaldonation.GestioneTesserinoManagement;

import it.unisa.is.c09.digitaldonation.ErroreManagement.GestioneTesserinoError.TesserinoFormException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.CannotSaveDataRepositoryException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.CannotUpdateDataRepositoryException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.GuestFormException;
import it.unisa.is.c09.digitaldonation.ErroreManagement.OrganizzazioneSeduteError.SedutaFormException;
import it.unisa.is.c09.digitaldonation.Model.Entity.Tesserino;
import it.unisa.is.c09.digitaldonation.Model.Entity.*;
import it.unisa.is.c09.digitaldonation.Model.Repository.DonatoreRepository;
import it.unisa.is.c09.digitaldonation.Model.Repository.IndisponibilitaRepository;
import it.unisa.is.c09.digitaldonation.Model.Repository.SedutaRepository;
import it.unisa.is.c09.digitaldonation.Model.Repository.TesserinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class GestioneTesserinoService implements GestioneTesserinoServiceInterface {

    private static Logger logger = Logger.getLogger(String.valueOf(GestioneTesserinoService.class));

    @Autowired
    private TesserinoRepository tesserinoRepository;

    @Autowired
    private DonatoreRepository donatoreRepository;

    @Autowired
    private IndisponibilitaRepository indisponibilitaRepository;

    @Autowired
    private SedutaRepository sedutaRepository;


    /**
     * Questo metodo permette all'operatore di creare un nuovo tesserino.
     *
     * @param tesserino Il tesserino
     * @return il tesserino creato
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tesserino creazioneTesserino(Tesserino tesserino) throws CannotSaveDataRepositoryException {
        if(tesserino == null){
            throw new CannotSaveDataRepositoryException("tesserinoError", "Errore, il tesserino è null");
        }else if(tesserinoRepository.findTesserinoByIdTessera(tesserino.getIdTessera())!=null){
            throw new CannotSaveDataRepositoryException("tesserinoError", "Il tesserino già esiste");
        }
        tesserinoRepository.save(tesserino);
        return tesserino;
    }


    /**
     * Questo metodo permette di dichiarare l'indisponibilità di un donatore a donare
     *
     * @param indisponibilita è l'oggetto che rappresenta l'indisponibilità.
     * @return l'indisponibilità
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Indisponibilita autodichiarazioneIndisponibilita(Indisponibilita indisponibilita) throws CannotSaveDataRepositoryException {

        List<Seduta> listaSedute;
        if (indisponibilita.getDataProssimaDisponibilita() == null){
            throw new CannotSaveDataRepositoryException("autodichiarazioneError", "Errore, la data è null");
        }
        else if(indisponibilita.getDataProssimaDisponibilita() == null){
            throw new CannotSaveDataRepositoryException("autodichiarazioneError", "Errore, la motivazione è null");
        }

        indisponibilitaRepository.save(indisponibilita);

        listaSedute = sedutaRepository.findAll();
        for(Seduta s: listaSedute){
            if(s.getDataSeduta().before(indisponibilita.getDataProssimaDisponibilita())){
                for(int i = 0; i < s.getListaDonatore().size(); i++){
                    if(s.getListaDonatore().get(i).getCodiceFiscale().equals(indisponibilita.getCodiceFiscaleDonatore())){
                        s.getListaDonatore().remove(i);
                        i--;
                    }
                }
                sedutaRepository.save(s);
            }
        }
        return indisponibilita;
    }


    /**
     * Questo metodo permette di aggiornare il tesserino
     *
     * @param tesserino Il tesserino
     * @return il tesserino aggiornato
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tesserino aggiornaTesserino(Tesserino tesserino) throws CannotUpdateDataRepositoryException {
        return tesserino;
    }

    /**
     * Questo metodo permette di generare una password in seguito alla creazione del tesserino
     *
     * @param tesserino Il tesserino
     * @return la password generata
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String generaPassword(Tesserino tesserino) throws CannotSaveDataRepositoryException {
      String password =null;
        if(tesserino == null){
            throw new CannotSaveDataRepositoryException("tesserinoError", "Errore, il tesserino è null");
        }
        return password;
    }

    /**
     * Controlla che il nome di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param nome Stringa che rappresenta il nome da controllare
     * @return nome La stringa che rappresenta il nome da controllare validato
     * @throws TesserinoFormException se il nome non è specificato oppure se non
     *                            rispetta il formato {@link Guest#NOME_COGNOME_REGEX}
     */
    public String validaNome(String nome) throws TesserinoFormException {
        if (nome == null) {
            throw new TesserinoFormException("TesserinoNomeError", "Il nome non rispetta il formato.  Inserire solo caratteri alfabetici.  ");
        } else {
            if (!nome.matches(Donatore.NOME_COGNOME_REGEX)) {
                throw new TesserinoFormException("TesserinoNomeError", "Il nome non rispetta il formato.  Inserire solo caratteri alfabetici.  .");
            } else {
                return nome;
            }
        }
    }

    /**
     * Controlla che il cognome di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param cognome Stringa che rappresenta il cognome da controllare
     * @return cognome La stringa che rappresenta il cognome da controllare validato
     * @throws TesserinoFormException se il cognome non è specificato oppure se non
     *                            rispetta il formato {@link Guest#NOME_COGNOME_REGEX}
     */
    public String validaCognome(String cognome) throws TesserinoFormException {
        if (cognome == null) {
            throw new TesserinoFormException("TesserinoCognomeError", "Il cognome non rispetta il formato. Inserire solo caratteri alfabetici.  ");
        } else {
            if (!cognome.matches(Donatore.NOME_COGNOME_REGEX)) {
                throw new TesserinoFormException("TesserinoCognomeError", "Il cognome non rispetta il formato. Inserire solo caratteri alfabetici.  ");
            } else {
                return cognome;
            }
        }
    }

    /**
     * Controlla che il codiceFiscale di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param codiceFisacle Stringa che rappresenta il codice fiscale da controllare
     * @return codiceFisacle La stringa che rappresenta il codice fiscale da controllare validato
     * @throws TesserinoFormException se il codice fiscale non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#CODICEFISCALE_REGEX}
     */
    public String validaCodiceFiscale(String codiceFisacle) throws TesserinoFormException {

        if (codiceFisacle == null) {
            throw new TesserinoFormException("TesserinoCodiceFiscaleError", "Il CF non rispetta il formato.");
        } else {
            if (!codiceFisacle.matches(Tesserino.CODICEFISCALE_REGEX)) {
                throw new TesserinoFormException("TesserinoCodiceFiscaleError", "Il CF non rispetta il formato.");
            } else {
                if((tesserinoRepository.findDonatoreBydonatoreUtenteCodiceFiscale(codiceFisacle) != null))
                {
                    throw new TesserinoFormException("TesserinoCodiceFiscaleError", "Utente con questo codice fiscale gia esistente sul database");
                }
                return codiceFisacle;
            }
        }
    }


    /**
     * Controlla che il l'immagine di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param image Stringa che rappresenta l'immagine da controllare
     * @return image La stringa che rappresenta l'immagine da controllare validata
     * @throws TesserinoFormException
     */
    public Image validaImage(Image image) throws TesserinoFormException {
        if (image == null) {
            throw new TesserinoFormException("TesserinoImageError", " Il formato dell’immagine non è corretto. Inserire un’immagine che ha formato png o jpg.");
        } else {
            {
                return image;
            }
        }
    }

    /**
     * Controlla che il la data di nascita di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param dataDiNascita Stringa che rappresenta la data di nascita da controllare
     * @return dataDiNascita La stringa che rappresenta la data di nascita da controllare validata
     * @throws TesserinoFormException se il nome non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#DATARILASCIO_REGEX}
     */
    public Date validaDataDiNascita(Date dataDiNascita) throws TesserinoFormException {
        Date date = new Date();
        if (dataDiNascita == null) {
            throw new TesserinoFormException("TesserinoDataNscitaError", "La data non rispetta il formato. Inserire solo dati numerici e del formato dd/mm/aaaa ");
        } else {
            if (!(parsDateToString(dataDiNascita).matches(Tesserino.DATARILASCIO_REGEX))) {
                throw new TesserinoFormException("TesserinoDataNscitaError", "La data non rispetta il formato. Inserire solo dati numerici e del formato dd/mm/aaaa ");
            } else if (((dataDiNascita.getYear() + 18) - date.getYear()) > 0 ) {
                throw new TesserinoFormException("TesserinoDataNscitaError", "L’utente non è maggiorenne. Inserire una data corretta.");
            }
            return dataDiNascita;
        }
    }


    /**
     * Controlla che il luogoDiNascita di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param luogoDiNascita Stringa che rappresenta il luogo di nascita da controllare
     * @return luogoDiNascita La stringa che rappresenta il luogo di nascita da controllare validato
     * @throws TesserinoFormException se la data di nascita non è specificata oppure se non
     *                            rispetta il formato {@link Donatore#LUOGONASCITA_REGEX}
     */
    public String validaLuogoDiNascita(String luogoDiNascita) throws TesserinoFormException {
        if (luogoDiNascita == null) {
            throw new TesserinoFormException("TesserinoLuogoNascitaError", "Il luogo di nascita non rispetta il formato. Inserire solo valori alfabetici.  ");
        } else {
            if (!luogoDiNascita.matches(Donatore.LUOGONASCITA_REGEX)) {
                throw new TesserinoFormException("TesserinoLuogoNascitaError", "Il luogo di nascita non rispetta il formato. Inserire solo valori alfabetici.  ");
            } else {
                return luogoDiNascita;
            }
        }
    }

    /**
     * Controlla che la residenza di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param residenza Stringa che rappresenta la residenza da controllare
     * @return residenza La stringa che rappresenta la residenza da controllare validata
     * @throws TesserinoFormException se la residenza non è specificata oppure se non
     *                            rispetta il formato {@link Donatore#RESIDENZA_REGEX}
     */
    public String validaResidenza(String residenza) throws TesserinoFormException {
        if (residenza == null) {
            throw new TesserinoFormException("TesserinoResidenzaError", "La residenza non rispetta il formato. Inserire solo dati alfanumerici e segni di punteggiatura.");
        } else {
            if (!residenza.matches(Donatore.RESIDENZA_REGEX)) {
                throw new TesserinoFormException("TesserinoResidenzaError", "La residenza non rispetta il formato. Inserire solo dati alfanumerici e segni di punteggiatura.");
            } else {
                return residenza;
            }
        }
    }

    /**
     * Controlla che la email di un tesserino sia specificata e che rispetti il formato
     * prestabilito.
     *
     * @param email Stringa che rappresenta l'email da controllare
     * @return email La stringa che rappresenta la mail da controllare validata
     * @throws TesserinoFormException se la mail non è specificata oppure se non
     *                            rispetta il formato {@link Utente#EMAIL_REGEX}
     */
    public String validaEmail(String email, String codiceFiscale) throws TesserinoFormException {
        Donatore donatore = new Donatore();
        if (email == null) {
            throw new TesserinoFormException("TesserinoEmailError", "L’email non rispetta il formato. Inserire email del formato: xxxx@xxx.xx ");
        } else {
            if (!email.matches(Donatore.EMAIL_REGEX)) {
                throw new TesserinoFormException("TesserinoEmailError", "L’email non rispetta il formato. Inserire email del formato: xxxx@xxx.xx ");
            } else {
                donatore = donatoreRepository.findDonatoreByCodiceFiscaleUtente(codiceFiscale);
                if(donatore.getEmail().equals(email))
                {
                    throw new TesserinoFormException("TesserinoEmailError", "L’email è già stata utilizzata. L’email è stata già registrata in qualche altro tesserino");
                }
                return email;
            }
        }
    }

    /**
     * Controlla che il gruppo sanguigno di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param gruppoSanguigno Stringa che rappresenta il gruppo sanguigni da controllare
     * @return gruppoSanguigno La stringa che rappresenta il gruppo sanguigno da controllare validato
     * @throws TesserinoFormException se il gruppo sanguigno non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#GRUPPOSANGUIGNO_REGEX}
     */
    public String validaGruppoSanguigno(String gruppoSanguigno) throws TesserinoFormException {
        if (gruppoSanguigno == null) {
            throw new TesserinoFormException("TesserinoGruppoSanguignoError", "Il gruppo sanguigno non rispetta il formato. Il gruppo sanguigno può assumere solo valori  “0”, “A”, “B”, “AB”.  ");
        } else {
            if (!gruppoSanguigno.matches(Tesserino.GRUPPOSANGUIGNO_REGEX)) {
                throw new TesserinoFormException("TesserinoGruppoSanguignoError", "Il gruppo sanguigno non rispetta il formato. Il gruppo sanguigno può assumere solo valori  “0”, “A”, “B”, “AB”.  ");
            } else {
                return gruppoSanguigno;
            }
        }
    }

    /**
     * Controlla che l'rh di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param Rh Stringa che rappresenta l'rh da controllare
     * @return Rh La stringa che rappresenta l'rh da controllare validato
     * @throws TesserinoFormException se l'rh non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#RH_REGEX}
     */
    public String validaRh(String Rh) throws TesserinoFormException {
        if (Rh == null) {
            throw new TesserinoFormException("TesserinoRhError", "Il campo RH non rispetta il formato. Può assumere solo valori “POS” e “NEG” ");
        } else {
            if (!Rh.matches(Tesserino.RH_REGEX)) {
                throw new TesserinoFormException("TesserinoRhError", "Il campo RH non rispetta il formato. Può assumere solo valori “POS” e “NEG” ");
            } else {
                return Rh;
            }
        }
    }

    /**
     * Controlla che le altre indicazioni di un tesserino siano specificate e che rispettino il formato
     * prestabilito.
     *
     * @param altreIndicazioni Stringa che rappresenta altre indicazioni da controllare
     * @return altreIndicazioni La stringa che rappresenta altre indicazioni da controllare validate
     * @throws GuestFormException se le altre indicazioni non sono specificate oppure se non
     *                            rispettano il formato {@link Tesserino#ALTREINDICAZIONI_REGEX}
     */
    public String validaAltreIndicazioni(String altreIndicazioni) throws TesserinoFormException {
        if (altreIndicazioni == null) {
            throw new TesserinoFormException("TesserinoAltreIndicazioniError", "Altre indicazioni non rispetta il formato. Inserire caratteri alfanumerici e punteggiatura.");
        } else {
            if (!altreIndicazioni.matches(Tesserino.ALTREINDICAZIONI_REGEX)) {
                throw new TesserinoFormException("TesserinoAltreIndicazioniError", "Altre indicazioni non rispetta il formato. Inserire caratteri alfanumerici e punteggiatura.");
            } else {
                return altreIndicazioni;
            }
        }
    }

    /**
     * Controlla che il numero di matricola di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param numeroMatricola Stringa che rappresenta il numero di matricola da controllare
     * @return numeroMatricola La stringa che rappresenta il numero di matricola da controllare validato
     * @throws TesserinoFormException se il numero di matricola non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#NUMEROMATRICOLA_REGEX}
     */
    public int validaNumeroMatricola(int numeroMatricola) throws TesserinoFormException {
        if (numeroMatricola != 0) {
            throw new TesserinoFormException("TesserinoNumeroMatricolaError", "Il numero di matricola non rispetta il formato. Inserire solo 7 caratteri numerici.  ");
        } else {
            if (! String.valueOf(numeroMatricola).matches(Tesserino.NUMEROMATRICOLA_REGEX)) {
                throw new TesserinoFormException("TesserinoNumeroMatricolaError", "Il numero di matricola non rispetta il formato. Inserire solo 7 caratteri numerici.  ");
            } else {
                return numeroMatricola;
            }
        }
    }


    /**
     * Controlla che il numero di tessera di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param numeroTessera Stringa che rappresenta il numero di tessera da controllare
     * @return numeroTessera La stringa che rappresenta il numero di tessera da controllare validato
     * @throws TesserinoFormException se il numero di tessera non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#NUMEROMATRICOLA_REGEX}
     */
    public int validaNumeroTessera(int numeroTessera) throws TesserinoFormException {
        if (numeroTessera != 0) {
            throw new TesserinoFormException("TesserinoNumeroMatricolaError", "Il numero di tesserino non rispetta il formato. Inserire solo 7 caratteri numerici. ");
        } else {
            if (! String.valueOf(numeroTessera).matches(Tesserino.NUMEROMATRICOLA_REGEX)) {
                throw new TesserinoFormException("TesserinoNumeroMatricolaError", "Il numero di tesserino non rispetta il formato. Inserire solo 7 caratteri numerici. ");
            } else {
                return numeroTessera;
            }
        }
    }

    /**
     * Controlla che il la data di rilascio di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param dataRilascio Stringa che rappresenta la data di rialscio del tesserino da controllare
     * @return dataRilascio La stringa che rappresenta la data di rialscio del tesserino da controllare validata
     * @throws TesserinoFormException se la data di rilascio non è specificata oppure se non
     *                            rispetta il formato {@link Tesserino#DATARILASCIO_REGEX}
     */
    public Date validaDataRilascio(Date dataRilascio) throws TesserinoFormException {
        Date date = new Date();
        if (dataRilascio == null) {
            throw new TesserinoFormException("TesserinoDataRilascioError", "Data rilascio tessera non rispetta il formato. La data deve avere solo valori numeri del formato: dd/mm/aaaa ");
        } else {
            if (!(parsDateToString(dataRilascio).matches(Tesserino.DATARILASCIO_REGEX))) {
                throw new TesserinoFormException("TesserinoDataRilascioError", "Data rilascio tessera non rispetta il formato. La data deve avere solo valori numeri del formato: dd/mm/aaaa ");
            } else if (dataRilascio.after(date)) {
                throw new TesserinoFormException("TesserinoDataRilascioError", "Data rilascio tessera non può superare la data attuale. ");
            }
            return dataRilascio;
        }
    }

    /**
     * Controlla che il la data di donazione di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param dataDonazione Stringa che rappresenta la data di donazione da controllare
     * @return dataDonazione La stringa che rappresenta la data di donazione da controllare validata
     * @throws TesserinoFormException se la data di donazione non è specificata oppure se non
     *                            rispetta il formato {@link Tesserino#DATARILASCIO_REGEX}
     */
    public Date validaDataDonazione(Date dataDonazione) throws TesserinoFormException {
        Date date = new Date();
        if (dataDonazione == null) {
            throw new TesserinoFormException("TesserinoTipoDonazioneError", "Data donazione non rispetta il formato. La data deve avere solo valori numeri del formato: dd/mm/aaaa ");
        } else {
            if (!(parsDateToString(dataDonazione).matches(Tesserino.DATARILASCIO_REGEX))) {
                throw new TesserinoFormException("TesserinoTipoDonazioneError", "Data donazione non rispetta il formato. La data deve avere solo valori numeri del formato: dd/mm/aaaa ");
            } else if (dataDonazione.after(date)) {
                throw new TesserinoFormException("TesserinoTipoDonazioneError", "Data donazione non può superare la data attuale. ");
            }
            return dataDonazione;
        }
    }

    /**
     * Controlla che il tipo di donazione di un tesserino sia specificato e che rispetti il formato
     * prestabilito.
     *
     * @param tipoDonazione Stringa che rappresenta l'rh da controllare
     * @return tipoDonazione La stringa che rappresenta l'rh da controllare validato
     * @throws TesserinoFormException se il tipo donazione non è specificato oppure se non
     *                            rispetta il formato {@link Tesserino#TIPODONAZIONE_REGEX}
     */
    public String validaTipoDonazione(String tipoDonazione) throws TesserinoFormException {
        if (tipoDonazione == null) {
            throw new TesserinoFormException("TesserinoTipoDonazioneError", "Il campo tipo donazione non rispetta il formato. Può assumere solo valori “plasma”,  “cito” e “sangue” ");
        } else {
            if (!tipoDonazione.matches(Tesserino.TIPODONAZIONE_REGEX)) {
                throw new TesserinoFormException("TesserinoTipoDonazioneError", "Il campo tipo donazione non rispetta il formato. Può assumere solo valori “plasma”,  “cito” e “sangue” ");
            } else {
                return tipoDonazione;
            }
        }
    }



    /**
     * Controlla che le motivazioni di una indisponibilità siano specificato e che rispetti il formato
     * prestabilito.
     *
     * @param motivazioni Stringa che rappresentano le motivazioni da controllare
     * @return motivazioni La stringa che rappresentano le motivazioni da controllare validate
     * @throws TesserinoFormException se le motivazioni non sono specificate oppure se non
     *                            rispettano il formato.
     */
    public String validaMotivazioni(String motivazioni) throws TesserinoFormException {
        /*if (!motivazioni.matches(Tesserino.MOTIVAZIONI_REGEX)) {
                throw new TesserinoFormException("MotivazioneError", "Le motivazioni di indisponibilità non rispettano il formato ");
            }*/
        return motivazioni;
    }

    /**
     * Controlla che il la data di prossima disponibilità presente su un tesserino sia specificata e che rispetti il formato
     * prestabilito.
     *
     * @param dataProssimaDisponibilita Stringa che rappresenta la data di prossima disponibilità da controllare
     * @return dataProssimaDisponibilita La stringa che rappresenta la data di prossima disponibilità da controllare validata
     * @throws TesserinoFormException se  data di prossima disponibilità non è specificata oppure se non
     *                            rispetta il formato {@link Tesserino#DATARILASCIO_REGEX}
     */
    public Date validaDataProssimaDisponibilitaDonazione(Date dataProssimaDisponibilita) throws TesserinoFormException {
        Date date = new Date();
        if (dataProssimaDisponibilita == null) {
            throw new TesserinoFormException("DataProssimaDisponibilitaError", "La Data Di Prossima Disponibilità non rispetta il formato: gg/mm/aaaa ");
        } else {
            if (!(parsDateToString(dataProssimaDisponibilita).matches(Tesserino.DATARILASCIO_REGEX))) {
                throw new TesserinoFormException("DataProssimaDisponibilitaError", "La Data Di Prossima Disponibilità non rispetta il formato: gg/mm/aaaa ");
            } else if (dataProssimaDisponibilita.before(date)) {
                throw new TesserinoFormException("DataProssimaDisponibilitaError", "La Data Di Prossima Disponibilità non può essere antecedente a quella attuale ");
            }
            return dataProssimaDisponibilita;
        }
    }
    /**
     * Metodo che fa parsing dalla (Date) date alla Stringa gg-mm-aaaa
     *
     * @param date data in input
     * @return stringa gg-mm-aaaa
     */
    public static String parsDateToString(Date date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String date1 = format1.format(date);
        return date1;
    }
}