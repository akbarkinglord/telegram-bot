package org.example.model;

import java.util.List;

public class DictModel {
    private String phonetic;
    private String word;
    private List<Meaning> meanings;

    private List<Phonetics> phonetics;

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String value) {
        this.phonetic = value;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String value) {
        this.word = value;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public void setMeanings(List<Meaning> value) {
        this.meanings = value;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(List<Phonetics> phonetics) {
        this.phonetics = phonetics;
    }
}

