package com.example.notasuisrael;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Semestre{
    private Integer codigoSemestre;
    private String descripcionSemestre;

    public Integer getCodigoSemestre() {
        return codigoSemestre;
    }

    public void setCodigoSemestre(Integer codigoSemestre) {
        this.codigoSemestre = codigoSemestre;
    }

    public String getDescripcionSemestre() {
        return descripcionSemestre;
    }

    public void setDescripcionSemestre(String descripcionSemestre) {
        this.descripcionSemestre = descripcionSemestre;
    }
}
