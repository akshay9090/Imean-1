package com.arps.imean;

/**
 * Created by rishabh on 3/10/15.
 */
public class WordsAndLabels {


    private int _id;
    private String _word;
    private String _label;
    private String _meaning;



    public WordsAndLabels() {

    }
//temp function,actually to be done for label
    public WordsAndLabels(String word){

        _word = word;
    }

    public WordsAndLabels(String word, String label,String meaning) {

        _word = word;
        _label = label;
        _meaning = meaning;

    }

    public void set_word_label_meaning(String word, String label,String meaning){

        _word = word;
        _label = label;
        _meaning = meaning;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public String get_label() {
        return _label;
    }

    public void set_label(String _label) {
        this._label = _label;
    }

    public String get_meaning() {
        return _meaning;
    }

    public void set_meaning(String _meaning) {
        this._meaning = _meaning;
    }
}


//NOTE
//another table to be made for word and meanings

