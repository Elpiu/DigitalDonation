package it.unisa.is.c09.digitaldonation.Model.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin Pacifico, Elpidio Mazza
 * Classe astratta che modella un donatore.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Donatore extends Utente{

    @Id
    private String residenza;
    private Date dataDiNascita;
    private String luogoDiNascita;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tesserino")
    private Tesserino tesserino;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "indisponibilita")
    private List<Indisponibilita> listaIndisponibilita;

    /**
     * Costruttore che crea un oggetto Donatore vuoto,
     * che verra' popolato con i metodi setters.
     */
    public Donatore() {
        listaIndisponibilita = new ArrayList<Indisponibilita>();
    }

    /**
     * Costruttore di un utente con parametri utili nei casi di test.
     * @param codiceFiscale è il codice fiscale dell'utente.
     * @param nome è il nome dell'utente.
     * @param cognome è il cognome dell'utente.
     * @param email è l'email dell'utente.
     * @param password è la password dell'utente.
     */
    public Utente(String codiceFiscale, String nome, String cognome, String email, String password, String residenza, Date dataDiNascita, String luogoDiNascita, Tesserino tesserino, List<Indisponibilita> listaIndisponibilita) {
        super(codiceFiscale, nome, cognome, email, password);

    }

    /**
     * Metodo che ritorna il codice fiscale dell'utente.
     * @return codiceFiscale e' il codiceFiscale dell'utente.
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Metodo che setta il codice fiscale dell'utente.
     * @param codiceFiscale e' il codice fiscale dell'utente.
     */
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    /**
     * Metodo che ritorna il nome dell'utente.
     * @return nome e' il nome dell'utente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che setta il nome dell'utente.
     * @param nome e' il nome dell'utente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che ritorna il cognome dell'utente.
     * @return nome e' il cognome dell'utente.
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che setta il cognome dell'utente.
     * @param cognome e' il cognome dell'utente.
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Metodo che ritorna l'email dell'utente.
     * @return email e' l'email dell'utente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metodo che setta l'email dell'utente.
     * @param email è l'email dell'utente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metodo che ritorna la password dell'utente.
     * @return password e' la password dell'utente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Metodo che setta la password dell'utente.
     * @param password è la password dell'utente.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Espressione regolare che definisce il formato del campo codice fiscale. */
    public static final String CF_REGEX = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";

    /** Espressione regolare che definisce il formato dell'email. */
    public static final String EMAIL_REGEX = "/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)/gi";

    /** Espressione regolare che definisce il formato del campo password. */
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@!#$%'-\\/=^\\_`~+&])(?=.*[^0-9a-zA-Z]).{8,16}$";

    /** Espressione regolare che definisce il formato dei campi nome e cognome. */
    public static final String NOME_COGNOME_REGEX = "^[a-zA-Zàòùèéìê' -]{3,20}+$";
}
