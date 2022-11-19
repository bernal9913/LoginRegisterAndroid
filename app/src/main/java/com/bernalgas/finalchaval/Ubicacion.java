package com.bernalgas.finalchaval;

public class Ubicacion {
    public Ubicacion(Float longitud, Float latitud) {
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Float getLongitud() {
        return longitud;
    }

    public void setLongitud(Float longitud) {
        this.longitud = longitud;
    }

    public Float getLatitud() {
        return latitud;
    }

    public void setLatitud(Float latitud) {
        this.latitud = latitud;
    }

    Float longitud;
    Float latitud;
}
